package io.github.shiryu.spider.api.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.github.shiryu.spider.api.executable.Executable;
import io.github.shiryu.spider.api.executable.parseable.*;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@Getter
public class ExecutableRegistry {

    private final Set<Executable> executables = Sets.newHashSet();
    private final List<ParsedHolder> holders = Lists.newArrayList();

    @NotNull
    public Executable parse(@NotNull final Plugin plugin, @NotNull final String raw){
        final ParseContext context = ParseContext.from(raw);

        final Executable executable = new Executable(
                create(plugin, ParseableType.ACTION, context),
                create(plugin, ParseableType.TARGETER, context),
                create(plugin, ParseableType.TRIGGER, context)
        );

        this.executables.add(executable);

        return executable;
    }

    @NotNull
    public <T> T create(@NotNull final Plugin plugin, @NotNull final ParseableType type, @NotNull final ParseContext context){
        final List<ParsedHolder> holders = this.getHolders(type);

        for (final ParsedHolder holder : holders) {
            final Parseable parseable = context.byType(type).orElse(null);

            if (parseable == null)
                continue;

            if (!holder.matches(parseable.getName()))
                continue;

            final T instance = holder.create();

            try{
                final Method method = instance.getClass().getMethod("initialize", ParseContext.class);

                method.invoke(instance, context);
            }catch (final Exception exception){
                throw new IllegalArgumentException("Could not initialize " + type.name().toLowerCase() + " " + holder.name() + "!", exception);
            }

            if (instance instanceof Trigger trigger)
                trigger.register(plugin);

            return instance;
        }

        throw new IllegalArgumentException("Could not find " + type.name().toLowerCase() + " for name " + context.byType(type).map(Parseable::getName).orElse("null") + "!");
    }

    @NotNull
    public List<ParsedHolder> getHolders(@NotNull final ParseableType type){
        return this.holders.stream()
                .filter(holder -> holder.type() == type)
                .toList();
    }

    public void register(@NotNull final ParseableType type, @NotNull final Class<?>... classes){
        for (final Class<?> clazz : classes) {
            this.register(type, clazz);
        }
    }

    public void register(@NotNull final ParseableType type, @NotNull final Class<?> clazz){
        final Parse parse = clazz.getAnnotation(Parse.class);

        if(parse == null)
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not annotated with @Parse or has a different name!");

        this.holders.add(new ParsedHolder(type, parse.name(), parse.description(), parse.aliases(), clazz));
    }
}

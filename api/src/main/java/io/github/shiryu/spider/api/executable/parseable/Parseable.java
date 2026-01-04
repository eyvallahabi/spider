package io.github.shiryu.spider.api.executable.parseable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Parseable {

    private final ParseableType type;
    private final String name;

    private final List<ParseArgument> arguments = Lists.newArrayList();

    public Parseable(@NotNull final ParseableType type, @NotNull final String name, @NotNull final List<ParseArgument> arguments){
        this.type = type;
        this.name = name;
        this.arguments.addAll(arguments);
    }

    @NotNull
    public Map<String, String> getArgumentsForRequirement(@NotNull final String name){
        final ParseArgument argument = find(name);

        if(argument == null)
            return Maps.newHashMap();

        final Map<String, String> arguments = Maps.newHashMap();

        for (final ParseArgument nested : argument.getNested()){
            arguments.put(nested.getName(), nested.getValue());
        }

        return arguments;
    }

    @NotNull
    public Map<String, Map<String, String>> getRequirements(){
        final Map<String, Map<String, String>> requirements = Maps.newHashMap();

        for (final ParseArgument argument : this.arguments){
            if (argument.getNested().isEmpty())
                continue;

            final Map<String, String> arguments = Maps.newHashMap();

            for (final ParseArgument nested : argument.getNested()){
                arguments.put(nested.getName(), nested.getValue());
            }

            requirements.put(argument.getName(), arguments);
        }

        return requirements;
    }

    @Nullable
    public String get(@NotNull final String name){
        final ParseArgument argument = find(name);

        if(argument == null)
            return null;

        return argument.getValue();
    }

    @NotNull
    public String getOrDefault(@NotNull final String name, @NotNull final String def){
        final ParseArgument argument = find(name);

        if(argument == null)
            return def;

        return argument.getValue();
    }

    @Nullable
    public ParseArgument find(@NotNull final String name){
        return this.arguments
                .stream()
                .filter(argument -> argument.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

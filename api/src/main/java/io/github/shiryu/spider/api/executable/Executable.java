package io.github.shiryu.spider.api.executable;

import com.google.common.collect.Lists;
import io.github.shiryu.spider.api.executable.action.Action;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.requirement.Requirement;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

@Getter
public class Executable {

    private final UUID uuid;

    private final List<Trigger> triggers;
    private final List<Requirement> requirements;
    private final Action action;
    private final Targeter targeter;

    public Executable(@NotNull final Action action,
                      @NotNull final Targeter targeter,
                      @NotNull final List<Requirement> requirements,
                      @NotNull final Trigger... triggers){
        this.uuid = UUID.randomUUID();
        this.action = action;
        this.triggers = Lists.newArrayList(triggers);
        this.requirements = requirements;
        this.targeter = targeter;
    }

    public boolean containsTrigger(@NotNull final Class<? extends Trigger> clazz){
        return this.triggers.stream()
                .anyMatch(trigger -> trigger.getClass().equals(clazz));
    }

    public boolean control(@NotNull final ExecutionContext context){
        boolean result = true;

        for (final Requirement requirement : this.requirements){
            result = result && requirement.control(context);
        }

        return result;
    }

    public void accept(@NotNull final Trigger trigger, @NotNull ExecutionContext context){
        if (!containsTrigger(trigger.getClass()))
            return;

        final Object target = this.targeter.find(trigger, context);

        if (target != null)
            context = ExecutionContextBuilder.from(context)
                    .add("target", target)
                    .build();

        if (!control(context))
            return;

        this.action.execute(context);
    }
}

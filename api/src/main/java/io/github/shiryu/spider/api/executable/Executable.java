package io.github.shiryu.spider.api.executable;

import io.github.shiryu.spider.api.executable.action.Action;
import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.parseable.ParseableType;
import io.github.shiryu.spider.api.executable.requirement.Requirement;
import io.github.shiryu.spider.api.executable.targeter.Targeter;
import io.github.shiryu.spider.api.executable.trigger.Trigger;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Executable {

    private final UUID uuid;

    private final Action action;
    private final Targeter targeter;
    private final Trigger trigger;

    private final Map<ParseableType, Requirement> requirements;

    public Executable(@NotNull final Action action,
                      @NotNull final Targeter targeter,
                      @NotNull final Trigger trigger,
                      @NotNull final Map<ParseableType, Requirement> requirements){
        this.uuid = UUID.randomUUID();
        this.action = action;
        this.targeter = targeter;
        this.trigger = trigger;
        this.requirements = requirements;
    }

    public void accept(@NotNull final Trigger trigger, @NotNull ExecutionContext context){
        if (!Objects.equals(trigger, this.trigger))
            return;

        context = ExecutionContextBuilder.from(context)
                .add("target", this.targeter.find(trigger, context))
                .build();

        this.action.execute(context);
    }
}

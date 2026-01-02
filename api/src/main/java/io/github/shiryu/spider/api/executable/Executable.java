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
import java.util.Objects;
import java.util.UUID;

@Getter
public class Executable {

    private final UUID uuid;

    private final Action action;
    private final Targeter targeter;
    private final Trigger trigger;

    private final List<Requirement> requirements;

    public Executable(@NotNull final Action action,
                      @NotNull final Targeter targeter,
                      @NotNull final Trigger trigger,
                      @NotNull final Requirement... requirements){
        this.uuid = UUID.randomUUID();
        this.action = action;
        this.targeter = targeter;
        this.trigger = trigger;
        this.requirements = Lists.newArrayList(requirements);
    }

    public boolean control(@NotNull final ExecutionContext context){
        boolean result = true;

        for (final Requirement requirement : this.requirements){
            result = result && requirement.control(context);
        }

        return result;
    }

    public void accept(@NotNull final Trigger trigger, @NotNull ExecutionContext context){
        if (!Objects.equals(trigger, this.trigger))
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

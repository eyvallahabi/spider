package io.github.shiryu.spider.api.executable.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.executable.variable.Variable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ExecutionContext {

    private final Map<String, Variable<?>> variables;

    private final List<ExecutionContext> childs = Lists.newArrayList();

    @NotNull
    public Entity caster(){
        return this.getOrSet("caster", null);
    }

    @Nullable
    public <T> T get(@NotNull final String name){
        Variable<T> variable = this.getVariable(name);

        if (variable == null)
            return null;

        return variable.getValue();
    }

    @NotNull
    public <T> T getOrSet(@NotNull final String name, @NotNull final T defaultValue){
        Variable<T> variable = this.getVariable(name);

        if (variable != null)
            return variable.getValue();

        variable = new Variable<T>() {
            private T val = defaultValue;

            @Override
            public @NotNull String getName() {
                return name;
            }

            @Override
            public @NotNull T getValue() {
                return val;
            }

            @Override
            public void setValue(@NotNull T value) {
                this.val = value;
            }
        };

        this.add(variable);

        return defaultValue;
    }

    @Nullable
    public Variable getVariable(@NotNull final String name){
        Variable variable = this.variables.get(name);

        if (variable != null)
            return variable;

        for (ExecutionContext child : this.childs) {
            variable = child.getVariable(name);
            if (variable != null)
                return variable;
        }

        return null;
    }

    public void add(@NotNull final Variable<?> variable){
        this.variables.put(variable.getName(), variable);
    }

    public void remove(@NotNull final String name){
        this.variables.remove(name);
    }

    public void clear(){
        this.variables.clear();
    }

    public boolean contains(@NotNull final String name){
        return this.variables.containsKey(name);
    }

    public void addChild(@NotNull final ExecutionContext child){
        this.childs.add(child);
    }

    public void removeChild(@NotNull final ExecutionContext child){
        this.childs.remove(child);
    }

    @NotNull
    public ExecutionContext copy(){
        return new ExecutionContext(Maps.newHashMap(this.variables));
    }

    @NotNull
    public ExecutionContext merge(@NotNull final ExecutionContext other){
        final Map<String, Variable<?>> merged = Maps.newHashMap(this.variables);
        merged.putAll(other.variables);
        return new ExecutionContext(merged);
    }

}

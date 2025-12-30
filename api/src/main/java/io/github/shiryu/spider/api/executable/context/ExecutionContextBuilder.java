package io.github.shiryu.spider.api.executable.context;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.executable.variable.Variable;
import io.github.shiryu.spider.api.executable.variable.impl.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ExecutionContextBuilder {

    private final Map<String, Variable<?>> variables = Maps.newHashMap();

    @NotNull
    public ExecutionContextBuilder add(@NotNull final String name, @NotNull final Object value){
        Variable<?> variable = null;

        if (value.getClass() == Boolean.class){
            variable = new BooleanVariable(name, (Boolean) value);
        }else if (value.getClass() == Double.class){
            variable = new DoubleVariable(name, (Double) value);
        }else if (value instanceof Player player){
            variable = new PlayerVariable(name, player);
        }else if (value instanceof Entity entity){
            variable = new EntityVariable(name, entity);
        }else if (value instanceof Event event){
            variable = new EventVariable(name, event);
        }else if (value.getClass() == String.class){
            variable = new StringVariable(name, (String) value);
        }else if (value.getClass() == Integer.class){
            variable = new IntegerVariable(name, (Integer) value);
        }else if (value.getClass() == Location.class){
            variable = new LocationVariable(name, (Location) value);
        }else if (value instanceof World world){
            variable = new WorldVariable(name, world);
        }

        if (variable == null)
            return this;

        this.variables.put(name, variable);
        return this;
    }

    @NotNull
    public ExecutionContext build(){
        return new ExecutionContext(this.variables);
    }
}

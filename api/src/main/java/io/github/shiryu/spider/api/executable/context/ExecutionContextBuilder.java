package io.github.shiryu.spider.api.executable.context;

import com.google.common.collect.Maps;
import io.github.shiryu.spider.api.executable.variable.Variable;
import io.github.shiryu.spider.api.executable.variable.impl.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ExecutionContextBuilder {

    @NotNull
    public static ExecutionContextBuilder from(@NotNull final ExecutionContext context){
        return new ExecutionContextBuilder(context);
    }

    @NotNull
    public static ExecutionContextBuilder newBuilder(){
        return new ExecutionContextBuilder();
    }

    private final Map<String, Variable<?>> variables = Maps.newHashMap();

    private ExecutionContext context;

    public ExecutionContextBuilder(){

    }

    public ExecutionContextBuilder(@NotNull final ExecutionContext context){
        this.context = context;
        this.variables.putAll(context.getVariables());
    }

    @NotNull
    public ExecutionContextBuilder caster(@NotNull final Entity entity){
        return this.add("caster", entity);
    }

    @NotNull
    public ExecutionContextBuilder trigger(@NotNull final Entity entity){
        return this.add("trigger", entity);
    }

    @NotNull
    public ExecutionContextBuilder trigger(@NotNull final Location location){
        return this.add("trigger", location);
    }

    @NotNull
    public ExecutionContextBuilder trigger(@NotNull final Block block){
        return this.add("trigger", block);
    }

    @NotNull
    public ExecutionContextBuilder player(@NotNull final String name, @NotNull final Player player){
        return this.add(name, player);
    }

    @NotNull
    public ExecutionContextBuilder damageSource(@NotNull final String name, @NotNull final DamageSource source){
        return this.add(name, source);
    }

    @NotNull
    public <T> ExecutionContextBuilder list(@NotNull final String name, @NotNull final List<T> list){
        return this.add(name, list);
    }

    @NotNull
    public ExecutionContextBuilder event(@NotNull final Event event){
        return this.add("event", event);
    }

    @NotNull
    public ExecutionContextBuilder location(@NotNull final Location location){
        return this.add("location", location);
    }

    @NotNull
    public ExecutionContextBuilder location(@NotNull final String name, @NotNull final Location location){
        return this.add(name, location);
    }

    @NotNull
    public ExecutionContextBuilder world(@NotNull final World world){
        return this.add("world", world);
    }

    @NotNull
    public ExecutionContextBuilder entity(@NotNull final String name, @NotNull final Entity entity){
        return this.add(name, entity);
    }

    @NotNull
    public ExecutionContextBuilder integer(@NotNull final String name, final int value){
        return this.add(name, value);
    }

    @NotNull
    public ExecutionContextBuilder string(@NotNull final String name, @NotNull final String value){
        return this.add(name, value);
    }

    @NotNull
    public ExecutionContextBuilder bool(@NotNull final String name, final boolean value){
        return this.add(name, value);
    }

    @NotNull
    public ExecutionContextBuilder decimal(@NotNull final String name, final double value){
        return this.add(name, value);
    }

    @NotNull
    public ExecutionContextBuilder block(@NotNull final String name, @NotNull final Block block){
        return this.add(name, block);
    }

    @NotNull
    public ExecutionContextBuilder item(@NotNull final String name, @NotNull final ItemStack itemStack){
        return this.add(name, itemStack);
    }

    @NotNull
    public ExecutionContextBuilder decimal(@NotNull final String name, final float value){
        return this.add(name, value);
    }

    @NotNull
    public ExecutionContextBuilder add(@NotNull final Variable<?> variable){
        this.variables.put(variable.getName(), variable);
        return this;
    }

    @NotNull
    public ExecutionContextBuilder add(@NotNull final String name, @NotNull final Object value){
        Variable<?> variable = null;

        if (value.getClass() == Float.class){
            variable = new FloatVariable(name, (Float) value);
        }else if (value instanceof List<?> list){
            variable = new ListVariable<>(name, list);
        }else if (value instanceof DamageSource source){
            variable = new DamageSourceVariable(name, source);
        }else if (value.getClass() == Boolean.class){
            variable = new BooleanVariable(name, (Boolean) value);
        }else if (value.getClass() == Double.class){
            variable = new DoubleVariable(name, (Double) value);
        }else if (value instanceof Player player){
            variable = new PlayerVariable(name, player);
        }else if (value instanceof Entity entity){
            variable = new EntityVariable(name, entity);
        }else if (value instanceof Event event){
            variable = new EventVariable(name, event);
        }else if (value instanceof Block block){
            variable = new BlockVariable(name, block);
        }else if (value.getClass() == ItemStack.class){
            variable = new ItemStackVariable(name, (ItemStack) value);
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

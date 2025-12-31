package io.github.shiryu.spider.api.executable.trigger.impl.event;

import io.github.shiryu.spider.api.executable.context.ExecutionContext;
import io.github.shiryu.spider.api.executable.context.ExecutionContextBuilder;
import io.github.shiryu.spider.api.executable.trigger.TriggerInfo;
import io.github.shiryu.spider.api.executable.trigger.ext.EventTrigger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

@TriggerInfo(name = "@bucket", description = "Triggers when a player uses a bucket.")
public class BucketTrigger implements EventTrigger {

    @EventHandler(priority = EventPriority.MONITOR)
    public void fill(final PlayerBucketFillEvent event){
        final Player player = event.getPlayer();

        var builder = ExecutionContextBuilder.newBuilder()
                .caster(player)
                .event(event)
                .location(player.getLocation())
                .world(player.getWorld())
                .trigger(event.getBlockClicked())
                .block("block", event.getBlock())
                .string("blockFace", event.getBlockFace().name())
                .string("bucketAction", "FILL");

        if (event.getItemStack() != null)
            builder = builder.item("bucket", event.getItemStack());

        this.call(builder.build());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void empty(final PlayerBucketEmptyEvent event){
        final Player player = event.getPlayer();

        var builder = ExecutionContextBuilder.newBuilder()
                .caster(player)
                .event(event)
                .location(player.getLocation())
                .world(player.getWorld())
                .trigger(event.getBlockClicked())
                .block("block", event.getBlock())
                .string("blockFace", event.getBlockFace().name())
                .string("bucketAction", "EMPTY");

        if (event.getItemStack() != null)
            builder = builder.item("bucket", event.getItemStack());

        this.call(builder.build());
    }
}

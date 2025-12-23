package io.github.shiryu.spider.integration.enchantment.ae.effects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.advancedplugins.ae.impl.effects.effects.actions.execution.ExecutionTask;
import net.advancedplugins.ae.impl.effects.effects.effects.AdvancedEffect;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TimberEffect extends AdvancedEffect {

    public TimberEffect(@NotNull final JavaPlugin plugin){
        super(plugin, "TIMBER", "Causes a tree to be cut down when the bottom log is broken.", "Timber", 10);
    }

    @Override
    public boolean executeEffect(@NotNull final ExecutionTask task, @NotNull final LivingEntity entity, @NotNull final String[] args){
        return executeEffect(task, task.getBuilder().getBlock().getLocation(), args);
    }

    @Override
    public boolean executeEffect(@NotNull final ExecutionTask task, @NotNull final Location location, @NotNull final String[] args){
        final Block block = location.getBlock();
        final ItemStack item = task.getBuilder().getItem();

        Bukkit.getScheduler().runTaskAsynchronously(
                getPlugin(),
                () -> {
                    final Tree tree = this.getTree(block, item);

                    if (this.isTree(tree.getBlocks()))
                        Bukkit.getScheduler().runTask(getPlugin(), tree::fell);
                }
        );

        return true;
    }

    private int getCount(@NotNull final Location center, @NotNull final Block block) {
        int count = 0;

        World world = block.getWorld();
        int cx = center.getBlockX();
        int cy = center.getBlockY();
        int cz = center.getBlockZ();

        int radiusXZ = 3; // yatay tarama (oak/birch için mükemmel)
        int radiusY  = 2; // dikey tarama

        for (int x = cx - radiusXZ; x <= cx + radiusXZ; x++) {
            for (int y = cy - radiusY; y <= cy + radiusY; y++) {
                for (int z = cz - radiusXZ; z <= cz + radiusXZ; z++) {

                    Block relative = world.getBlockAt(x, y, z);

                    if (isLeaves(relative.getType())) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private boolean isLeaves(Material type) {
        return type.name().endsWith("_LEAVES");
    }

    public boolean isTree(@NotNull final List<Block> blocks) {
        if (blocks.isEmpty())
            return false;

        Block base = blocks.getFirst();

        if (base.getType().name().endsWith("_STEM"))
            return true;

        int topY = blocks.stream()
                .mapToInt(Block::getY)
                .max()
                .orElse(base.getY());

        Location topLoc = base.getLocation().clone();
        topLoc.setY(topY);

        Block topBlock = base.getWorld().getBlockAt(topLoc);

        int leafCount = getCount(topLoc, topBlock);

        return leafCount >= 3;
    }

    public boolean isLog(@NotNull final Material material){
        return material.name().endsWith("_LOG") || material.name().endsWith("_STEM");
    }

    @NotNull
    public Tree getTree(@NotNull final Block start, @NotNull final ItemStack tool) {
        final Set<Block> visited = new HashSet<>();
        final Deque<Block> queue = new ArrayDeque<>();

        visited.add(start);
        queue.add(start);

        final int maxBlocks = 500;

        while (!queue.isEmpty() && visited.size() < maxBlocks) {
            Block current = queue.pollFirst();

            for (Block neighbor : getNeighbors(current, 1)) {
                if (!isLog(neighbor.getType())) continue;
                if (visited.contains(neighbor)) continue;

                visited.add(neighbor);
                queue.add(neighbor);
            }
        }

        return new Tree(new ArrayList<>(visited), tool);
    }

    private List<Block> getNeighbors(Block block, int radius) {
        List<Block> list = new ArrayList<>();
        var world = block.getWorld();

        int bx = block.getX();
        int by = block.getY();
        int bz = block.getZ();

        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    if (x == bx && y == by && z == bz) continue;
                    list.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return list;
    }

    @Getter
    @RequiredArgsConstructor
    public class Tree {

        private final List<Block> blocks;
        private final ItemStack tool;

        public void fell() {
            if (blocks.isEmpty())
                return;

            new BukkitRunnable() {

                private int index = 0;

                @Override
                public void run() {
                    if (index >= blocks.size()) {
                        cancel();
                        return;
                    }

                    Block block = blocks.get(index++);
                    breakBlock(block);
                }

            }.runTaskTimer(getPlugin(), 0L, 2L);
        }

        private void breakBlock(@NotNull Block block) {
            block.breakNaturally(tool);

            block.getWorld().playSound(
                    block.getLocation(),
                    Sound.BLOCK_WOOD_BREAK,
                    1.0f,
                    1.0f
            );
        }
    }


}

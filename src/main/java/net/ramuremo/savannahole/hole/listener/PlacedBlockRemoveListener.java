package net.ramuremo.savannahole.hole.listener;

import net.ramuremo.savannahole.hole.HoleHandler;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public final class PlacedBlockRemoveListener implements Listener {

    private final HoleHandler handler;
    private final Map<Block, BukkitTask> tasks = new HashMap<>();

    public PlacedBlockRemoveListener(HoleHandler handler) {
        this.handler = handler;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        final Block block = event.getBlock();
        final Location location = block.getLocation();

        final Block existsBlock = tasks.keySet().stream()
                .filter(b -> b.getLocation().equals(block.getLocation()))
                .findFirst()
                .orElse(null);

        if (existsBlock == null) {
            runPlacedBlockRemoveTask(location, block);
        } else {
            runPlacedBlockRemoveTask(location, existsBlock);
        }
    }

    private void runPlacedBlockRemoveTask(Location location, Block existsBlock) {
        final BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                final BlockState state = location.getBlock().getState();
                state.setType(existsBlock.getType());
                state.setData(existsBlock.getState().getData());
                tasks.remove(existsBlock);
            }
        }.runTaskLater(handler.plugin, 20 * 30);
        tasks.put(existsBlock, task);
    }

    public void removeAll() {

        tasks.clear();
    }
}

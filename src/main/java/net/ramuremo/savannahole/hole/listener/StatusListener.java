package net.ramuremo.savannahole.hole.listener;

import net.ramuremo.savannahole.Util;
import net.ramuremo.savannahole.hole.HoleHandler;
import net.ramuremo.savannahole.hole.Status;
import net.ramuremo.savannahole.hole.event.StatusChangeEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public final class StatusListener implements Listener {

    private final HoleHandler handler;
    private final Map<Player, BukkitTask> tasks = new HashMap<>();

    public StatusListener(HoleHandler handler) {
        this.handler = handler;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        setStatus(player, Status.WAITING);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();

        setStatus(player, Status.WAITING);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        final Entity entity = event.getEntity();
        if (!entity.getType().equals(EntityType.PLAYER)) return;
        final Player player = (Player) entity;

        if (!event.getDamager().getType().equals(EntityType.PLAYER)) return;
        final Player damager = (Player) event.getDamager();
        setStatus(damager, Status.FIGHTING);
        runChangeStatusTask(damager, Status.RESTING, 20 * 10);


        setStatus(player, Status.FIGHTING);
        runChangeStatusTask(player, Status.RESTING, 20 * 10);
    }

    private void setStatus(Player player, Status status) {
        final StatusChangeEvent event = new StatusChangeEvent(player, status);
        Util.callEvent(event);
        if (event.isCancelled()) return;

        handler.statusMap.put(player, event.getStatus());
    }

    private void runChangeStatusTask(Player player, Status status, int delay) {
        final BukkitTask executedTask = tasks.get(player);
        if (executedTask != null) executedTask.cancel();

        final BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                tasks.remove(player);
                setStatus(player, status);
            }
        }.runTaskLaterAsynchronously(handler.plugin, delay);
        tasks.put(player, task);
    }
}

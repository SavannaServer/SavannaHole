package net.ramuremo.savannahole.hole.listener;

import net.ramuremo.savannahole.hole.HoleHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public final class TeleportSpawnListener implements Listener {

    private final HoleHandler handler;

    public TeleportSpawnListener(HoleHandler handler) {
        this.handler = handler;
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(handler.map.getSpawnLocation());
    }
}

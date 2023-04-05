package net.ramuremo.savannahole.hole;

import net.ramuremo.savannahole.SavannaHole;
import net.ramuremo.savannahole.Util;
import net.ramuremo.savannahole.asset.MapAsset;
import net.ramuremo.savannahole.asset.map.DebugMap;
import net.ramuremo.savannahole.hole.listener.StatusListener;
import net.ramuremo.savannahole.hole.listener.TeleportSpawnListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class HoleHandler {

    public final SavannaHole plugin;
    public final MapAsset map;
    public final World world;
    public final Map<Player, Status> statusMap = new HashMap<>();

    public HoleHandler(SavannaHole plugin) {
        this.plugin = plugin;
        this.map = new DebugMap();
        world = map.getCustomCreator().createWorld();
        world.setAutoSave(map.isEnableSave());

        registerListeners();
    }

    private void registerListeners() {
        Util.registerListeners(
                plugin,
                new StatusListener(this),
                new TeleportSpawnListener(this)
        );
    }

    public void unload() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(ChatColor.RED + "サーバーを再起動しています。"));
        Bukkit.unloadWorld(world, map.isEnableSave());
    }
}

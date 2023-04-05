package net.ramuremo.savannahole.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;


public final class HoleConfig extends ConfigFile {

    public HoleConfig(Plugin plugin) {
        super(plugin, "config.yml");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public Location getSpawnLocation() {
        final String world = getConfig().getString("spawn-location.world", "world");
        final double x = getConfig().getDouble("spawn-location.x", 0d);
        final double y = getConfig().getDouble("spawn-location.y", 0d);
        final double z = getConfig().getDouble("spawn-location.z", 0d);
        final float yaw = (float) getConfig().getDouble("spawn-location.yaw", 0d);
        final float pitch = (float) getConfig().getDouble("spawn-location.pitch", 0d);
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }
}

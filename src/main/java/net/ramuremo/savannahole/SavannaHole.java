package net.ramuremo.savannahole;

import net.ramuremo.savannahole.config.HoleConfig;
import net.ramuremo.savannahole.hole.HoleHandler;
import net.ramuremo.savannahole.world.WorldHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class SavannaHole extends JavaPlugin {

    public HoleConfig config;
    public WorldHandler worldHandler;
    public HoleHandler holeHandler;

    @Override
    public void onEnable() {
        config = new HoleConfig(this);
        worldHandler = new WorldHandler(this);
        holeHandler = new HoleHandler(this);

        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        holeHandler.unload();
        getLogger().info("The plugin has been disabled.");
    }
}

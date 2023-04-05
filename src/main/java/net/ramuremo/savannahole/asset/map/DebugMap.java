package net.ramuremo.savannahole.asset.map;

import net.ramuremo.savannahole.Util;
import net.ramuremo.savannahole.asset.MapAsset;
import org.bukkit.*;
import org.bukkit.util.Vector;

public final class DebugMap implements MapAsset {
    @Override
    public String getWorldName() {
        return "debug";
    }

    @Override
    public String getDisplayName() {
        return ChatColor.GRAY + "デバッグ";
    }

    @Override
    public WorldCreator getCustomCreator() {
        return new WorldCreator(getWorldName())
                .environment(World.Environment.NORMAL)
                .generateStructures(false)
                .type(WorldType.NORMAL)
                .generator(new Util.VoidGenerator());
    }

    @Override
    public boolean isEnableSave() {
        return true;
    }

    @Override
    public Location getSpawnLocation() {
        return new Location(Bukkit.getWorld(getWorldName()), 0, 0, 0, 0, 0);
    }

    @Override
    public Vector getSpawnPos1() {
        return new Vector(10, 10, 10);
    }

    @Override
    public Vector getSpawnPos2() {
        return new Vector(-10, -10, -10);
    }
}

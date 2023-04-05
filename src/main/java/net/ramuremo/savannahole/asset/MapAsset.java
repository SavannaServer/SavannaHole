package net.ramuremo.savannahole.asset;

import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.util.Vector;

public interface MapAsset {
    String getWorldName();

    String getDisplayName();

    WorldCreator getCustomCreator();

    boolean isEnableSave();

    Location getSpawnLocation();

    Vector getSpawnPos1();

    Vector getSpawnPos2();
}

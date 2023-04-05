package net.ramuremo.savannahole;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public final class Util {

    public static void registerListeners(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static void registerCommands(Command... commands) {
        for (Command command : commands) {
            ((CraftServer) Bukkit.getServer()).getCommandMap().register("savanna", command);
        }
    }

    public static <T extends Event> void callEvent(T event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static final class VoidGenerator extends ChunkGenerator {
        @Override
        public ChunkData generateChunkData(org.bukkit.World world, Random random, int x, int z, BiomeGrid biome) {
            return createChunkData(world);
        }
    }
}

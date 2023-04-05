package net.ramuremo.savannahole.sidebar;

import net.ramuremo.savannahole.SavannaHole;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public final class SideBarHandler {
    public final SavannaHole plugin;
    private final Set<SideBar> sideBars = new HashSet<>();
    private final BukkitTask updateTimer;

    public SideBarHandler(SavannaHole plugin) {
        this.plugin = plugin;

        updateTimer = Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                this::updateAll,
                20, 5
        );
    }

    @Nullable
    public SideBar getCurrentSideBar(Player player) {
        return sideBars.stream()
                .filter(sidebar -> sidebar.player.equals(player))
                .findFirst()
                .orElse(null);
    }

    public Set<SideBar> getSideBars() {
        return sideBars;
    }

    @Nullable
    public SideBar getSideBar(Player player) {
        for (SideBar sideBar : sideBars) {
            if (sideBar.player.equals(player)) return sideBar;
        }
        return null;
    }

    public void setSideBar(SideBar instance) {
        remove(instance.player);

        sideBars.add(instance);
    }

    private void updateAll() {
        sideBars.forEach(SideBar::update);
    }

    public void remove(Player player) {
        for (SideBar sideBar : sideBars) {
            if (!sideBar.player.equals(player)) continue;

            sideBar.remove();
            sideBars.remove(sideBar);
        }
    }

    public void removeAll() {
        sideBars.clear();
    }
}
package net.ramuremo.savannahole.hole;

import org.bukkit.ChatColor;

public enum Status {
    WAITING(ChatColor.AQUA + "待機中"),
    RESTING(ChatColor.GREEN + "休憩中"),
    FIGHTING(ChatColor.RED + "戦闘中");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

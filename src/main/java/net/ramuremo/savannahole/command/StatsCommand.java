package net.ramuremo.savannahole.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class StatsCommand extends Command {

    public StatsCommand() {
        super("stats");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        return false;
    }
}

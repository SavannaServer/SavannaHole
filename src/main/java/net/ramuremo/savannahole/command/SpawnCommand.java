package net.ramuremo.savannahole.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public final class SpawnCommand extends Command {

    public SpawnCommand() {
        super("spawn");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        return false;
    }
}

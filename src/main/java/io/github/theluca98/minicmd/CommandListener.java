package io.github.theluca98.minicmd;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.Arrays;

class CommandListener implements Listener {

    private final CommandManager manager;

    public CommandListener(CommandManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if (handleCommand(e.getPlayer(), e.getMessage())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent e) {
        if (handleCommand(e.getSender(), e.getCommand())) {
            e.setCancelled(true);
        }
    }

    private boolean handleCommand(CommandSender sender, String message) {
        String[] parts = message.split(" ");
        boolean result = parts.length > 0 ? manager.post(sender, parts[0].replaceFirst("/", ""), Arrays.copyOfRange(parts, 1, parts.length))
                : manager.post(sender, parts[0].replaceFirst("/", ""), new String[0]);
        manager.debug("handleCommand.result = " + result);
        return result;
    }

}
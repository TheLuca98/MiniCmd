package io.github.theluca98.minicmd;

import io.github.theluca98.minicmd.exceptions.CommandException;
import io.github.theluca98.minicmd.exceptions.UsageException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class CommandManager {

    private final List<Object> listeners;
    private final Plugin plugin;
    private boolean debug;

    /**
     * Creates a new instance of {@link CommandManager}, given a {@link Plugin}.
     *
     * @param plugin Your plugin's instance.
     */
    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
        this.listeners = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(new CommandListener(this), this.plugin);
        this.debug = false;
    }

    /**
     * Registers all the {@link Command} methods in the given listener class.
     *
     * @param listener The listener class.
     */
    public void register(Object listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters all the {@link Command} methods in the given listener class.
     *
     * @param listener The listener class.
     */
    public void unregister(Object listener) {
        listeners.remove(listener);
    }

    /**
     * Sends a specific command on behalf of a given {@link CommandSender}.
     *
     * @param sender The sender.
     * @param label  The name of the command.
     * @param args   A list of arguments.
     * @return Whether or not the command was handled by any of the listeners.
     */
    public boolean post(CommandSender sender, String label, String[] args) {
        Pair<Method, Object> pair = find(label);
        if (pair != null) {
            if (Player.class.isAssignableFrom(pair.getFirstValue().getParameterTypes()[0]) && !(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "This command must be executed as a player.");
                return true;
            }
            if (checkPermission(sender, pair.getFirstValue())) {
                try {
                    new CommandCall(pair, sender, args).call();
                } catch (CommandException e) {
                    if (e instanceof UsageException) {
                        sender.sendMessage(UsageException.getUsageMessage(pair.getFirstValue().getAnnotation(Command.class)));
                    } else {
                        sender.sendMessage(e.getFormattedMessage());
                    }
                } catch (Throwable e) {
                    sender.sendMessage(ChatColor.RED + "An internal error occurred while executing this command.");
                    e.printStackTrace();
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
        } else {
            return false;
        }
        return true;
    }

    private Pair<Method, Object> find(String label) {
        for (Object l : listeners) {
            for (Method m : l.getClass().getMethods()) {
                if (isCommand(m) && arrayHas(m.getAnnotation(Command.class).value(), label)) {
                    debug("find.pair != null");
                    return new Pair<>(m, l);
                }
            }
        }
        debug("find.pair = null");
        return null;
    }

    private boolean checkPermission(CommandSender sender, Method method) {
        boolean result;
        if (method.getAnnotation(Permission.class) != null) {
            result = sender.hasPermission(method.getAnnotation(Permission.class).value());
        } else {
            result = true;
        }
        debug("checkPermission.result = " + result);
        return result;
    }

    private boolean isCommand(Method method) {
        return method.getAnnotation(Command.class) != null &&
                method.getParameterCount() >= 2 &&
                CommandSender.class.isAssignableFrom(method.getParameterTypes()[0]) &&
                CommandArgs.class.isAssignableFrom(method.getParameterTypes()[1]);
    }

    private boolean arrayHas(String[] array, String string) {
        for (String item : array) {
            if (item.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    // Needed for tab-complete events
    List<String> search(String tcLabel) {
        List<String> list = new ArrayList<>();
        for (Object l : listeners) {
            for (Method m : l.getClass().getMethods()) {
                Command cmd = m.getAnnotation(Command.class);
                if (cmd != null) {
                    for (String s : cmd.value()) {
                        if (s.startsWith("/" + tcLabel)) {
                            list.add("/" + s);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * Toggles ON (true) or OFF (false) debug messages.
     *
     * @param debug The value.
     */
    public void toggleDebug(boolean debug) {
        this.debug = debug;
        debug("Debug mode is ENABLED.");
    }

    void debug(String message) {
        if (this.debug) {
            this.plugin.getLogger().log(Level.INFO, "[MiniCmd Debug] " + message);
        }
    }

}
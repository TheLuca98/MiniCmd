package io.github.theluca98.minicmd.exceptions;

import com.google.common.base.Joiner;
import io.github.theluca98.minicmd.Command;
import org.bukkit.ChatColor;

/**
 * Represents an exception which should be thrown when the sender doesn't provide enough arguments.
 */
public class UsageException extends CommandException {

    /**
     * Creates a new {@link UsageException} object.
     */
    public UsageException() {
        super("Invalid usage.");
    }

    /**
     * Utility method which returns the appropriate usage message for a {@link Command}.
     *
     * @param cmd The command.
     * @return The formatted usage message.
     */
    public static String getUsageMessage(Command cmd) {
        return (cmd.description().length > 0 ?
                ChatColor.RED.toString() + Joiner.on("\n" + ChatColor.RED).join(cmd.description()) + "\n" : "")
                + ChatColor.RED + "Usage: /" + cmd.value()[0] + " " + Joiner.on(' ').join(cmd.usage());
    }

    /**
     * Returns a generic usage message. This method has no use right now.
     *
     * @return A generic message.
     * @deprecated Replaced by {@link #getUsageMessage(Command)}.
     */
    @Override
    @Deprecated
    public String getFormattedMessage() {
        return super.getFormattedMessage();
    }
}
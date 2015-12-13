package io.github.theluca98.minicmd.exceptions;

import org.bukkit.ChatColor;

/**
 * Represents an exception used to display an error message to a {@link org.bukkit.command.CommandSender}.
 */
public class CommandException extends Exception {

    /**
     * Creates a new {@link CommandException} object, given a message.
     *
     * @param message The message to display.
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Returns the error message as it should be displayed in chat.
     *
     * @return The formatted error message.
     */
    public String getFormattedMessage() {
        return ChatColor.RED + getMessage().replace("\n", "\n" + ChatColor.RED);
    }

}
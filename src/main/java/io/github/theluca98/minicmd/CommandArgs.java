package io.github.theluca98.minicmd;

import com.google.common.base.Joiner;
import io.github.theluca98.minicmd.exceptions.CommandException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An implementation of {@link ArrayList} which contains methods for handling command arguments.
 */
public class CommandArgs extends ArrayList<String> {

    CommandArgs(String[] args) {
        super(Arrays.asList(args));
    }

    /**
     * Parses an argument as a {@link Player}, or prints an error message to the sender.
     *
     * @param index Index of the argument.
     * @return The resulting player.
     * @throws CommandException if the player could not be found.
     */
    @SuppressWarnings("deprecation")
    public Player getPlayer(int index) throws CommandException {
        Player player = Bukkit.getPlayer(get(index));
        if (player != null) {
            return player;
        } else {
            throw new CommandException("Error (argument " + (index + 1) + "): player is not online.");
        }
    }

    /**
     * Parses an argument as an integer number, or prints an error message to the sender.
     *
     * @param index Index of the argument.
     * @return The resulting {@code int}.
     * @throws CommandException if the argument is not a valid number.
     */
    public int getInt(int index) throws CommandException {
        try {
            return Integer.parseInt(get(index));
        } catch (NumberFormatException e) {
            throw new CommandException("Error (argument " + (index + 1) + "): must be a valid integer.");
        }
    }

    /**
     * Parses an argument as a floating point number, or prints an error message to the sender.
     *
     * @param index Index of the argument.
     * @return The resulting {@code float}.
     * @throws CommandException if the argument is not a valid number.
     */
    public float getFloat(int index) throws CommandException {
        try {
            return Float.parseFloat(get(index));
        } catch (NumberFormatException e) {
            throw new CommandException("Error (argument " + (index + 1) + "): must be a valid number.");
        }
    }

    /**
     * Parses an argument as a double-precision floating point number, or prints an error message to the sender.
     *
     * @param index Index of the argument.
     * @return The resulting {@code double}.
     * @throws CommandException if the argument is not a valid number.
     */
    public double getDouble(int index) throws CommandException {
        try {
            return Double.parseDouble(get(index));
        } catch (NumberFormatException e) {
            throw new CommandException("Error (argument " + (index + 1) + "): must be a valid number.");
        }
    }

    /**
     * Parses an argument as a boolean value, or prints an error message to the sender.
     *
     * @param index Index of the argument.
     * @return The resulting {@code boolean}.
     * @throws CommandException if the argument is not a valid boolean value.
     */
    public boolean getBoolean(int index) throws CommandException {
        try {
            return Boolean.parseBoolean(get(index));
        } catch (Exception e) {
            throw new CommandException("Error (argument " + (index + 1) + "): must be either 'true' or 'false'.");
        }
    }

    /**
     * Checks if a specific command flag (-a, -b, -c, ...) is present.
     *
     * @param flag The flag.
     * @return Whether the flag is present.
     */
    public boolean hasFlag(char flag) {
        return contains("-" + flag);
    }

    @Override
    public String toString() {
        return Joiner.on(' ').join(this);
    }
}
package io.github.theluca98.minicmd;

import io.github.theluca98.minicmd.exceptions.UsageException;
import org.bukkit.command.CommandSender;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class CommandCall {

    private final Pair<Method, Object> pair;
    private final CommandSender sender;
    private final String[] args;

    public CommandCall(Pair<Method, Object> pair, CommandSender sender, String[] args) {
        this.pair = pair;
        this.sender = sender;
        this.args = args;
    }

    public void call() throws Throwable {
        try {
            pair.getFirstValue().invoke(pair.getSecondValue(), sender, new CommandArgs(args));
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof IndexOutOfBoundsException) {
                throw new UsageException();
            } else {
                throw e.getCause();
            }
        }
    }

}
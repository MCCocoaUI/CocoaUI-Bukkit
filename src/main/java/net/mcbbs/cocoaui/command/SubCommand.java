package net.mcbbs.cocoaui.command;

import org.bukkit.command.CommandSender;

/**
 * Represent a sub command
 *
 * @author Zoyn
 * @since 2018-08-05
 */
@FunctionalInterface
public interface SubCommand {

    void execute(CommandSender sender, String[] args);

}

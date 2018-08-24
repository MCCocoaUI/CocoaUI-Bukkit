package net.mcbbs.cocoaui.command.subcommand;

import net.mcbbs.cocoaui.command.StaticPages;
import net.mcbbs.cocoaui.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Zoyn Fuck!
 * @since 2018-08-05
 */
public class HelpCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        StaticPages.MainPage.show((Player) sender);
    }

    public static void main(String[] args) {

    }
}

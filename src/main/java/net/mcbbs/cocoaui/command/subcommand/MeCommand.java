package net.mcbbs.cocoaui.command.subcommand;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player!");
            return;
        }
        String head = args[1];
        if(head.equalsIgnoreCase("create")) {
        	if(args.length==3) {
        			CocoaUI.getPicturesManager().registerPlugin(args[2]);
        	}
        }
        
    }
}

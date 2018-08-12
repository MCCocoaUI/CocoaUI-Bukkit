package net.mcbbs.cocoaui.command.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.command.StaticPages;
import net.mcbbs.cocoaui.command.SubCommand;
import net.mcbbs.cocoaui.managers.picturemanager.PluginPicturesManager;

public class PictureCommand implements SubCommand {

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!sender.isOp()) {
			sender.sendMessage("§c You don't have permissions to do it...");
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only player can use this command...");
			return;
		}
		Player p = (Player) sender;
		if (args.length == 1) {
			StaticPages.PictureMainPage.show(p);
			return;
		}
		String head = args[1];
		if (head.equalsIgnoreCase("list")) {
			if (args.length == 3) {
				
			}
		}
	}

	private void listPlugins(Player p) {
		
	}

}

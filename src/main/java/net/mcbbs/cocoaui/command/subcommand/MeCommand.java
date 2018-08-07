package net.mcbbs.cocoaui.command.subcommand;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.command.SubCommand;
import net.mcbbs.cocoaui.managers.picturemanager.Picture;

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
		if (head.equalsIgnoreCase("create")) {
			if (args.length == 3) {
				CocoaUI.getPicturesManager().registerPlugin(args[2]);
			}
		}
		if (head.equalsIgnoreCase("addp")) {
			if (args.length == 5) {
				CocoaUI.getPicturesManager().getPluginPictureManager(args[2])
						.loadPicture(new Picture(args[3], args[4]));
			}
		}
		if (head.equalsIgnoreCase("lookup")) {
			if (args.length == 3) {
				sender.sendMessage(
				CocoaUI.getPicturesManager().getPluginPictureManager(args[2]).getPackage().toString());
			}
		}
		if (head.equalsIgnoreCase("save")) {
			if (args.length == 3) {
				CocoaUI.getPicturesManager().getPluginPictureManager(args[2]).save();
			}
		}

	}
}

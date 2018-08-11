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
		if (head.equalsIgnoreCase("send")) {
			CocoaUI.getPicturesManager().sendUpdatePackage((Player) sender);
			sender.sendMessage("已发送");
		}
		if (head.equalsIgnoreCase("setURL")) {
			if (args.length == 5) {
				CocoaUI.getPicturesManager().getPluginPictureManager(args[2]).setURL(args[3], args[4]);
			}
		}
		if (head.equalsIgnoreCase("remove")) {
			if (args.length == 4) {
				CocoaUI.getPicturesManager().getPluginPictureManager(args[2]).removePicture(args[3]);
			}
		}
		if (head.equalsIgnoreCase("updateUrl")) {
			if (args.length == 4) {
				CocoaUI.getPicturesManager().sendUpdateRequest((Player) sender, args[2], args[3]);
				sender.sendMessage("请在弹出的窗口里选择图片");
			}
		}

	}
}

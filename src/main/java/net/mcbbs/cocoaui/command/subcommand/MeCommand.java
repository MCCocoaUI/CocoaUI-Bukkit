package net.mcbbs.cocoaui.command.subcommand;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.command.SubCommand;
import net.mcbbs.cocoaui.resource.Resource;
import net.mcbbs.cocoaui.resource.ResourceType;

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
				CocoaUI.getResourcesManager().registerPlugin(args[2]);
				sender.sendMessage("Plugin Created");
			}
		}
		if (head.equalsIgnoreCase("addp")) {
			if (args.length == 5) {
				CocoaUI.getResourcesManager().getPluginResourceManager(args[2]).loadResource(new Resource(args[3], args[4], ResourceType.PICTURE));
			}
			sender.sendMessage("Created: Name: " + args[3] + " URL: " + args[4] + " Type: Picture");
			if (args.length == 6) {
				CocoaUI.getResourcesManager().getPluginResourceManager(args[2]).loadResource(new Resource(args[3], args[4], ResourceType.toResourceType(args[5])));
				sender.sendMessage("Created: Name: " + args[3] + " URL: " + args[4] + " Type: " + args[5]);
			}
		}
		if (head.equalsIgnoreCase("lookup")) {
			if (args.length == 3) {
				sender.sendMessage(CocoaUI.getResourcesManager().getPluginResourceManager(args[2]).getPackage().toString());
			}
		}
		if (head.equalsIgnoreCase("save")) {
			if (args.length == 3) {
				CocoaUI.getResourcesManager().getPluginResourceManager(args[2]).save();
				sender.sendMessage("Save Done");
			}
		}
		if (head.equalsIgnoreCase("send")) {
			CocoaUI.getResourcesManager().sendUpdatePackage((Player) sender);
			sender.sendMessage("已发送");
		}
		if (head.equalsIgnoreCase("setURL")) {
			if (args.length == 5) {
				CocoaUI.getResourcesManager().getPluginResourceManager(args[2]).setURL(args[3], args[4]);
				sender.sendMessage("URLSetted");
			}
		}
		if (head.equalsIgnoreCase("remove")) {
			if (args.length == 4) {
				CocoaUI.getResourcesManager().getPluginResourceManager(args[2]).removeResource(args[3]);
				sender.sendMessage("removed");
			}
		}
		if (head.equalsIgnoreCase("updateUrl")) {
			if (args.length == 4) {
				CocoaUI.getResourcesManager().sendUpdateRequest((Player) sender, args[2], args[3]);
				sender.sendMessage("请在弹出的窗口里选择图片");
			}
		}

	}
}

package net.mcbbs.cocoaui.command;

import com.google.common.collect.Maps;
import net.mcbbs.cocoaui.command.subcommand.HelpCommand;
import net.mcbbs.cocoaui.command.subcommand.MeCommand;
import net.mcbbs.cocoaui.command.subcommand.PictureCommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;

/**
 * 用于管理可爱的子命令
 * <p>
 * Used to manage SubCommands
 *
 * @author Vlvxingze
 * @since 2018-08-05
 */
public class CommandHandler implements CommandExecutor {

	/**
	 * 对应关系: 子命令名 -> 子命令实现类
	 */
	private Map<String, SubCommand> commandMap = Maps.newHashMap();

	/**
	 * 初始化所有子命令
	 * <p>
	 * Initialize all sub commands
	 */
	public CommandHandler() {
		this.registerCommand("help", new HelpCommand());
		this.registerCommand("me", new MeCommand());
		this.registerCommand("picture", new PictureCommand());
	}

	/**
	 * 用于注册子命令
	 * <p>
	 * Used to register SubCommand
	 *
	 * @param commandName 子命令名
	 * @param subCommand  子命令实现类
	 */
	private void registerCommand(String commandName, SubCommand subCommand) {
		if (commandMap.containsKey(commandName)) {
			Bukkit.getLogger().warning("duplicate add command!");
		}
		commandMap.put(commandName, subCommand);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// 默认返回help子命令的内容
		if (args.length == 0) {
			commandMap.get("help").execute(sender, args);
			return true;
		}
		if (!commandMap.containsKey(args[0])) {
			sender.sendMessage("[CocoaUI]§c未知的命令!");
			return true;
		}

		commandMap.get(args[0]).execute(sender, args);

		return true;
	}
}

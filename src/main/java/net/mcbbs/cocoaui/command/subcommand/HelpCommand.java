package net.mcbbs.cocoaui.command.subcommand;

import net.mcbbs.cocoaui.command.SubCommand;
import net.mcbbs.cocoaui.utils.tellraw.HelpLine;
import net.mcbbs.cocoaui.utils.tellraw.HelpPage;

import org.bukkit.command.CommandSender;

/**
 * @author Zoyn
 * @since 2018-08-05
 */
public class HelpCommand implements SubCommand {
	private HelpPage mainPage;
    @Override
    public void execute(CommandSender sender, String[] args) {
		mainPage = new HelpPage("CocoaUI 管理器");
		mainPage.setClear(true);
		mainPage.addLine(new HelpLine("§c这里是CocoaUI的管理界面"));
		mainPage.addLine(new HelpLine("  §c✎ §a进入图片管理器", "/CocoaUI picmanager", "§c上传|修改|查看\n\n§c>>> §a点击查看"));
		
    }

	public static void main(String[] args) {

		
	}
}

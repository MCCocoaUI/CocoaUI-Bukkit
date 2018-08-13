package net.mcbbs.cocoaui.command;

import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.utils.tellraw.HelpLine;
import net.mcbbs.cocoaui.utils.tellraw.HelpPage;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public enum StaticPages {
	MainPage(new HelpPage("CocoaUI 管理器").setClear(true).addLine(new HelpLine("§c这里是CocoaUI的管理界面\n§a你可以用鼠标点击选项来进行操作哦"))
			.addLine(new HelpLine("  §c✎ §a进入图片管理器", "/CocoaUI picture", "§c上传|修改|查看\n\n§c>>> §a点击查看"))),
	PictureMainPage(new HelpPage("CocoaUI 图片管理器").setClear(true)
			.addLine(new HelpLine("§c这里是CocoaUI的图片管理界面\n§a你可以用鼠标点击选项来进行操作哦"))
			.addLine(new HelpLine(" §c<<< §a返回主菜单", "/CocoaUI", "§c点击返回主菜单"))
			.addLine(new HelpLine("  §c✎ §a列出插件列表", "/CocoaUI picture list plugin 1", "§c列出含有图片的附属插件列表\n§c打开后你可以在里面编辑附属插件的图片\n\n§c>>> §a点击查看")
					.addExtra("              ")
					.addExtra(new HelpLine("§e列出自定义列表", "/CocoaUI picture list custom 1", "§c列出含有所有自定义分类的图片列表\n§c打开后你可以在里面编辑自定义的图片\n\n\n§c>>> §a点击查看")))
			.addLine(new HelpLine("  §c✎ §a新建一个自定义分类", Action.SUGGEST_COMMAND, "/CocoaUI picture create","§c新建一个自定义的图片分类\n§c以便于您制作GUI。\n§a请您在补全指令后，输入名称按下回车即可创建\n\n§c>>> §a点击补全指令"))),
	UnknownPage_List_Plugin(new HelpPage("CocoaUI 警告信息").setClear(true)
			.addLine(new HelpLine("§c警告：您输入的页数无效"))
			.addLine(new HelpLine("§a下面是关于本警告的处理方案："))
			.addLine(new HelpLine("§c<= §e打开插件列表第一页","/cui picture list plugin 1","§c<= §a点击执行"))
			.addLine(new HelpLine("§c<= §b打开图片管理器","/cui picture","§c<= §a点击执行"))
			.addLine(new HelpLine("§c<= §a打开主菜单","/cui","§c<= §a点击执行"))
			),
	UnknownPage_Custom_Plugin(new HelpPage("CocoaUI 警告信息").setClear(true)
			.addLine(new HelpLine("§c警告：您输入的页数无效"))
			.addLine(new HelpLine("§a下面是关于本警告的处理方案："))
			.addLine(new HelpLine("§c<= §e打开自定义分类列表第一页","/cui picture list custom 1","§c<= §a点击执行"))
			.addLine(new HelpLine("§c<= §b打开图片管理器","/cui picture","§c<= §a点击执行"))
			.addLine(new HelpLine("§c<= §a打开主菜单","/cui","§c<= §a点击执行"))
			),
	noContent(new HelpPage("CocoaUI 提醒").setClear(true)
			.addLine(new HelpLine("§c提醒：这个列表里并没有可显示的信息。。"))
			.addLine(new HelpLine("§a下面是关于本提醒的建议："))
			.addLine(new HelpLine("§c<= §a打开主菜单","/cui","§c<= §a点击执行"))
			),
	UnknownType_Pic_list(new HelpPage("CocoaUI 警告信息").setClear(true)
			.addLine(new HelpLine("§c警告：列表类型无效，只能是Custom或Plugin"))
			.addLine(new HelpLine("§a下面是关于本警告的处理方案："))
			.addLine(new HelpLine("§c<= §e打开自定义分类列表第一页","/cui picture list custom 1","§c<= §a点击执行"))
			.addLine(new HelpLine("§c<= §e打开插件分类列表第一页","/cui picture list plugin 1","§c<= §a点击执行"))
			.addLine(new HelpLine("§c<= §b打开图片管理器","/cui picture","§c<= §a点击执行"))
			.addLine(new HelpLine("§c<= §a打开主菜单","/cui","§c<= §a点击执行"))
			);

	private HelpPage page;

	private StaticPages(HelpPage page) {
		this.page = page;
	}

	public void show(Player p) {
		this.page.send(p);
	}

	public static void sendUnknownCommand(Player p, String rootCommand, String[] arg3) {
		HelpPage page = new HelpPage("CocoaUI 警告信息");
		StringBuilder builder = new StringBuilder();
		builder.append("/");
		builder.append(rootCommand);
		builder.append(" ");
		for (String s1 : arg3) {
			builder.append(s1 + " ");
		}
		page.addLine(new HelpLine("§c似乎遇到了一些问题，您输入的指令的用法不正确。"));
		page.addLine(new HelpLine("§a<= 点击自动补全刚刚的指令，再试一次", new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, builder.toString()),
				new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§a重新进行打开操作\n\n§c<= §a点击执行").create())));
		page.addLine(new HelpLine("§c<= §a打开主菜单", new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cui"),
				new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§c<= §a点击执行").create())));
		page.addLine(new HelpLine("§c------------------------------------------------------------"));
		page.send(p);
	}
}

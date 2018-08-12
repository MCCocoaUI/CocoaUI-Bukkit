package net.mcbbs.cocoaui.command;

import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.utils.tellraw.HelpLine;
import net.mcbbs.cocoaui.utils.tellraw.HelpPage;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public enum StaticPages {
	// MainPage
	MainPage(new HelpPage("CocoaUI 管理器").setClear(true).addLine(new HelpLine("§c这里是CocoaUI的管理界面\n§a你可以用鼠标点击选项来进行操作哦"))
			.addLine(new HelpLine("  §c✎ §a进入图片管理器", "/CocoaUI picture", "§c上传|修改|查看\n\n§c>>> §a点击查看"))),
	// PictureMainPage
	PictureMainPage(new HelpPage("CocoaUI 图片管理器").setClear(true).addLine(new HelpLine("§c这里是CocoaUI的图片管理界面\n§a你可以用鼠标点击选项来进行操作哦"))
			.addLine(new HelpLine(" §c<<< §a返回主菜单","/CocoaUI","§c点击返回主菜单"))
			.addLine(new HelpLine("  §c✎ §a列出插件列表", "/CocoaUI picture list plugins", "§c列出含有图片的附属插件列表\n§c打开后你可以在里面编辑附属插件的图片\n\n§c>>> §a点击查看").addExtra("              ")
					.addExtra(new HelpLine("§e列出自定义列表", "/CocoaUI picture list custom", "§c列出含有所有自定义分类的图片列表\n§c打开后你可以在里面编辑自定义的图片\n\n\n§c>>> §a点击查看")))
			.addLine(new HelpLine("  §c✎ §a新建一个自定义分类", Action.SUGGEST_COMMAND, "/CocoaUI picture create",
					"§c新建一个自定义的图片分类\n§c以便于您制作GUI。\n§a请您在补全指令后，输入名称按下回车即可创建\n\n§c>>> §a点击补全指令")));

	private HelpPage page;

	private StaticPages(HelpPage page) {
		this.page = page;
	}

	public void show(Player p) {
		this.page.send(p);
	}
}

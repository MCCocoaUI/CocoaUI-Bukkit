package net.mcbbs.cocoaui.command.subcommand;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.command.StaticPages;
import net.mcbbs.cocoaui.command.SubCommand;
import net.mcbbs.cocoaui.util.tellraw.HelpLine;
import net.mcbbs.cocoaui.util.tellraw.HelpPage;

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
            if (args.length == 4) {
                int page = this.getInt(args[3]);
                if (page == Integer.MIN_VALUE) {
                    StaticPages.UnknownPage_List_Plugin.show(p);
                    return;
                }
                if (args[2].equalsIgnoreCase("plugin")) {
                    this.listPlugins(p, page, true);
                    return;
                }
                if (args[2].equalsIgnoreCase("custom")) {
                    this.listPlugins(p, page, false);
                    return;
                }
                StaticPages.UnknownType_Pic_list.show(p);
                return;
            }
            if (args.length == 3) {
                if (args[2].equalsIgnoreCase("plugin")) {
                    this.listPlugins(p, 1, true);
                }
                if (args[2].equalsIgnoreCase("custom")) {
                    this.listPlugins(p, 1, false);
                }
                StaticPages.UnknownType_Pic_list.show(p);
                return;
            }
        }
        StaticPages.sendUnknownCommand((Player) sender, "cui", args);
    }

    private void listPlugins(Player p, int page, boolean isPlugin) {
        List<String> list = (isPlugin ? CocoaUI.getResourcesManager().getPlugins() : CocoaUI.getResourcesManager().getCustoms());
        System.out.println(list);
        if (list.size() == 0) {
            StaticPages.noContent.show(p);
            return;
        }
        int maxPage = list.size() / 5 + 1;
        if (page > maxPage) {
            page = maxPage;
        }
        if (page < 1) {
            page = 1;
        }
        int now = (page - 1) * 5;
        HelpPage hp = new HelpPage("CocoaUI 图片管理器 - §a" + (isPlugin ? "插件" : "自定义分类") + "§c列表").setClear(true);
        hp.addLine(new HelpLine("§a当前页面 §b(" + page + " / " + maxPage + ") §a，共加载了 §c" + list.size() + " §a个附属插件。"));
        int i = 0;
        while (i <= 4) {
            if (now > list.size() - 1) {
                break;
            }
            String name = list.get(now);
            hp.addLine(new HelpLine("  §a第 §c(" + (now + 1) + " / " + list.size() + ")§a 个 §b附属插件：§d " + name, "/cui picture show " + name,
                    "§a点击查看这个" + (isPlugin ? "插件" : "自定义分类") + "下的所有图片\n§a对图片进行重新上传等操作\n\n\n§c>>> §a点击查看"));
            i++;
            now++;
        }
        HelpLine up = null, down = null;
        if (page < maxPage) {
            down = new HelpLine("§c下一页§a=>", "/cui list plugins " + (page + 1), "§c点击进入下一页");
        } else {
            down = new HelpLine("§c下一页§a=>", "§c似乎没有下一页了呢。。");
        }
        if (page > 1) {
            up = new HelpLine("§a<=§c上一页", "/cui list plugins " + (page - 1), "§c点击进入上一页");
        } else {
            up = new HelpLine("§a<=§c上一页", "§c似乎没有上一页了呢。。");
        }
        hp.addLine(up.addExtra("                                      ").addExtra(down));
        hp.send(p);
    }

    private int getInt(String s) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            return Integer.MIN_VALUE;
        }
    }

}

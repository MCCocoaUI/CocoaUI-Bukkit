package net.mcbbs.cocoaui.utils.tellraw;

import java.util.LinkedHashSet;

import org.bukkit.entity.Player;

import com.google.common.collect.Sets;

public class HelpPage implements Cloneable {

    LinkedHashSet<HelpLine> set = Sets.newLinkedHashSet();
    private String head;
    private boolean clear;
    private static final String UP = "§a--------------------------------";
    private static final String END = "§a------------------------------------------------------------------";
    private static final String CLEAR = "\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n \n";

    private HelpPage(LinkedHashSet<HelpLine> set) {
        this.set = set;
    }

    public void setClear(boolean b) {
        this.clear = b;
    }

    public HelpPage(String title) {
        char[] chars = title.toCharArray();
        int length = title.length();
        int chinesechar = 0;
        for (char c : chars) {
            if (c == '§') {
                length = length - 2;
            }
            if ((c >= 0x4e00) && (c <= 0x9fbb)) {
                length++;
                chinesechar++;
            }
        }
        if (chinesechar != 0) {
            length = length + chinesechar / 3;
        }
        String finalup = UP.substring(0, UP.length() - (length / 2));
        head = finalup + " §c" + title + " " + finalup;
    }

    public HelpPage addLine(HelpLine l) {
        this.set.add(l);
        return this;
    }

    public void send(Player p) {
        if (this.clear) {
            p.sendMessage(CLEAR);
        }
        p.sendMessage(head);
        for (HelpLine line : set) {
            line.send(p);
        }
        p.sendMessage(END);
    }

    public void increase(int loc, String message) {
        int nowloc = 0;
        LinkedHashSet<HelpLine> set = Sets.newLinkedHashSet();
        HelpLine forincrease = new HelpLine(message);
        for (HelpLine hl : this.set) {
            set.add(hl);
            if (loc == nowloc) {
                set.add(forincrease);
            }
            nowloc++;
        }
        this.set = set;
    }

    public HelpPage clone() {
        return new HelpPage(this.set);
    }

}

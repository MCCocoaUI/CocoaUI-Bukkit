package net.mcbbs.cocoaui.util.tellraw;

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
        this.load();
    }

    public void load() {
    }

    public HelpPage setClear(boolean b) {
        this.clear = b;
        return this;
    }

    public HelpPage(String title) {
        char[] chars = title.toCharArray();
        int length = title.length();
        int chineseChar = 0;
        for (char c : chars) {
            if (c == '§') {
                length = length - 2;
            }
            if ((c >= 0x4e00) && (c <= 0x9fbb)) {
                length++;
                chineseChar++;
            }
        }
        if (chineseChar != 0) {
            length = length + chineseChar / 3;
        }
        String finalUp = UP.substring(0, UP.length() - (length / 2));
        head = finalUp + " §c" + title + " " + finalUp;
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
        int nowLoc = 0;
        LinkedHashSet<HelpLine> set = Sets.newLinkedHashSet();
        HelpLine forIncrease = new HelpLine(message);
        for (HelpLine hl : this.set) {
            set.add(hl);
            if (loc == nowLoc) {
                set.add(forIncrease);
            }
            nowLoc++;
        }
        this.set = set;
    }

    public HelpPage clone() {
        return new HelpPage(this.set);
    }

}

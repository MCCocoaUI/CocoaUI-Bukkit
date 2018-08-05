package net.mcbbs.cocoaui.utils.tellraw;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class HelpLine {
	private TextComponent text = null;

	public HelpLine(String message) {
		text = new TextComponent(message);
	}

	public HelpLine(TextComponent e) {
		this.text = e;
	}

	public HelpLine(String message, ClickEvent clickevent, HoverEvent hoverevent) {
		text = new TextComponent(message);
		text.setBold(true);
		text.setClickEvent(clickevent);
		text.setHoverEvent(hoverevent);
	}

	public HelpLine(String message, String command, String hoverevent) {
		text = new TextComponent(message);
		text.setBold(true);
		if (command != null) {
			text.setClickEvent(new ClickEvent(Action.RUN_COMMAND, command));
		}
		if (hoverevent != null) {
			text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverevent).create()));
		}
	}

	public HelpLine(String message, Action action, String command, String hoverevent) {
		text = new TextComponent(message);
		text.setBold(true);
		if (command != null) {
			text.setClickEvent(new ClickEvent(action, command));
		}
		if (hoverevent != null) {
			text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverevent).create()));
		}
	}

	public TextComponent getText() {
		return this.text;
	}

	public HelpLine addExtra(String t) {
		this.text.addExtra(t);
		return this;
	}

	public HelpLine addExtra(TextComponent t) {
		this.text.addExtra(t);
		return this;
	}

	public void send(Player p) {
		p.spigot().sendMessage(this.text);
	}

}

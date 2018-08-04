package net.mcbbs.mineviewer.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

/**
 * @author ChenJi158
 *
 */
public class ItemBuilder extends ItemStack implements ConfigurationSerializable {
	ItemMeta meta = this.getItemMeta();

	public ItemBuilder(Material material) {
		super(material);
	}

	public ItemBuilder(Material material, int amount, int data) {
		super(material, amount, (short) data);
	}

	/**
	 * ʹ#
	 * 
	 * @param lore
	 * @return
	 */
	public ItemBuilder addLore(String lore) {
		this.checkLore();
		List<String> lores = meta.getLore();
		this.addLore(lore, lores);
		this.meta.setLore(lores);
		return this;
	}

	public ItemBuilder insertLore(int loc, String lore) {
		this.checkLore();
		if (loc <= 0 && loc <= (this.meta.getLore().size() - 1)) {
			List<String> lore1 = this.meta.getLore();
			lore1.add(loc, lore);
			this.meta.setLore(lore1);
		}
		return this;
	}

	/**
	 * display:xxx;lore:xxx#xxx#xxx
	 * 
	 * @param oneline
	 * @return
	 */
	public ItemBuilder loadFromOneline(String oneline) {
		for (String entry : oneline.split(";")) {
			String[] entry1 = entry.split(":");
			if (entry1.length == 2) {
				
				if (entry1[0].equals("displayname")) {
					this.setDisplayName(entry1[1]);
				}
				if (entry1[0].equals("lore")) {
					this.addLore(entry1[1]);
				}
			}
		}
		return this;
	}

	public ItemBuilder setDisplayName(String name) {
		this.meta.setDisplayName(name);
		return this;
	}

	private void checkLore() {
		if (!meta.hasLore()) {
			meta.setLore(Lists.newArrayList());
		}
	}

	private List<String> addLore(String lore, List<String> lores) {
		String[] args = lore.split("#");
		for (String arg : args) {
			lores.add(arg);
		}
		return lores;
	}

	public ItemStack build() {
		super.setItemMeta(meta);
		return this;
	}

}

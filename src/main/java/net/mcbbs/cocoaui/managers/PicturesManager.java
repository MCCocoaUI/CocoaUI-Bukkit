package net.mcbbs.cocoaui.managers;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.mcbbs.cocoaui.managers.picturemanager.Picture;
import net.mcbbs.cocoaui.utils.config.AbstractConfiguration;
import net.mcbbs.cocoaui.utils.config.ConfigException;

class PicturesManager extends AbstractConfiguration {
	private Map<String, Map<String, Picture>> pictures = Maps.newHashMap();

	public PicturesManager() throws ConfigException {
		super("pictures.yml", false, "pictures.yml created", "cannot create pictures.yml");
	}

	@Override
	public void loadConfig() {
		for (String name : super.getConfig().getKeys(false)) {
			this.loadType(name);
		}
	}

	private void loadType(String typename) {
		for (String name : super.getConfig().getConfigurationSection(typename).getKeys(false)) {
			this.loadPicture((Picture) super.getConfig().get(name));
		}
	}

	public void checkCreateType(String name) {
		if (!this.pictures.containsKey(name)) {
			this.pictures.put(name, Maps.newHashMap());
		}
	}

	public boolean sameNameCheck(String name, String type) {
		return this.pictures.containsKey(type) ? this.pictures.get(type).containsKey(name) : false;
	}

	public boolean setType(String oldtype, String name, String newType) {
		if (this.pictures.containsKey(oldtype)) {
			if (this.pictures.get(oldtype).containsKey(name)) {
				if (!this.sameNameCheck(name, newType)) {
					Picture newpic = this.pictures.get(oldtype).get(name).clone();
					this.pictures.get(oldtype).remove(name);
					newpic.setType(newType);
					this.loadPicture(newpic);
					return true;
				}

			}
		}
		return false;
	}

	public boolean setName(String type, String name, String newName) {
		if (this.pictures.containsKey(type)) {
			if (this.pictures.get(type).containsKey(name)) {
				if (!this.sameNameCheck(newName, type)) {
					Picture newpic = this.pictures.get(type).get(name).clone();
					this.pictures.get(type).remove(name);
					newpic.setName(newName);
					this.loadPicture(newpic);
					return true;
				}

			}
		}
		return false;
	}

	public boolean setDescription(String type, String name, String description) {
		if (this.pictures.containsKey(type)) {
			if (this.pictures.get(type).containsKey(name)) {
				this.pictures.get(type).get(name).setDescription(description);
				return true;
			}
		}
		return false;
	}

	public boolean setURL(String type, String name, String url) {
		if (this.pictures.containsKey(type)) {
			if (this.pictures.get(type).containsKey(name)) {
				this.pictures.get(type).get(name).setUrl(url);
				return true;
			}
		}
		return false;
	}

	public Collection<Picture> getPictures(String type) {
		if (this.pictures.containsKey(type)) {
			return this.pictures.get(type).values();
		}
		return Sets.newHashSet();
	}

	public void saveData() {
		for (Entry<String, Map<String, Picture>> entry : this.pictures.entrySet()) {
			this.saveType(entry.getValue(), entry.getKey() + ".");
		}
		try {
			super.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void saveType(Map<String, Picture> type, String rootpath) {
		for (Entry<String, Picture> entry : type.entrySet()) {
			super.getConfig().set(rootpath + entry.getKey(), entry.getValue());
		}
	}

	public boolean loadPicture(Picture p) {
		this.checkCreateType(p.getType());
		this.pictures.get(p.getType()).put(p.getName(), p);
		return true;
	}

	public Set<String> getTypes() {
		return this.pictures.keySet();
	}

	public Set<String> getNames(String type) {
		if (this.pictures.containsKey(type)) {
			return this.pictures.get(type).keySet();
		}
		return Sets.newHashSet();
	}

	public boolean removeType(String type) {
		if (this.pictures.containsKey(type)) {
			this.pictures.remove(type);
			return true;
		}
		return false;
	}

	public boolean removePicture(String name, String type) {
		if (this.sameNameCheck(name, type)) {
			this.pictures.get(type).remove(name);
			return true;
		}
		return false;
	}

}
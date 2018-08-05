package net.mcbbs.cocoaui.managers.picturemanager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.Maps;

public class Picture implements ConfigurationSerializable, Cloneable {
	private static String NAME = "name";
	private static String URL = "url";
	private static String DESCRIPTION = "description";
	private static String TYPE = "type";
	private static String LOCK = "lock";
	private static String HEIGHT = "height";
	private static String WIDTH = "width";
	private String name;
	private String url;
	private String description;
	private String type;
	private boolean lock;
	private int height;
	private int width;

	public Picture(Map<String, Object> obj) {
		this.name = (String) obj.get(NAME);
		this.type = (String) obj.get(TYPE);
		this.url = (String) obj.get(URL);
		this.description = (String) obj.get(DESCRIPTION);
		this.lock = (boolean) obj.get(LOCK);
		this.height = (int) obj.get(HEIGHT);
		this.width = (int) obj.get(WIDTH);
	}

	public Picture(String name, String url) {
		this(name, url, "default", "nowrite");
	}

	public Picture(String name, String url, String type) {
		this(name, url, type, "nowrite");
	}

	public Picture(String name, String url, String type, String description) {
		this.name = name;
		this.url = url;
		this.type = type;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public final Picture clone() {
		try {
			return (Picture) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put(Picture.NAME, this.name);
		map.put(Picture.URL, this.url);
		map.put(Picture.DESCRIPTION, this.description);
		map.put(Picture.TYPE, this.type);
		map.put(Picture.LOCK, this.lock);
		map.put(Picture.HEIGHT, this.height);
		map.put(Picture.WIDTH, this.width);
		return map;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}

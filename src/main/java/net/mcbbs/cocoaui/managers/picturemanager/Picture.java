package net.mcbbs.cocoaui.managers.picturemanager;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.Maps;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.mcbbs.cocoaui.utils.MD5Tool;

public class Picture implements ConfigurationSerializable, Cloneable {
	private static final String NAME = "name";
	private static final String URL = "url";
	private static final String DESCRIPTION = "description";
	private static final String TYPE = "type";
	private static final String LOCK = "lock";
	private static final String HEIGHT = "height";
	private static final String WIDTH = "width";
	private static final String DEFAULTURL = "DEFAULTURL";
	private static final String MD5 = "md5";
	private String name;
	private String url;
	private String description;
	private String type;
	private boolean lock;
	private int height;
	private int width;
	private String defaultURL;
	private String md5;

	public Picture(Map<String, Object> obj) {
		this.name = (String) obj.get(NAME);
		this.type = (String) obj.get(TYPE);
		this.url = (String) obj.get(URL);
		this.description = (String) obj.get(DESCRIPTION);
		this.lock = (boolean) obj.get(LOCK);
		this.height = (int) obj.get(HEIGHT);
		this.width = (int) obj.get(WIDTH);
		this.defaultURL = (String) obj.get(DEFAULTURL);
		this.md5 = (String) obj.get(MD5);
		this.loadSize();
		this.loadMD5();
	}

	public String getMD5() {
		return this.md5;
	}

	public Picture(String name, String defaultURL) {
		this(name, defaultURL, "default", "nowrite");
	}

	public Picture(String name, String defaultURL, String type) {
		this(name, defaultURL, type, "nowrite");
	}

	public Picture(String name, String defaultURL, String type, String description) {
		this(name, defaultURL, type, description, true);
	}

	public Picture(String name, String defaultURL, String type, String description, boolean lock) {
		this.name = name;
		this.defaultURL = defaultURL;
		this.url = this.defaultURL;
		this.type = type;
		this.description = description;
		this.lock = lock;
		this.loadSize();
		this.loadMD5();
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
		this.loadMD5();
		this.loadSize();
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
		map.put(Picture.MD5, this.md5);
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

	public String getDefaultURL() {
		return this.defaultURL;
	}

	private void loadMD5() {
		try (InputStream in = new URI(this.getUrl()).toURL().openStream()) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			byte[] bs = new byte[16384];
			int len;
			while ((len = in.read(bs)) != -1) {
				out.write(bs, 0, len);
			}
			byte[] bytes = out.toByteArray();
			this.md5 = MD5Tool.md5(bytes);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}

	}

	private void loadSize() {
		BufferedImage img;
		try {
			img = ImageIO.read(new URI(this.getUrl()).toURL().openStream());
			this.width = img.getWidth();
			this.height = img.getHeight();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public String getPictureID(String pluginName) {
		return pluginName + "." + this.name;
	}

}

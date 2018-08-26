package net.mcbbs.cocoaui.resource;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.Maps;

/**
 * 资源信息记录类
 *
 * @author ChenJi
 *
 */
public class Resource implements ConfigurationSerializable, Cloneable {

	private static final String NAME = "name";
	private static final String URL = "url";
	private static final String DESCRIPTION = "description";
	private static final String LOCK = "lock";
	private static final String HEIGHT = "height";
	private static final String WIDTH = "width";
	private static final String DEFAULT_URL = "DEFAULT_URL";
	private static final String MD5 = "md5";
	private static final String TYPE = "type";
	private String name;
	private String url;
	private String description;
	private boolean lock;
	private int height;
	private int width;
	private String defaultURL;
	private String md5;
	private ResourceType type;
	private String pluginName;

	/**
	 * ConfigurationSerializable 接口
	 *
	 * @param obj
	 */
	public Resource(Map<String, Object> obj) {
		this.name = (String) obj.get(NAME);
		this.url = (String) obj.get(URL);
		this.description = (String) obj.get(DESCRIPTION);
		this.lock = (boolean) obj.get(LOCK);
		this.height = (int) obj.get(HEIGHT);
		this.width = (int) obj.get(WIDTH);
		this.defaultURL = (String) obj.get(DEFAULT_URL);
		this.md5 = (String) obj.get(MD5);
		this.type = ResourceType.toResourceType((String) obj.get(TYPE));

	}

	/**
	 * 获取资源的类型
	 *
	 * @return 资源类型
	 */
	public ResourceType getType() {
		return this.type;
	}

	/**
	 * 获取资源的MD5
	 *
	 * @return md5
	 */
	public String getMD5() {
		return this.md5;
	}

	/**
	 * 构造函数
	 *
	 * @param name       名字
	 * @param defaultURL 默认地址
	 */
	public Resource(String name, String defaultURL, ResourceType type) {

		this(name, defaultURL, "nowrite", type);
	}

	/**
	 * 构造函数
	 *
	 * @param name        名字
	 * @param defaultURL  默认地址
	 * @param description 叙述
	 */
	public Resource(String name, String defaultURL, String description, ResourceType type) {
		this(name, defaultURL, description, true, type);
	}

	/**
	 * 构造函数
	 *
	 * @param name        名字
	 * @param defaultURL  默认地址
	 * @param description 叙述
	 * @param lock        是否锁定
	 */
	public Resource(String name, String defaultURL, String description, boolean lock, ResourceType type) {
		if (name == null) {
			throw new NullPointerException("name cannot be null");
		}
		this.name = name;
		if (defaultURL == null) {
			throw new NullPointerException("name cannot be null");
		}
		this.defaultURL = defaultURL;
		this.url = this.defaultURL;
		this.description = description;
		this.lock = lock;
		if (type == null) {
			throw new NullPointerException("name cannot be null");
		}
		this.type = type;

	}

	/**
	 * 获取资源的名称
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取资源的地址
	 *
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 重新设置资源的地址
	 *
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取资源的介绍：暂未开发
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 重新设置资源的介绍
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Clone 不解释
	 */
	public final Resource clone() {
		try {
			return (Resource) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> serialize() {
		HashMap<String, Object> map = Maps.newHashMap();
		map.put(Resource.NAME, this.name);
		map.put(Resource.URL, this.url);
		map.put(Resource.DESCRIPTION, this.description);
		map.put(Resource.LOCK, this.lock);
		map.put(Resource.HEIGHT, this.height);
		map.put(Resource.WIDTH, this.width);
		map.put(Resource.MD5, this.md5);
		map.put(Resource.TYPE, this.type.toString());
		return map;
	}

	/**
	 * 返回是否是锁定状态
	 *
	 * @return
	 */
	public boolean isLock() {
		return lock;
	}

	/**
	 * 设定锁定状态
	 *
	 * @param lock
	 */
	public void setLock(boolean lock) {
		this.lock = lock;
	}

	/**
	 * 获取资源的高
	 *
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * 获取资源的宽
	 *
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * 获取默认的链接
	 *
	 * @return
	 */
	public String getDefaultURL() {
		return this.defaultURL;
	}

	/**
	 * 设定资源信息
	 *
	 * @param info
	 */
	public void setInfo(ResourceInfo info) {
		this.md5 = info.getMd5();
		this.width = info.getWidth();
		this.height = info.getHeight();
	}

	public boolean check() {
		return this.md5 == null;
	}
	/**
	 * 获取所属插件名称
	 * @return 插件名
	 */
	public String getPluginName() {
		return pluginName;
	}

	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
	/**
	 * 获取完整的资源名
	 * @return 资源名
	 */
	public String getFullName() {
		return this.pluginName + "." + this.name;
	}

}

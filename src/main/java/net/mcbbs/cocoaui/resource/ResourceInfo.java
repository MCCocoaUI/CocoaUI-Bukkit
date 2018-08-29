package net.mcbbs.cocoaui.resource;

/**
 * 图片信息回传记录类 内部使用
 *
 * @author ChenJi
 *
 */
public class ResourceInfo {

	public ResourceInfo(String url, String md5, int width, int height, String name, String pluginName) {
		super();
		this.url = url;
		this.md5 = md5;
		this.width = width;
		this.height = height;
		this.name = name;
		this.pluginName = pluginName;
	}

	private String url;
	private String md5;
	private int width;
	private int height;
	private String name;
	private String pluginName;

	String getUrl() {
		return url;
	}

	void setUrl(String url) {
		this.url = url;
	}

	String getMd5() {
		return md5;
	}

	void setMd5(String md5) {
		this.md5 = md5;
	}

	int getWidth() {
		return width;
	}

	void setWidth(int width) {
		this.width = width;
	}

	int getHeight() {
		return height;
	}

	void setHeight(int height) {
		this.height = height;
	}

	String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	String getPluginName() {
		return pluginName;
	}

	void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
}

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }
}

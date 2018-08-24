package net.mcbbs.cocoaui.managers.picturemanager;

/**
 * 图片信息回传记录类 内部使用
 *
 * @author ChenJi
 *
 */
public class PictureInfo {

    public PictureInfo(String url, String md5, int width, int height, String name, String pluginName) {
        super();
        this.url = url;
        this.md5 = md5;
        this.width = width;
        this.height = height;
        this.name = name;
        this.pluginName = pluginName;
    }

    String url;
    String md5;
    int width;
    int height;
    String name;
    String pluginName;
}

package net.mcbbs.cocoaui.managers.picturemanager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.common.collect.Maps;

/**
 * 图片信息记录类
 *
 * @author ChenJi
 *
 */
public class Picture implements ConfigurationSerializable, Cloneable {

    private static final String NAME = "name";
    private static final String URL = "url";
    private static final String DESCRIPTION = "description";
    private static final String LOCK = "lock";
    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";
    private static final String DEFAULTURL = "DEFAULTURL";
    private static final String MD5 = "md5";
    private String name;
    private String url;
    private String description;
    private boolean lock;
    private int height;
    private int width;
    private String defaultURL;
    private String md5;

    /**
     * ConfigurationSerializable 接口
     *
     * @param obj
     */
    public Picture(Map<String, Object> obj) {
        this.name = (String) obj.get(NAME);
        this.url = (String) obj.get(URL);
        this.description = (String) obj.get(DESCRIPTION);
        this.lock = (boolean) obj.get(LOCK);
        this.height = (int) obj.get(HEIGHT);
        this.width = (int) obj.get(WIDTH);
        this.defaultURL = (String) obj.get(DEFAULTURL);
        this.md5 = (String) obj.get(MD5);
    }

    /**
     * 获取图片的MD5
     *
     * @return md5
     */
    public String getMD5() {
        return this.md5;
    }

    /**
     * 构造函数
     *
     * @param name 名字
     * @param defaultURL 默认地址
     */
    public Picture(String name, String defaultURL) {
        this(name, defaultURL, "nowrite");
    }

    /**
     * 构造函数
     *
     * @param name 名字
     * @param defaultURL 默认地址
     * @param description 叙述
     */
    public Picture(String name, String defaultURL, String description) {
        this(name, defaultURL, description, true);
    }

    /**
     * 构造函数
     *
     * @param name 名字
     * @param defaultURL 默认地址
     * @param description 叙述
     * @param lock 是否锁定
     */
    public Picture(String name, String defaultURL, String description, boolean lock) {
        this.name = name;
        this.defaultURL = defaultURL;
        this.url = this.defaultURL;
        this.description = description;
        this.lock = lock;
    }

    /**
     * 获取图片的名称
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 获取图片的地址
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 重新设置图片的地址
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图片的介绍：暂未开发
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 重新设置图片的介绍
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Clone 不解释
     */
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
        map.put(Picture.LOCK, this.lock);
        map.put(Picture.HEIGHT, this.height);
        map.put(Picture.WIDTH, this.width);
        map.put(Picture.MD5, this.md5);
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
     * 获取图片的高
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * 获取图片的宽
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
     * 设定图片信息
     *
     * @param info
     */
    public void setInfo(PictureInfo info) {
        this.md5 = info.md5;
        this.width = info.width;
        this.height = info.width;
    }

    /**
     * 获取图片的完整名
     *
     * @param pluginName
     * @return
     */
    public String getPictureID(String pluginName) {
        return pluginName + "." + this.name;
    }

    public boolean check() {
        return this.md5 == null;
    }

}

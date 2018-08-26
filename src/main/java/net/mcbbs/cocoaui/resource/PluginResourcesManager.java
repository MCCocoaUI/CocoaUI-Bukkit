package net.mcbbs.cocoaui.resource;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.packages.OutResourceUpdate;
import net.mcbbs.cocoaui.pluginmessage.packages.OutSingleResourceUpdate;
import net.mcbbs.cocoaui.utils.config.AbstractConfiguration;
import net.mcbbs.cocoaui.utils.config.ConfigException;

/**
 * 插件资源管理器
 *
 * @author ChenJi
 *
 */
public final class PluginResourcesManager extends AbstractConfiguration {

    private Map<String, Resource> resoucres = Maps.newHashMap();
    private Map<String, ResourceChange> resourcechanges = Maps.newHashMap();
    private Set<String> picSet = Sets.newHashSet();
    private Set<String> fontSet = Sets.newHashSet();
    private Set<String> videoSet = Sets.newHashSet();
    private Set<String> musicset = Sets.newHashSet();
    private String pluginName;

    public PluginResourcesManager(String pluginName) throws ConfigException {
        super("resources/" + pluginName + ".yml", false, "pluginName.yml created", "cannot create resoucres.yml");
        this.pluginName = pluginName;
        CocoaUI.getLog().info("[PictureManager]正在加載 " + pluginName);

        this.loadConfig();
    }

    /**
     * 加載config
     */
    @Override
    public void loadConfig() {
        for (String name : super.getConfig().getKeys(false)) {
            Resource resource = (Resource) super.getConfig().get(name);
            this.resoucres.put(resource.getName(), resource);
            this.addSet(resource.getName(), resource.getType());
            this.check(resource);

        }
    }

    private void addSet(String name, ResourceType type) {
        switch (type) {
            case VIDEO:
                this.videoSet.add(name);
                return;
            case MUSIC:
                this.musicset.add(name);
                return;
            case PICTURE:
                this.picSet.add(name);
                return;
            case FONT:
                this.fontSet.add(name);
                return;
        }
    }

    private void removeSet(String name, ResourceType type) {
        switch (type) {
            case VIDEO:
                this.videoSet.remove(name);
                return;
            case MUSIC:
                this.musicset.remove(name);
                return;
            case PICTURE:
                this.picSet.remove(name);
                return;
            case FONT:
                this.fontSet.remove(name);
                return;
        }
    }

    private void check(Resource resource) {
        if (resource.check()) {
            resource.setLock(true);
            CocoaUI.getResourcesManager().reloadResourceInfo(resource.getUrl(), resource.getName(), pluginName, resource.getType().equals(ResourceType.PICTURE));
        }

    }

    /**
     * 重新设置URL
     *
     * @param url url
     * @param name name
     * @return 是否成功，资源未找到则返回false
     */
    public boolean setURL(String name, String url) {
        if (this.resoucres.containsKey(name)) {
            Resource res = this.resoucres.get(name);
            res.setUrl(url);
            CocoaUI.getResourcesManager().reloadResourceInfo(url, name, pluginName, res.getType().equals(ResourceType.PICTURE));
            this.resourcechanges.put(name, new ResourceChange(ResourceChange.SETURL, name, null));

            return true;
        }
        return false;
    }

    /**
     * 删除资源
     *
     * @param name 资源名称
     * @return 是否成功，资源未找到则返回false
     */
    public boolean removeResource(String name) {
        if (this.resoucres.containsKey(name)) {
            this.removeSet(name, this.resoucres.get(name).getType());
            this.resoucres.remove(name);
            this.resourcechanges.put(name, new ResourceChange(ResourceChange.REMOVE, name, null));
            this.update(name);

            return true;
        }
        return false;
    }

    /**
     * 通过名称获取资源 如果未找到则返回null
     *
     * @param name 资源名称
     * @return 资源
     */
    public Resource getResource(String name) {
        return this.resoucres.get(name);
    }

    /**
     * 获取这个管理器所属的插件名称
     *
     * @return 插件名称
     */
    public String getpluginName() {
        return this.pluginName;
    }

    /**
     * 直接加载资源并立即重载资源信息，注意如果出现同名会直接覆盖。
     *
     * @param resource 资源
     */
    protected void forceLoadResource(Resource resource) {
        if (this.resoucres.containsKey(resource.getName())) {
            return;
        }
        this.resoucres.put(resource.getName(), resource);
        this.addSet(resource.getName(), resource.getType());
        CocoaUI.getResourcesManager().reloadResourceInfo(resource.getUrl(), resource.getName(), pluginName, resource.getType().equals(ResourceType.PICTURE));
    }

    /**
     * 获取这个管理器内是否包含某个资源
     *
     * @param name 资源名称
     * @return 是否存在
     */
    public boolean contains(String name) {
        return this.resoucres.containsKey(name);
    }

    private Map<String, String> getPictureData(Resource p) {
        Map<String, String> map = Maps.newHashMap();
        map.put("name", p.getName());
        map.put("url", p.getUrl());
        map.put("type", p.getType().toString());
        map.put("md5", p.getMD5());
        return map;
    }

    /**
     * 获取这个管理器的更新包
     *
     * @return OutPictureUpdate
     */
    public OutResourceUpdate getPackage() {
        Map<String, Map<String, String>> content = Maps.newHashMap();
        for (Resource pic : this.resoucres.values()) {
            content.put(pic.getName(), this.getPictureData(pic));
        }
        Map<String, Object> back = Maps.newHashMap();
        back.put("pluginName", this.getpluginName());
        back.put("picAmount", this.resoucres.size());
        back.put("content", content);
        return new OutResourceUpdate(new Gson().toJson(back));
    }

    /**
     * 保存
     */
    public void save() {
        for (Resource pic : this.resoucres.values()) {
            super.getConfig().set(pic.getName(), pic);
        }
        try {
            super.saveConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取修改过的资源的更新包
     *
     * @param name 名称
     * @param p 资源
     * @return OutSinglePictureUpdate
     */
    public OutSingleResourceUpdate getUpdatePackage(String name, Resource p) {
        ResourceChange chenge = this.resourcechanges.get(name);
        return new OutSingleResourceUpdate(this.pluginName, p, chenge.state);
    }

    /**
     * 发送更新包
     *
     * @param name 资源信息
     */
    protected void update(String name) {
        if (this.resourcechanges.containsKey(name)) {
            if (this.resoucres.containsKey(name)) {
                Resource pic = this.resoucres.get(name);
                OutSingleResourceUpdate pack = this.getUpdatePackage(name, pic);
                CocoaUI.getPluginMessageManager().sendAll(pack);
            }
        }
    }

    protected void updateInfo(String name, ResourceInfo value) {
        if (this.resoucres.containsKey(name)) {
            if (this.resourcechanges.containsKey(name)) {
                this.update(name);
            }
            this.resoucres.get(name).setInfo(value);
        }

    }

    /**
     * 附属插件调用端口：加载资源
     *
     * @param resource resource
     * @return 是否含有重复的
     */
    public boolean loadResource(Resource resource) {
        if (this.resoucres.containsKey(resource.getName())) {
            return false;
        }
        this.resoucres.put(resource.getName(), resource);
        this.addSet(resource.getName(), resource.getType());
        return true;
    }

    /**
     * 获取所有资源
     *
     * @return
     */
    public Collection<Resource> getResoucres() {
        return resoucres.values();
    }

    /**
     * 获取所有图片资源的名称
     *
     * @return
     */
    public Set<String> getPicSet() {
        return picSet;
    }

    /**
     * 获取所有字体资源的名称
     *
     * @return
     */
    public Set<String> getFontSet() {
        return fontSet;
    }

    /**
     * 获取所有视频资源的名称
     *
     * @return
     */
    public Set<String> getVideoSet() {
        return videoSet;
    }

    /**
     * 获取所有音乐资源的名称
     *
     * @return
     */
    public Set<String> getMusicset() {
        return musicset;
    }

    /**
     * 获取插件名称
     *
     * @return
     */
    public String getPluginName() {
        return pluginName;
    }

}

class ResourceChange {

    public final static int REMOVE = 3;
    public final static int SETURL = 1;

    ResourceChange(int state, String now, String old) {
        this.state = state;
        this.nowName = now;
        this.oldName = old;
    }

    int state;
    String nowName;
    String oldName;
}

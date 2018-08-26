package net.mcbbs.cocoaui.resource;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.packages.OutOpenResourceChooser;
import net.mcbbs.cocoaui.pluginmessage.packages.OutResourceUpdateSent;
import net.mcbbs.cocoaui.util.config.ConfigException;

/**
 * 资源管理器主类
 *
 * @author ChenJi
 *
 */
public class ResourcesManager {

    private Map<String, PluginResourcesManager> resourceManagers = Maps.newHashMap();
    private Map<UUID, PictureEditor> resourceEditors = Maps.newHashMap();
    private Map<ResourceName, Future<ResourceInfo>> updateList = Maps.newHashMap();
    private ExecutorService pool = Executors.newFixedThreadPool(4);
    private Set<ResourceName> finish = Sets.newHashSet();
    private HashMap<ResourceName, ResourceInfoLoader> waitLoadList = Maps.newHashMap();
    private int loaded;
    private int timer = 0;
    private boolean firstLoad = true;
    private BukkitTask task;

    /**
     * init 不解釋
     */
    public void init() {
        this.loadManagers();
        CocoaUI.getLog().info("[ResourceManager]所有信息加载完成，等待异步验证 ");
    }

    /**
     * 重新设置URL
     *
     * @param p 玩家
     * @param url url
     * @return 是否成功，资源未找到则返回false
     */
    public boolean setURL(Player p, String url) {
        if (this.resourceEditors.containsKey(p.getUniqueId())) {
            PictureEditor editor = this.resourceEditors.get(p.getUniqueId());
            if (resourceManagers.containsKey(editor.getPluginName())) {
                return this.resourceManagers.get(editor.getPluginName()).setURL(editor.getName(), url);
            }
        }
        return false;
    }

    private void startTask() {
        this.task = Bukkit.getScheduler().runTaskTimer(CocoaUI.getPlugin(CocoaUI.class), new Runnable() {
            @Override
            public void run() {
                onTick();
            }
        }, 1L, 1L);
    }

    /**
     * 編輯圖片
     *
     * @param p 玩家
     * @param pluginName 插件名称
     * @param name 资源名称
     * @return 是否成功记录，如果资源未找到则返回false
     */
    public boolean editResource(Player p, String pluginName, String name) {
        PluginResourcesManager pl = this.resourceManagers.get(pluginName);
        if (pl != null) {
            if (pl.contains(name)) {
                Resource res = pl.getResource(name);
                if (res.getType() != ResourceType.PICTURE) {
                    return false;
                }
                this.resourceEditors.put(p.getUniqueId(), new PictureEditor(p.getUniqueId(), pluginName, name));
                return true;
            }
        }
        return false;
    }

    /**
     * 注册插件，申请PluginResourcesManager
     *
     * @param pluginName 插件名称
     * @return 如果插件已经存在则返回false
     */
    public boolean registerPlugin(String pluginName) {
        if (this.resourceManagers.containsKey(pluginName)) {
            return false;
        }
        try {
            this.resourceManagers.put(pluginName, new PluginResourcesManager(pluginName));
            return true;
        } catch (ConfigException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取资源，不存在时返回null
     *
     * @param pluginName 资源所属插件的名称
     * @param resname 资源名称
     * @return
     */
    public Resource getResource(String pluginName, String resname) {
        if (this.resourceManagers.containsKey(pluginName)) {
            return this.resourceManagers.get(pluginName).getResource(resname);
        }
        return null;
    }

    /**
     * 根据插件名称获取插件资源管理器
     *
     * @param name 插件名
     * @return 插件资源管理器
     */
    public PluginResourcesManager getPluginResourceManager(String name) {
        return this.resourceManagers.get(name);
    }

    private void loadManagers() {
        File f = new File(CocoaUI.getKDataFolder() + "/resources/");
        if (!f.exists()) {
            f.mkdirs();
        }

        for (File file : f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File arg0) {
                return arg0.toString().endsWith("yml");
            }
        })) {
            this.loadFromFile(file);
        }
    }

    private void loadFromFile(File f) {
        String name = f.getName();
        this.loadManager(name.substring(0, name.length() - 4));

    }

    private void loadManager(String name) {
        try {
            PluginResourcesManager manager = new PluginResourcesManager(name);
            this.resourceManagers.put(name, manager);
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }

    /**
     * 为某个玩家发送资源更新包
     *
     * @param p 玩家
     */
    public void sendUpdatePackage(Player p) {
        for (PluginResourcesManager manager : resourceManagers.values()) {
            CocoaUI.getPluginMessageManager().sendPackage(manager.getPackage(), p);
        }
        CocoaUI.getPluginMessageManager().sendPackage(new OutResourceUpdateSent(), p);

    }

    /**
     * 保存
     */
    public void save() {
        for (PluginResourcesManager manager : this.resourceManagers.values()) {
            manager.save();
        }
    }

    /**
     * 请求根据URL重载资源的MD5，wdith，height。
     *
     * @param url URL地址
     * @param name 资源名称
     * @param pluginName 所属插件名称
     */
    public void reloadResourceInfo(String url, String name, String pluginName, boolean isResource) {
        if (!this.firstLoad) {
            this.updateList.put(new ResourceName(name, pluginName), this.pool.submit(new ResourceInfoLoader(url, name, pluginName, isResource)));
            return;
        }
        this.waitLoadList.put(new ResourceName(name, pluginName), new ResourceInfoLoader(url, name, pluginName, isResource));
        return;
    }

    public void onServerDone() {
        for (Entry<ResourceName, ResourceInfoLoader> entry : this.waitLoadList.entrySet()) {
            this.updateList.put(entry.getKey(), this.pool.submit(entry.getValue()));
        }
        this.startTask();
    }

    void onTick() {
        timer++;
        if (this.updateList.isEmpty()) {
            if (this.firstLoad) {
                this.firstLoad = false;
                CocoaUI.getLog().info("[CocoaUI]插件所有资源信息已经加载完成");
                return;
            }
        }
        if (timer == 30) {
            if (!this.isFinish()) {
                CocoaUI.getLog().info("已经加载(" + this.loaded + "/" + (this.loaded + this.updateList.size() + ")"));
                this.timer = 0;
            }
        }
        for (Entry<ResourceName, Future<ResourceInfo>> entry : this.updateList.entrySet()) {
            if (entry.getValue().isDone()) {
                try {
                    this.updateInfo(entry.getKey(), entry.getValue().get());
                    this.finish.add(entry.getKey());
                    this.loaded++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        for (ResourceName pl : this.finish) {
            this.updateList.remove(pl);
        }
    }

    private void updateInfo(ResourceName key, ResourceInfo value) {
        if (this.resourceManagers.containsKey(key.pluginName)) {
            this.resourceManagers.get(key.pluginName).updateInfo(key.name, value);
        }
    }

    /**
     * 查看资源管理器是否初始化完毕。 （遍历资源加载信息是异步操作，所以可能出现服务端启动完成却加载不完的现象，具体以这个函数的返回值为准）
     *
     * @return 是否加载完成
     */
    public boolean isFinish() {
        return !this.firstLoad;
    }

    public void onDisable() {
        this.save();
        if (this.task != null) {
            this.task.cancel();
        }
        this.pool.shutdown();
    }

    public void sendUpdateRequest(Player p, String pluginName, String name) {
        this.resourceEditors.put(p.getUniqueId(), new PictureEditor(p.getUniqueId(), pluginName, name));
        CocoaUI.getPluginMessageManager().sendPackage(new OutOpenResourceChooser(), p);
    }

    public List<String> getPlugins() {
        List<String> list = Lists.newArrayList();
        for (String s : this.resourceManagers.keySet()) {
            if (CocoaUI.getCocoaPluginManager().contains(s)) {
                list.add(s);
            }
        }
        return list;
    }

    public List<String> getCustoms() {
        List<String> list = Lists.newArrayList();
        for (String s : this.resourceManagers.keySet()) {
            if (!CocoaUI.getCocoaPluginManager().contains(s)) {
                list.add(s);
            }
        }
        return list;
    }

}

class ResourceName {

    String name;
    String pluginName;

    public ResourceName(String name, String pluginName) {
        this.name = name;
        this.pluginName = pluginName;
    }
}

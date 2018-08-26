package net.mcbbs.cocoaui.manager;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Maps;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.packages.OutVerifyPackage;

public class VerifyManager {

    private Map<UUID, Integer> verifyList = Maps.newHashMap();
    private BukkitTask task;
    boolean forceUseMod;

    public void verifyPlayer(Player p) {
        Bukkit.getScheduler().runTaskLater(CocoaUI.getPlugin(CocoaUI.class), () -> {
            verifyList.put(p.getUniqueId(), 0);
            CocoaUI.getPluginMessageManager().sendPackage(new OutVerifyPackage(), p);
        }, 10);

    }

    public VerifyManager() {
        this.start();
    }

    public void setForceUseMod(boolean value) {
        this.forceUseMod = value;
    }

    public void receiveVerify(Player p) {
        this.verifyList.remove(p.getUniqueId());
    }

    private void start() {
        task = Bukkit.getScheduler().runTaskTimer(CocoaUI.getPlugin(CocoaUI.class), () -> task(), 20L, 20L);
    }

    private void task() {
        for (UUID uuid : verifyList.keySet()) {
            int i = this.verifyList.get(uuid);
            if (i++ > 5) {
                if (this.forceUseMod) {
                    if (Bukkit.getPlayer(uuid) != null) {
                        Bukkit.getPlayer(uuid).kickPlayer("Please install CocoaUI mod");
                    }
                }
                this.verifyList.remove(uuid);
            } else {
                this.verifyList.put(uuid, i);
            }
        }
    }

    public void onDisable() {
        this.task.cancel();
    }
}

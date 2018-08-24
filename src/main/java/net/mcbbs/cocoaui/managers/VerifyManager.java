package net.mcbbs.cocoaui.managers;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Maps;

import net.mcbbs.cocoaui.CocoaUI;
import net.mcbbs.cocoaui.pluginmessage.packages.OutVerifyPackage;

public class VerifyManager {

    Map<UUID, Integer> verifylist = Maps.newHashMap();
    BukkitTask task;
    boolean forceUseMod;

    public void verifyPlayer(Player p) {
        Bukkit.getScheduler().runTaskLater(CocoaUI.getPlugin(CocoaUI.class), new Runnable() {
            @Override
            public void run() {
                verifylist.put(p.getUniqueId(), 0);
                CocoaUI.getPluginMessageManager().sendPackage(new OutVerifyPackage(), p);

            }
        }, 10);

    }

    public VerifyManager() {
        this.start();
    }

    public void setForceUseMod(boolean value) {
        this.forceUseMod = value;
    }

    public void receiveVerify(Player p) {
        this.verifylist.remove(p.getUniqueId());
    }

    private void start() {
        task = Bukkit.getScheduler().runTaskTimer(CocoaUI.getPlugin(CocoaUI.class), new Runnable() {
            @Override
            public void run() {
                task();

            }
        }, 20L, 20L);
    }

    private void task() {
        for (UUID uuid : verifylist.keySet()) {
            int i = this.verifylist.get(uuid);
            if (i++ > 5) {
                if (this.forceUseMod) {
                    if (Bukkit.getPlayer(uuid) != null) {
                        Bukkit.getPlayer(uuid).kickPlayer("Please install CocoaUI mod");
                    }
                }
                this.verifylist.remove(uuid);
            } else {
                this.verifylist.put(uuid, i);
            }
        }
    }

    public void onDisable() {
        this.task.cancel();
    }
}

package net.mcbbs.cocoaui.util;

import net.mcbbs.cocoaui.util.reflect.ReflectionUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Easy to use NMS
 *
 * @author Zoyn
 * @since 2018/8/7
 */
public final class NMSUtils {

    private static String version;
    private static Field playerConnectionField;
    private static Method sendPacketMethod;
    private static Method asNMSCopyMethod;
    private static Method craftBukkitEntityPlayerGetHandleMethod;

    // Prevent accidental construction
    private NMSUtils() {
    }

    static {
        // org.bukkit.craftbukkit.vX_XX_RX;
        version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        // initial
        try {
            playerConnectionField = ReflectionUtils.getFieldByFieldName(getNMSClass("EntityPlayer"), "playerConnection");
            sendPacketMethod = ReflectionUtils.getMethod(getNMSClass("PlayerConnection"), "sendPacket", getNMSClass("Packet"));
            asNMSCopyMethod = ReflectionUtils.getMethod(getOBCClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);
            craftBukkitEntityPlayerGetHandleMethod = ReflectionUtils.getMethod(getOBCClass("entity.CraftPlayer"), "getHandle");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            System.out.println("found a exception when initial the NMSUtils");
            e.printStackTrace();
        }
    }

    /**
     * 取服务器版本 如 v1_10_R1
     * <br>
     * get the server version, returns a string similar to v1_10_R1
     *
     * @return server version
     */
    public static String getVersion() {
        return version;
    }

    /**
     * 取 org.bukkit.craftbukkit 包下的类对象
     * <br>
     * get org.bukkit.craftbukkit's class object
     *
     * @param className a class's name in the package obc
     * @return {@link Class}
     */
    public static Class<?> getOBCClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + NMSUtils.getVersion() + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取物品的 NMS 对象
     * <br>
     * get a item's nms object
     *
     * @param itemStack a itemStack object
     * @return {@link Object}
     */
    public static Object getNMSItem(ItemStack itemStack) {
        if (asNMSCopyMethod == null) {
            Class<?> craftItemStack = NMSUtils.getOBCClass("inventory.CraftItemStack");
            Validate.notNull(itemStack);

            try {
                //CraftItemStack

                asNMSCopyMethod = ReflectionUtils.getMethod(craftItemStack, "asNMSCopy", ItemStack.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            return asNMSCopyMethod.invoke(Validate.notNull(itemStack));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取对应的 NMS 下的类
     * <br>
     * get net.minecraft.server's class object
     *
     * @param className a class's name in the package nms
     * @return {@link Class}
     */
    public static Class<?> getNMSClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + className);
        } catch (Exception e) {
            System.out.println("错误: " + e.getMessage());
        }
        return null;
    }

    /**
     * 给一名玩家发送 NMS 数据包
     * <br>
     * send a NMS packet to a player
     *
     * @param player player object
     * @param packet packet object
     * @see #getNMSPlayer(Player)
     * @see ReflectionUtils#invokeMethod(Method, Object, Object...)
     */
    public static void sendPacket(Player player, Object packet) {
        Object entityPlayer = getNMSPlayer(player);

        if (playerConnectionField == null) {
            try {
                playerConnectionField = ReflectionUtils.getFieldByFieldName(getNMSClass("EntityPlayer"), "playerConnection");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        if (sendPacketMethod == null) {
            try {
                sendPacketMethod = ReflectionUtils.getMethod(getNMSClass("PlayerConnection"), "sendPacket", getNMSClass("Packet"));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        try {
            // get playerConnection instance
            Object playerConnection = playerConnectionField.get(entityPlayer);
            // invoke method sendPacket()
            ReflectionUtils.invokeMethod(sendPacketMethod, playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取玩家的 NMS 对象
     * <br>
     * get a player's nms object
     *
     * @param player player object
     * @return {@link Object}
     * @see ReflectionUtils#invokeMethod(Method, Object, Object...)
     */
    public static Object getNMSPlayer(Player player) {
        try {
            return ReflectionUtils.invokeMethod(craftBukkitEntityPlayerGetHandleMethod, player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player;
    }

}

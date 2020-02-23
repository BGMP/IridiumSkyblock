package com.iridium.iridiumskyblock;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class NMSUtils {

    public static Class<?> CraftWorld;
    public static Class<?> EntityArmorStand;
    public static Class<?> World;
    public static Class<?> IChatBaseComponent;
    public static Class<?> ChatMessage;
    public static Class<?> PacketPlayOutSpawnEntityLiving;
    public static Class<?> EntityLiving;
    public static Class<?> WorldBorder;
    public static Class<?> PacketPlayOutWorldBorder;
    public static Class<?> BlockPosition;
    public static Class<?> IBlockData;
    public static Class<?> Block;

    public static Constructor<?> BlockPositionConstructor;

    public static Method CraftChunkgetHandle;
    public static Method EntityArmorStandsetInvisible;
    public static Method EntityArmorStandsetCustomNameVisible;
    public static Method WorldBordersetCenter;
    public static Method WorldBordersetSize;
    public static Method WorldBordersetWarningDistance;
    public static Method WorldBordersetWarningTime;
    public static Method WorldBordertransitionSizeBetween;
    public static Method setTypeAndData;
    public static Method getByCombinedId;
    public static Method getHandle;

    static {
        try {
            CraftWorld = getCraftClass("CraftWorld");
            EntityArmorStand = getNMSClass("EntityArmorStand");
            World = getNMSClass("World");
            IChatBaseComponent = getNMSClass("IChatBaseComponent");
            ChatMessage = getNMSClass("ChatMessage");
            PacketPlayOutSpawnEntityLiving = getNMSClass("PacketPlayOutSpawnEntityLiving");
            EntityLiving = getNMSClass("EntityLiving");
            WorldBorder = getNMSClass("WorldBorder");
            PacketPlayOutWorldBorder = getNMSClass("PacketPlayOutWorldBorder");
            IBlockData = getNMSClass("IBlockData");
            Block = getNMSClass("Block");
            BlockPosition = getNMSClass("BlockPosition");

            BlockPositionConstructor = BlockPosition.getConstructor(int.class, int.class, int.class);

            CraftChunkgetHandle = getCraftClass("CraftChunk").getMethod("getHandle");
            EntityArmorStandsetInvisible = getNMSClass("EntityArmorStand").getMethod("setInvisible", boolean.class);
            EntityArmorStandsetCustomNameVisible = getNMSClass("EntityArmorStand").getMethod("setCustomNameVisible", boolean.class);
            WorldBordersetCenter = getNMSClass("WorldBorder").getMethod("setCenter", double.class, double.class);
            WorldBordersetSize = getNMSClass("WorldBorder").getMethod("setSize", double.class);
            WorldBordersetWarningDistance = getNMSClass("WorldBorder").getMethod("setWarningDistance", int.class);
            WorldBordersetWarningTime = getNMSClass("WorldBorder").getMethod("setWarningTime", int.class);
            WorldBordertransitionSizeBetween = getNMSClass("WorldBorder").getMethod("transitionSizeBetween", double.class, double.class, long.class);
            getHandle = CraftWorld.getMethod("getHandle");
            getByCombinedId = Block.getMethod("getByCombinedId", int.class);
            setTypeAndData = getNMSClass("WorldServer").getMethod("setTypeAndData", BlockPosition, IBlockData, int.class);
        } catch (Exception e) {
            IridiumSkyblock.getInstance().sendErrorMessage(e);
        }
    }

    public static void setBlockFast(World world, int X, int Y, int Z, int blockId, byte data) {
        Location location = new Location(world, X, Y, Z);
        org.bukkit.block.Block b = location.getBlock();
        BlockState state = b.getState();
        if (state instanceof Chest) {
            ((Chest) state).getInventory().clear();
        } else if (state instanceof DoubleChest) {
            ((DoubleChest) state).getInventory().clear();
        } else if (state instanceof Dropper) {
            ((Dropper) state).getInventory().clear();
        } else if (state instanceof Dispenser) {
            ((Dispenser) state).getInventory().clear();
        } else if (state instanceof Hopper) {
            ((Hopper) state).getInventory().clear();
        } else if (state instanceof Furnace) {
            ((Furnace) state).getInventory().clear();
        } else if (state instanceof BrewingStand) {
            ((BrewingStand) state).getInventory().clear();
        } else if (state instanceof Beacon) {
            ((Beacon) state).getInventory().clear();
        }
        new Location(world, X, Y, Z).getBlock().setTypeIdAndData(blockId, data, false);
    }

    public static void sendChunk(Player p, Chunk c) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(IridiumSkyblock.getInstance(), () -> {
            try {
                sendPacket(p, getNMSClass("PacketPlayOutUnloadChunk").getConstructor(int.class, int.class).newInstance(c.getX(), c.getZ()));
            } catch (Exception ignored) {
            }
        });
        Bukkit.getScheduler().scheduleAsyncDelayedTask(IridiumSkyblock.getInstance(), () -> {
            try {
                sendPacket(p, getNMSClass("PacketPlayOutMapChunk").getConstructor(getNMSClass("Chunk"), int.class).newInstance(CraftChunkgetHandle.invoke(c), 65535));
            } catch (Exception ignored) {
            }
        }, 2);
    }

    public static void sendHologram(Player p, final Location loc, List<String> text) {
        Bukkit.getScheduler().runTaskAsynchronously(IridiumSkyblock.getInstance(), new Runnable() {
            Location location = loc;

            @Override
            public void run() {
                try {
                    for (int i = -1; ++i < text.size(); ) {
                        Object craftWorld = CraftWorld.cast(location.getWorld());
                        Object entityArmorStand = EntityArmorStand.getConstructor(World, double.class, double.class, double.class).newInstance(CraftWorld.getMethod("getHandle").invoke(craftWorld), loc.getX(), loc.getY(), loc.getZ());

                        EntityArmorStandsetInvisible.invoke(entityArmorStand, true);
                        EntityArmorStandsetCustomNameVisible.invoke(entityArmorStand, true);
                        try {
                            entityArmorStand.getClass().getMethod("setCustomName", String.class).invoke(entityArmorStand, Utils.color(text.get(i)));
                        } catch (NoSuchMethodException noSuchMethodEx) {
                            entityArmorStand.getClass().getMethod("setCustomName", IChatBaseComponent).invoke(entityArmorStand, ChatMessage.getConstructor(String.class, Object[].class).newInstance(Utils.color(text.get(i)), new Object[0]));
                        }
                        Object packet = PacketPlayOutSpawnEntityLiving.getConstructor(EntityLiving).newInstance(entityArmorStand);
                        sendPacket(p, packet);
                        location = location.subtract(0, 0.4, 0);
                    }
                } catch (Exception e) {
                    IridiumSkyblock.getInstance().sendErrorMessage(e);
                }
            }
        });
    }

    public static void sendWorldBorder(Player player, Color color, double size, Location centerLocation) {
        Bukkit.getScheduler().runTaskAsynchronously(IridiumSkyblock.getInstance(), () -> {
            try {
                Object worldBorder = WorldBorder.getConstructor().newInstance();


                Object craftWorld = CraftWorld.cast(centerLocation.getWorld());
                setField(worldBorder, "world", craftWorld.getClass().getMethod("getHandle").invoke(craftWorld), false);

                WorldBordersetCenter.invoke(worldBorder, centerLocation.getBlockX() + 0.5, centerLocation.getBlockZ() + 0.5);

                WorldBordersetSize.invoke(worldBorder, size);

                WorldBordersetWarningTime.invoke(worldBorder, 0);
                WorldBordersetWarningDistance.invoke(worldBorder, 0);

                switch (color) {
                    case Red:
                        WorldBordertransitionSizeBetween.invoke(worldBorder, size, size - 1.0D, 20000000L);
                        break;
                    case Green:
                        WorldBordertransitionSizeBetween.invoke(worldBorder, size - 0.1D, size, 20000000L);
                        break;
                }

                Object packet = PacketPlayOutWorldBorder.getConstructor(WorldBorder,
                        PacketPlayOutWorldBorder.getDeclaredClasses()[getVersionNumber() > 100 ? 0 : 1]).newInstance(worldBorder,
                        Enum.valueOf((Class<Enum>) PacketPlayOutWorldBorder.getDeclaredClasses()[getVersionNumber() > 100 ? 0 : 1], "INITIALIZE"));
                sendPacket(player, packet);
            } catch (Exception e) {
                IridiumSkyblock.getInstance().sendErrorMessage(e);
            }
        });
    }

    public static void sendActionBar(Player player, String message) {
        Bukkit.getScheduler().runTaskAsynchronously(IridiumSkyblock.getInstance(), () -> {
            try {
                Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);

                Object text = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));
                Object packet = constructor.newInstance(text, (byte) 2);
                sendPacket(player, packet);
            } catch (Exception e) {
                IridiumSkyblock.getInstance().sendErrorMessage(e);
            }
        });
    }

    public static void sendSubTitle(Player player, String message, int fadeIn, int displayTime, int fadeOut) {
        Bukkit.getScheduler().runTaskAsynchronously(IridiumSkyblock.getInstance(), () -> {
            try {
                Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
                Object chat = IChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class)
                        .invoke(null, ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));

                Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], IChatBaseComponent,
                        int.class, int.class, int.class);
                Object packet = titleConstructor.newInstance(enumTitle, chat, fadeIn, displayTime, fadeOut);

                sendPacket(player, packet);
            } catch (Exception e) {
                IridiumSkyblock.getInstance().sendErrorMessage(e);
            }
        });
    }

    public static void sendTitle(Player player, String message, int fadeIn, int displayTime, int fadeOut) {
        Bukkit.getScheduler().runTaskAsynchronously(IridiumSkyblock.getInstance(), () -> {
            try {
                Object enumTitle = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
                Object chat = IChatBaseComponent.getDeclaredClasses()[0].getMethod("a", String.class)
                        .invoke(null, ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));

                Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
                        getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], IChatBaseComponent,
                        int.class, int.class, int.class);
                Object packet = titleConstructor.newInstance(enumTitle, chat, fadeIn, displayTime, fadeOut);

                sendPacket(player, packet);
            } catch (Exception e) {
                IridiumSkyblock.getInstance().sendErrorMessage(e);
            }
        });
    }


    public static void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (Exception e) {
            IridiumSkyblock.getInstance().sendErrorMessage(e);
        }
    }

    public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + getVersion() + "." + name);
    }

    public static Class<?> getCraftClass(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + name);
    }

    public static void setField(Object object, String fieldName, Object fieldValue, boolean declared) {
        try {
            Field field;

            if (declared) {
                field = object.getClass().getDeclaredField(fieldName);
            } else {
                field = object.getClass().getField(fieldName);
            }

            field.setAccessible(true);
            field.set(object, fieldValue);
        } catch (Exception e) {
            IridiumSkyblock.getInstance().sendErrorMessage(e);
        }
    }

    public static String getVersion() {
        return IridiumSkyblock.getInstance().getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static int getVersionNumber() {
        return Integer.parseInt(getVersion().substring(1, getVersion().length() - 3).replace("_", ""));
    }

    public enum Color {
        Blue, Green, Red
    }
}
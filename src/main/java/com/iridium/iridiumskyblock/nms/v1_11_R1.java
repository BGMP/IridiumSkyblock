package com.iridium.iridiumskyblock.nms;

import com.iridium.iridiumskyblock.Color;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

public class v1_11_R1 implements NMS {
    @Override
    public void setBlockFast(Block block, int blockId, byte data) {
        BlockState state = block.getState();
        if (state.getType().name().endsWith("AIR") && blockId == 0) return;
        if (state instanceof InventoryHolder) {
            ((InventoryHolder) state).getInventory().clear();
        }
        net.minecraft.server.v1_11_R1.World nmsWorld = ((org.bukkit.craftbukkit.v1_11_R1.CraftWorld) block.getWorld()).getHandle();
        net.minecraft.server.v1_11_R1.BlockPosition bp = new net.minecraft.server.v1_11_R1.BlockPosition(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ());
        net.minecraft.server.v1_11_R1.IBlockData ibd = net.minecraft.server.v1_11_R1.Block.getByCombinedId(blockId + (data << 12));
        nmsWorld.setTypeAndData(bp, ibd, 2);
    }

    @Override
    public void sendChunk(Player player, Chunk chunk) {
        PacketPlayOutMapChunk packetPlayOutMapChunk = new PacketPlayOutMapChunk(((CraftChunk) chunk).getHandle(), 65535);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutMapChunk);
    }

    @Override
    public void sendWorldBorder(Player player, Color color, double size, Location centerLocation) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) centerLocation.getWorld()).getHandle();
        worldBorder.setCenter(centerLocation.getBlockX() + 0.5, centerLocation.getBlockZ() + 0.5);

        if (color == Color.Off) {
            worldBorder.setSize(Integer.MAX_VALUE);
        } else {
            worldBorder.setSize(size);
        }

        worldBorder.setWarningDistance(0);
        worldBorder.setWarningTime(0);

        if (color == Color.Red) {
            worldBorder.transitionSizeBetween(size, size - 1.0D, 20000000L);
        } else if (color == Color.Green) {
            worldBorder.transitionSizeBetween(size - 0.1D, size, 20000000L);
        }
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE));
    }

    @Override
    public void sendSubTitle(Player player, String message, int fadeIn, int displayTime, int fadeOut) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, iChatBaseComponent, fadeIn, displayTime, fadeOut);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutTitle);
    }

    @Override
    public void sendTitle(Player player, String message, int fadeIn, int displayTime, int fadeOut) {
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a(ChatColor.translateAlternateColorCodes('&', "{\"text\":\"" + message + "\"}"));
        PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iChatBaseComponent, fadeIn, displayTime, fadeOut);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packetPlayOutTitle);
    }
}

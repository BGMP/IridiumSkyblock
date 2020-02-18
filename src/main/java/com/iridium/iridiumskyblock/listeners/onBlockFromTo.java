package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.MultiversionMaterials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.*;

public class onBlockFromTo implements Listener {

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
        if(e.getBlock().getType().equals(Material.WATER) || e.getBlock().getType().equals(Material.LAVA)){
            Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getBlock().getLocation());
            if(island != null){
                if(island!=IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getToBlock().getLocation())){
                    e.setCancelled(true);
                }
            }
        }
        try {
            if (e.getFace() != BlockFace.DOWN) {
                Block b = e.getToBlock();
                Location fromLoc = b.getLocation();
                Bukkit.getScheduler().runTask(IridiumSkyblock.getInstance(), () -> {
                    if (b.getType().equals(Material.COBBLESTONE) || b.getType().equals(Material.STONE)) {
                        if (!isSurroundedByWater(fromLoc)) {
                            return;
                        }

                        Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(fromLoc);
                        if (island != null) {
                            Random r = new Random();
                            ArrayList<String> items = new ArrayList<>(e.getBlock().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld()) ? IridiumSkyblock.oreUpgradeCache.get(island.getOreLevel()) : IridiumSkyblock.netherOreUpgradeCache.get(island.getOreLevel()));
                            String item = items.get(r.nextInt(items.size()));
                            MultiversionMaterials material = MultiversionMaterials.fromString(item);
                            if (material == null) return;
                            e.setCancelled(true);
                            b.setType(material.parseMaterial());
                            b.setData((byte) material.data);
                            b.getState().update(true);
                        }
                    }
                });
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }

    @EventHandler
    public void onObsidianGen(BlockFormEvent e) {
        if (e.getNewState().getType().equals(Material.OBSIDIAN)) {
            Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getBlock().getLocation());
            if (island != null) {
                island.failedGenerators.add(e.getBlock().getLocation());
            }
        }
    }

    public boolean isSurroundedByWater(Location fromLoc) {
        // Sets gives better performance than arrays when used wisely
        Set<Block> blocksSet = new HashSet<>(Arrays.asList(
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() + 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() - 1, fromLoc.getBlockY(), fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() + 1),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY(), fromLoc.getBlockZ() - 1),

                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() + 1, fromLoc.getBlockY() + 1, fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() - 1, fromLoc.getBlockY() + 1, fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY() + 1, fromLoc.getBlockZ() + 1),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY() + 1, fromLoc.getBlockZ() - 1),

                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() + 1, fromLoc.getBlockY() - 1, fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX() - 1, fromLoc.getBlockY() - 1, fromLoc.getBlockZ()),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY() - 1, fromLoc.getBlockZ() + 1),
                fromLoc.getWorld().getBlockAt(fromLoc.getBlockX(), fromLoc.getBlockY() - 1, fromLoc.getBlockZ() - 1)
        ));
        for (Block b : blocksSet) {
            if (b.getType().toString().contains("WATER")) return true;
        }
        return false;

    }
}

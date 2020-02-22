package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;

public class onFireSpread implements Listener {

    @EventHandler
    public void onFireSpread(BlockIgniteEvent event) {
        BlockIgniteEvent.IgniteCause cause = event.getCause();
        if (cause != BlockIgniteEvent.IgniteCause.SPREAD) return;

        try {
            if (event.isCancelled()) return;

            Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(event.getIgnitingBlock().getLocation());
            if (island != null && !island.getGlobals().fireSpread) event.setCancelled(true);
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }

    @EventHandler
    public void onBlockBurned(BlockBurnEvent event) {
        try {
            if (event.isCancelled()) return;

            Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(event.getIgnitingBlock().getLocation());
            if (island != null && !island.getGlobals().fireSpread) event.setCancelled(true);
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}

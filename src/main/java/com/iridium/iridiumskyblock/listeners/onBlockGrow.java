package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.XBlock;
import org.bukkit.CropState;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.material.Crops;

public class onBlockGrow implements Listener {

    @EventHandler
    public void onBlockGrow(BlockGrowEvent e) {
        try {
            Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getBlock().getLocation());
            if (island != null) {
                if (island.getFarmingBooster() != 0) {
                    if (XBlock.isCrops(e.getBlock().getType())) {
                        e.setCancelled(true);
                        Crops c = new Crops(CropState.RIPE);
                        BlockState bs = e.getBlock().getState();
                        bs.setData(c);
                        bs.update();
                    }
                }
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}

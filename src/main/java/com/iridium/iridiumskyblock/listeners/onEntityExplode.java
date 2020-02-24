package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class onEntityExplode implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        try {
            if (IridiumSkyblock.getConfiguration().disableExplosions && (e.getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld()) || e.getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getNetherWorld()))) {
                e.setCancelled(true);
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMonitorEntityExplode(EntityExplodeEvent e) {
        Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getLocation());
        if (island != null) {
            for (Block b : e.blockList()) {
                if (Utils.isBlockValuable(b)) {
                    if (!(b.getState() instanceof CreatureSpawner)) {
                        if (island.valuableBlocks.containsKey(b.getType().name())) {
                            island.valuableBlocks.put(b.getType().name(), island.valuableBlocks.get(b.getType().name()) - 1);
                        }
                    }
                    if(island.updating){
                        island.tempValues.remove(b.getLocation());
                    }
                }
            }
            island.calculateIslandValue();
        }
    }
}

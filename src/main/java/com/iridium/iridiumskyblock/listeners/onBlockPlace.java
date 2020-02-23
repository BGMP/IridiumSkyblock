package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.*;
import com.iridium.iridiumskyblock.configs.Missions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Crops;

public class onBlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        try {
            User u = User.getUser(e.getPlayer());
            Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getBlock().getLocation());
            if (island != null) {
                if (u.islandID == island.getId()) {
                    for (String mission : IridiumSkyblock.getMissions().mission.keySet()) {
                        if (!island.getMissionLevels().containsKey(mission)) island.getMissionLevels().put(mission, 1);
                        Missions.Mission m = IridiumSkyblock.getMissions().mission.get(mission).get(island.getMissionLevels().get(mission));
                        if (m.type == MissionType.BLOCK_PLACE) {
                            if (m.conditions.isEmpty() || m.conditions.contains(MultiversionMaterials.fromMaterial(e.getBlock().getType()).toString()) || (e.getBlock().getState().getData() instanceof Crops && m.conditions.contains(((Crops) e.getBlock().getState().getData()).getState().toString()))) {
                            }
                        }
                    }
                }
                if ((!island.getPermissions((u.islandID == island.getId() || island.isCoop(u.getIsland())) ? (island.isCoop(u.getIsland()) ? Role.Member : u.getRole()) : Role.Visitor).placeBlocks) && !u.bypassing)
                    e.setCancelled(true);
                else if (Utils.isBlockValuable(e.getBlock()))
                    island.blocks.add(e.getBlock().getLocation());
                island.calculateIslandValue();
            } else {
                if (e.getBlock().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld()) || e.getBlock().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getNetherWorld())) {
                    if (!u.bypassing) {
                        e.setCancelled(true);
                    }
                }
            }
        } catch (
                Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}

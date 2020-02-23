package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.MissionType;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.configs.Missions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class onEntityDeath implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        try {
            if (e.getEntity().getKiller() == null) return;
            if (e.getEntity().getKiller().getPlayer() == null) return;
            Island island = User.getUser(e.getEntity().getKiller().getPlayer()).getIsland();
            if (island != null) {
                for (String mission : IridiumSkyblock.getMissions().mission.keySet()) {
                    if (!island.getMissionLevels().containsKey(mission)) island.getMissionLevels().put(mission, 1);
                    Missions.Mission m = IridiumSkyblock.getMissions().mission.get(mission).get(island.getMissionLevels().get(mission));
                    if (m.type == MissionType.ENTITY_KILL) {
                        if (m.conditions.isEmpty() || m.conditions.contains(e.getEntityType().toString())) {
                        }
                    }
                }
                if (island.getExpBooster() != 0) {
                    e.setDroppedExp(e.getDroppedExp() * 2);
                }
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}

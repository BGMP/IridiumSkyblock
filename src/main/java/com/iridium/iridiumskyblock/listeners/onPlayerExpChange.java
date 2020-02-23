package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.MissionType;
import com.iridium.iridiumskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class onPlayerExpChange implements Listener {

    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent e) {
        try {
            Player p = e.getPlayer();
            User user = User.getUser(p);
            Island island = user.getIsland();
            if (island != null) {
                for (String mission : IridiumSkyblock.getMissions().mission.keySet()) {
                    if (!island.getMissionLevels().containsKey(mission)) island.getMissionLevels().put(mission, 1);
                    if (IridiumSkyblock.getMissions().mission.get(mission).get(island.getMissionLevels().get(mission)).type == MissionType.EXPERIENCE) {
                    }
                }
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}

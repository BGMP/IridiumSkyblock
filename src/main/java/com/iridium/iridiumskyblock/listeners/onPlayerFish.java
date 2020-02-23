package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.MissionType;
import com.iridium.iridiumskyblock.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class onPlayerFish implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        try {
            if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
                User u = User.getUser(e.getPlayer());
                Island island = u.getIsland();
                if (island != null) {
                    for (String mission : IridiumSkyblock.getMissions().mission.keySet()) {
                        if (!island.getMissionLevels().containsKey(mission)) island.getMissionLevels().put(mission, 1);
                        if (IridiumSkyblock.getMissions().mission.get(mission).get(island.getMissionLevels().get(mission)).type == MissionType.FISH_CATCH) {
                        }
                    }
                }
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}

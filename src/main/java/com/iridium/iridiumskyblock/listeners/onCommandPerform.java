package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class onCommandPerform implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandPerform(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();
        if (command.equalsIgnoreCase("/fly")
        ) {
            Player player = event.getPlayer();
            try {
                User u = User.getUser(event.getPlayer());
                Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(event.getPlayer().getLocation());

                if (island != null) {
                    player.sendMessage("island is not null");
                    if (u.islandID != island.getId()) {
                        if ((!island.getPermissions((u.islandID == island.getId() || island.isCoop(u.getIsland())) ? (island.isCoop(u.getIsland()) ? Role.Member : u.getRole()) : Role.Visitor).fly) && !u.bypassing)
                            event.setCancelled(true);
                    }
                }

            } catch (Exception ex) {
                IridiumSkyblock.getInstance().sendErrorMessage(ex);
            }
        }
    }
}

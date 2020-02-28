package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class onPlayerTeleport implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getTo());
        User user = User.getUser(e.getPlayer());
        if (island != null) {
            if (island.getOwner().equals(user.player)) {
                if (user.role == Role.Owner) return;
                user.role = Role.Owner;
            } else {
                if (user.role == Role.Visitor) return;
                user.role = Role.Visitor;
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(IridiumSkyblock.getInstance(), () -> island.sendBorder(e.getPlayer()), 1);
        } else {
            user.role = Role.Visitor;
        }
    }
}

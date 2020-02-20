package com.iridium.iridiumskyblock.listeners;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.spawn.EssentialsSpawn;
import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class onPlayerMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        try {
            if (e.getPlayer().getLocation().getY() < 0 && IridiumSkyblock.getConfiguration().voidTeleport) {
                Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(e.getPlayer().getLocation());
                if (island != null) {
                    if (e.getPlayer().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld())) {
                        island.teleportHome(e.getPlayer());
                    } else {
                        island.teleportNetherHome(e.getPlayer());
                    }
                } else {
                    User u = User.getUser(e.getPlayer());
                    if (u.getIsland() != null) {
                        if (e.getPlayer().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld())) {
                            u.getIsland().teleportHome(e.getPlayer());
                        } else if (e.getPlayer().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getNetherWorld())) {
                            u.getIsland().teleportNetherHome(e.getPlayer());
                        }
                    } else if (e.getPlayer().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld()) || e.getPlayer().getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getNetherWorld())) {
                        if (Bukkit.getPluginManager().isPluginEnabled("EssentialsSpawn")) {
                            EssentialsSpawn essentialsSpawn = (EssentialsSpawn) Bukkit.getPluginManager().getPlugin("EssentialsSpawn");
                            Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
                            e.getPlayer().teleport(essentialsSpawn.getSpawn(essentials.getUser(e.getPlayer()).getGroup()));
                        } else {
                            e.getPlayer().teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                        }
                    }
                }
            }
            User user = User.getUser(e.getPlayer());
            Island island = user.getIsland();
            if (island != null) {
                if (user.flying && (!island.isInIsland(e.getPlayer().getLocation()) || island.getFlightBooster() == 0) && !e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                    e.getPlayer().setAllowFlight(false);
                    e.getPlayer().setFlying(false);
                    user.flying = false;
                    e.getPlayer().sendMessage(Utils.color(IridiumSkyblock.getMessages().flightDisabled.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            }
        } catch (Exception ex) {
            IridiumSkyblock.getInstance().sendErrorMessage(ex);
        }
    }
}

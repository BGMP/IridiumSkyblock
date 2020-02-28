package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class onProjectileLaunch implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        EntityType entityType = event.getEntityType();
        if (entityType != EntityType.ENDER_PEARL
                && entityType != EntityType.EGG
                && entityType != EntityType.FISHING_HOOK
                && entityType != EntityType.SPLASH_POTION
        ) return;

        ProjectileSource shooter = event.getEntity().getShooter();
        if (!(shooter instanceof Player)) return;

        Player p = (Player) shooter;
        User user = User.getUser(p);

        if (user.getIsland() != null) {
            Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(event.getLocation());
            if (island == null) return;

            if (entityType == EntityType.ENDER_PEARL) {
                if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).useEnderpearl && !user.bypassing) {
                    p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantEnderpearl.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    event.setCancelled(true);
                }
            } else if (entityType == EntityType.EGG) {
                if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).throwChickenEgg && !user.bypassing) {
                    p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
                    p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantChickenEgg.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    event.setCancelled(true);
                }
            } else if (entityType == EntityType.SPLASH_POTION) {
                if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).throwPotions && !user.bypassing) {
                    p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantPotion.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    event.setCancelled(true);
                }
            } else if (entityType == EntityType.FISHING_HOOK) {
                if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).fish && !user.bypassing) {
                    p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantFish.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    event.setCancelled(true);
                }
            }
        }
    }
}

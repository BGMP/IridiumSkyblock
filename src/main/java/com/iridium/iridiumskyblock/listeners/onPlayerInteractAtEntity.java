package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.PlayerInventory;

public class onPlayerInteractAtEntity implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity == null) return;

        EntityType entityType = entity.getType();

        Player player = event.getPlayer();
        PlayerInventory playerInventory = player.getInventory();
        if (playerInventory.getItemInMainHand() == null
                || playerInventory.getItemInOffHand() == null
                || playerInventory.getItemInHand() == null
        ) return;

        User user = User.getUser(player);

        Island island = user.getIsland();
        if (island == null) return;

        Material mainHand = playerInventory.getItemInMainHand().getType();
        Material offHand = playerInventory.getItemInOffHand().getType();

        if ((entityType == EntityType.COW || entityType == EntityType.MUSHROOM_COW) && (mainHand == Material.BUCKET || offHand == Material.BUCKET)) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).milk && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantMilk.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        } else if (entityType == EntityType.SHEEP && (mainHand == Material.SHEARS || offHand == Material.SHEARS)) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).useShears && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantShear.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        } else if (entityType == EntityType.BOAT || entityType == EntityType.MINECART) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).useVehicles && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseVehicles.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        } else if (entityType == EntityType.ITEM_FRAME) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).useItemFrames && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseItemFrames.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        } else if (mainHand == Material.LEASH || offHand == Material.LEASH) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).leash && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseLeash.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        }
    }
}

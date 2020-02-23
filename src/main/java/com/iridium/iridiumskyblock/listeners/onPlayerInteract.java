package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.MultiversionMaterials;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class onPlayerInteract implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) return;

        Player player = event.getPlayer();
        User user = User.getUser(player);
        Island island = user.getIsland();

        if (island == null) return;

        if (event.getAction() == Action.PHYSICAL) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).redstone && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantEnderpearl.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
            return;
        }

        Material clickedMaterial = clickedBlock.getType();

        if (clickedMaterial == Material.ANVIL) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).useAnvil && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseAnvil.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        } else if (clickedMaterial == Material.BEACON) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).useBeacon && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseBeacon.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        } else if (clickedMaterial == Material.WOOD_BUTTON
                || clickedMaterial == Material.STONE_BUTTON
                || clickedMaterial == Material.REDSTONE_COMPARATOR
                || clickedMaterial == Material.REDSTONE_COMPARATOR_OFF
                || clickedMaterial == Material.REDSTONE_COMPARATOR_ON
                || clickedMaterial == Material.DIODE_BLOCK_ON
                || clickedMaterial == Material.DIODE_BLOCK_OFF
                || clickedMaterial == Material.NOTE_BLOCK
                || clickedMaterial == Material.HOPPER
                || clickedMaterial == Material.HOPPER_MINECART
                || clickedMaterial == Material.LEVER
                || clickedMaterial == Material.FENCE_GATE
                || clickedMaterial == Material.TRAP_DOOR
                || clickedMaterial == Material.DARK_OAK_DOOR
                || clickedMaterial == Material.DAYLIGHT_DETECTOR
                || clickedMaterial == Material.DAYLIGHT_DETECTOR_INVERTED
        ) {
            if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).redstone && !user.bypassing) {
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseBeacon.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                event.setCancelled(true);
            }
        }
    }
}

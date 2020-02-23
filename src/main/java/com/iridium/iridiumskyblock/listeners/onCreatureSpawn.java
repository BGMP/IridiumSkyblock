package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class onCreatureSpawn implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
        if (reason == CreatureSpawnEvent.SpawnReason.NATURAL) event.setCancelled(true);
        else if (reason == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            try {
                if (event.isCancelled()) return;
                Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(event.getLocation());

                if (island == null) return;

                EntityType mob = event.getEntity().getType();
                if (mob == EntityType.BAT
                        || mob == EntityType.CHICKEN
                        || mob == EntityType.COW
                        || mob == EntityType.DONKEY
                        || mob == EntityType.HORSE
                        || mob == EntityType.IRON_GOLEM
                        || mob == EntityType.WOLF
                        || mob == EntityType.LLAMA
                        || mob == EntityType.VILLAGER
                        || mob == EntityType.MULE
                        || mob == EntityType.MUSHROOM_COW
                        || mob == EntityType.OCELOT
                        || mob == EntityType.PARROT
                        || mob == EntityType.PIG
                        || mob == EntityType.POLAR_BEAR
                        || mob == EntityType.SHEEP
                        || mob == EntityType.SQUID
                        || mob == EntityType.RABBIT
                ) {
                    if (!island.getGlobals().animalSpawning) event.setCancelled(true);
                } else {
                    if (!island.getGlobals().mobSpawning) event.setCancelled(true);
                }
            } catch (Exception ex) {
                IridiumSkyblock.getInstance().sendErrorMessage(ex);
            }
        }
    }
}

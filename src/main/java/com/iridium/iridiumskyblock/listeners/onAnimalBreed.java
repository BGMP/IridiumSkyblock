package com.iridium.iridiumskyblock.listeners;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class onAnimalBreed implements Listener {

    @EventHandler
    public void onAnimalBreed(EntityBreedEvent event) {
        Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(event.getBreeder().getLocation());
        if (!island.getGlobals().breedAnimals) event.setCancelled(true);
    }
}

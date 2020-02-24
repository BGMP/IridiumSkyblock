package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.configs.Inventories;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MissionsGUI extends GUI implements Listener {

    public MissionsGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().missionsGUISize, IridiumSkyblock.getInventories().missionsGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if (IridiumSkyblock.getIslandManager().islands.containsKey(islandID)) {
            Island island = IridiumSkyblock.getIslandManager().islands.get(islandID);
            for (Inventories.Item item : IridiumSkyblock.getInventories().missionsItems) {
                setItem(item.slot, Utils.makeItemHidden(item, island));
            }
        }
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
        }
    }
}

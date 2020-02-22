package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.configs.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class FlagsGUI extends GUI implements Listener {

    public FlagsGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().flagsGUISize, IridiumSkyblock.getInventories().flagsGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (IridiumSkyblock.getIslandManager().islands.containsKey(islandID)) {
            for (Inventories.Item item : IridiumSkyblock.getInventories().flags.keySet()){
                setItem(item.slot, Utils.makeItemHidden(item, getIsland()));
            }
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);

            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            Player p = (Player) e.getWhoClicked();
            User u = User.getUser(p);

            for (Inventories.Item item : IridiumSkyblock.getInventories().flags.keySet()){
                if (item.slot == e.getSlot()){
                    p.closeInventory();
                    Bukkit.getServer().dispatchCommand(e.getWhoClicked(), IridiumSkyblock.getInventories().flags.get(item));
                    return;
                }
            }
        }
    }

}

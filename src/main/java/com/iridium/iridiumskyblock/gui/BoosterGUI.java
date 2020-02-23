package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BoosterGUI extends GUI implements Listener {

    public BoosterGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().boosterGUISize, IridiumSkyblock.getInventories().boosterGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (IridiumSkyblock.getIslandManager().islands.containsKey(islandID)) {
            if (IridiumSkyblock.getBoosters().spawnerBooster.enabled)
                setItem(IridiumSkyblock.getBoosters().spawnerBooster.slot, Utils.makeItem(IridiumSkyblock.getInventories().spawner, getIsland()));
        }
        setItem(27, Utils.makeItem(IridiumSkyblock.getInventories().goBackArrow, getIsland()));
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            Player p = (Player) e.getWhoClicked();
            Island island = IridiumSkyblock.getIslandManager().islands.get(islandID);
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getSlot() == IridiumSkyblock.getBoosters().spawnerBooster.slot && IridiumSkyblock.getBoosters().spawnerBooster.enabled) {
                if (getIsland().getSpawnerBooster() == 0) {
                    if (Utils.canBuy(p, IridiumSkyblock.getBoosters().spawnerBooster.vaultCost)) {
                        getIsland().setSpawnerBooster(IridiumSkyblock.getBoosters().spawnerBooster.time);
                    } else {
                        e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().cantBuy.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    }
                } else {
                    e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().spawnerBoosterActive.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            } else if (e.getSlot() == 27) {
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is");
            }
        }
    }
}
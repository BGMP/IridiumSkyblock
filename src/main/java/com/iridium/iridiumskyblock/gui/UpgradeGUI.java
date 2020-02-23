package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.configs.Upgrades;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UpgradeGUI extends GUI implements Listener {

    public UpgradeGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().upgradeGUISize, IridiumSkyblock.getInventories().upgradeGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        Island island = getIsland();
        if (island != null) {
            if (IridiumSkyblock.getUpgrades().sizeUpgrade.enabled)
                setItem(IridiumSkyblock.getUpgrades().sizeUpgrade.slot, Utils.makeItemHidden(IridiumSkyblock.getInventories().size, getIsland()));
            setItem(27, Utils.makeItem(IridiumSkyblock.getInventories().goBackArrow, getIsland()));
        }
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            Player p = (Player) e.getWhoClicked();
            if (e.getSlot() == IridiumSkyblock.getUpgrades().sizeUpgrade.slot && IridiumSkyblock.getUpgrades().sizeUpgrade.enabled) {
                if (IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.containsKey(getIsland().getSizeLevel() + 1)) {
                    Upgrades.IslandUpgrade upgrade = IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(getIsland().getSizeLevel() + 1);
                    if (Utils.canBuy(p, upgrade.vaultCost)) {
                        getIsland().setSizeLevel(getIsland().getSizeLevel() + 1);
                    } else {
                        e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().cantBuy.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    }
                } else {
                    e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().maxLevelReached.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            } else if (e.getSlot() == 27) {
                e.getWhoClicked().closeInventory();
                Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is");
            }
        }
    }
}

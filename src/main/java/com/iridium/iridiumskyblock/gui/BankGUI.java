package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.*;
import com.iridium.iridiumskyblock.support.Vault;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BankGUI extends GUI implements Listener {

    public BankGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().bankGUISize, IridiumSkyblock.getInventories().bankGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        if (IridiumSkyblock.getIslandManager().islands.containsKey(islandID)) {
            setItem(IridiumSkyblock.getInventories().deposit.slot == null ? 11 : IridiumSkyblock.getInventories().deposit.slot, Utils.makeItemHidden(IridiumSkyblock.getInventories().deposit, getIsland()));
            setItem(IridiumSkyblock.getInventories().withdraw.slot == null ? 13 : IridiumSkyblock.getInventories().withdraw.slot, Utils.makeItemHidden(IridiumSkyblock.getInventories().withdraw, getIsland()));
            setItem(IridiumSkyblock.getInventories().money.slot == null ? 15 : IridiumSkyblock.getInventories().money.slot, Utils.makeItemHidden(IridiumSkyblock.getInventories().money, getIsland()));
        }
        setItem(27, Utils.makeItem(IridiumSkyblock.getInventories().goBackArrow, getIsland()));
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            Player p = (Player) e.getWhoClicked();

            if (e.getSlot() == (IridiumSkyblock.getInventories().deposit.slot == null ? 11 : IridiumSkyblock.getInventories().deposit.slot)) {
                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().depositAdvice.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                p.closeInventory();
            } else if (e.getSlot() == (IridiumSkyblock.getInventories().withdraw.slot == null ? 13 : IridiumSkyblock.getInventories().withdraw.slot)) {
                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().withdrawAdvice.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                p.closeInventory();
            } else if (e.getSlot() == 27) {
                p.closeInventory();
                Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is");
            }
        }
    }
}
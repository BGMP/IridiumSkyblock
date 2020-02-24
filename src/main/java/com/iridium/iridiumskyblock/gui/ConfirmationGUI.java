package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.XMaterial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ConfirmationGUI extends GUI implements Listener {

    public Runnable runnable;

    public ConfirmationGUI(Island island, Runnable runnable, String action) {
        super(island, 27, IridiumSkyblock.getInventories().confirmationGUITitle.replace("%action%", action));
        IridiumSkyblock.getInstance().registerListeners(this);
        this.runnable = runnable;
        for (int i = 0; i < getInventory().getSize(); i++) {
            setItem(i, Utils.makeItemHidden(IridiumSkyblock.getInventories().background));
        }
        setItem(12, Utils.makeItem(XMaterial.LIME_STAINED_GLASS_PANE, 1, IridiumSkyblock.getMessages().yes));
        setItem(14, Utils.makeItem(XMaterial.RED_STAINED_GLASS_PANE, 1, IridiumSkyblock.getMessages().no));
    }

    @Override
    public void addContent() {
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getSlot() == 12) {
                e.getWhoClicked().closeInventory();
                runnable.run();
            } else if (e.getSlot() == 14) {
                e.getWhoClicked().closeInventory();
            }
        }
    }
}
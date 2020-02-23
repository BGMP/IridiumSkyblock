package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.NMSUtils;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BorderColorGUI extends GUI implements Listener {

    public ItemStack red;
    public ItemStack green;
    public ItemStack blue;
    public ItemStack off;

    public BorderColorGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().borderColorGUISize, IridiumSkyblock.getInventories().borderColorGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        this.red = Utils.makeItem(IridiumSkyblock.getInventories().red);
        this.green = Utils.makeItem(IridiumSkyblock.getInventories().green);
        this.blue = Utils.makeItem(IridiumSkyblock.getInventories().blue);
        this.off = Utils.makeItem(IridiumSkyblock.getInventories().off);

        setItem(11, this.red);
        setItem(13, this.blue);
        setItem(15, this.green);
        setItem(27, Utils.makeItem(IridiumSkyblock.getInventories().goBackArrow, getIsland()));
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (e.getCurrentItem() != null) {
                if (e.getCurrentItem().equals(blue))
                    IridiumSkyblock.getIslandManager().getIslandViaId(islandID).setBorderColor(NMSUtils.Color.Blue);
                if (e.getCurrentItem().equals(red))
                    IridiumSkyblock.getIslandManager().getIslandViaId(islandID).setBorderColor(NMSUtils.Color.Red);
                if (e.getCurrentItem().equals(green))
                    IridiumSkyblock.getIslandManager().getIslandViaId(islandID).setBorderColor(NMSUtils.Color.Green);
                if (e.getCurrentItem().equals(off))
                    IridiumSkyblock.getIslandManager().getIslandViaId(islandID).setBorderColor(NMSUtils.Color.Off);
                IridiumSkyblock.getIslandManager().getIslandViaId(islandID).sendBorder();
                if (e.getSlot() == 27) {
                    e.getWhoClicked().closeInventory();
                    Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is");
                }
            }
        }
    }
}

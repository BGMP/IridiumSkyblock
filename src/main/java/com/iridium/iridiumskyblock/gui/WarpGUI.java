package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Collections;
import java.util.HashMap;

public class WarpGUI extends GUI implements Listener {
    public HashMap<Integer, Island.Warp> warps = new HashMap<>();

    public WarpGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().warpGUISize, IridiumSkyblock.getInventories().warpGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (IridiumSkyblock.getIslandManager().islands.containsKey(islandID)) {
            Island island = IridiumSkyblock.getIslandManager().islands.get(islandID);
            int i = 9;
            warps.clear();
            for (Island.Warp warp : island.getWarps()) {
                warps.put(i, warp);
                setItem(i, Utils.makeItem(IridiumSkyblock.getInventories().islandWarp, Collections.singletonList(new Utils.Placeholder("warp", warp.getName()))));
                i++;
            }
            setItem(27, Utils.makeItem(IridiumSkyblock.getInventories().goBackArrow, getIsland()));
        }
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            Player p = (Player) e.getWhoClicked();
            User u = User.getUser(p);
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (warps.containsKey(e.getSlot())) {
                Island.Warp warp = warps.get(e.getSlot());
                if (e.getClick().equals(ClickType.RIGHT)) {
                    u.getIsland().removeWarp(warps.get(e.getSlot()));
                    getInventory().clear();
                    addContent();
                } else {
                    if (warp.getPassword().isEmpty()) {
                        p.teleport(warp.getLocation());
                        p.sendMessage(Utils.color(IridiumSkyblock.getMessages().teleporting.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    } else {
                        p.sendMessage(Utils.color(IridiumSkyblock.getMessages().enterPassword.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        u.warp = warp;
                    }
                }
                p.closeInventory();
            } else if (e.getSlot() == 27) {
                e.getWhoClicked().closeInventory();
                Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is");
            }
        }
    }
}

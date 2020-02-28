package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TopGUI extends GUI implements Listener {

    public HashMap<Integer, Integer> islands = new HashMap<>();

    public TopGUI() {
        super(IridiumSkyblock.getInventories().topGUISize, IridiumSkyblock.getInventories().topGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        List<Island> top = Utils.getTopIslands();
        for (int i : IridiumSkyblock.getConfiguration().islandTopSlots.keySet()) {
            if (top.size() >= i) {
                Island island = top.get(i - 1);
                User owner = User.getUser(island.getOwner());
                ItemStack head = Utils.makeItem(IridiumSkyblock.getInventories().topisland, Arrays.asList(new Utils.Placeholder("player", owner.name), new Utils.Placeholder("name", island.getName()), new Utils.Placeholder("rank", i + ""), new Utils.Placeholder("value", NumberFormat.getInstance().format(island.getValue()) + "")));
                islands.put(IridiumSkyblock.getConfiguration().islandTopSlots.get(i), island.getId());
                setItem(IridiumSkyblock.getConfiguration().islandTopSlots.get(i), head);
            } else {
                setItem(IridiumSkyblock.getConfiguration().islandTopSlots.get(i), Utils.makeItemHidden(IridiumSkyblock.getInventories().background));
            }
        }
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (islands.containsKey(e.getSlot())) {
                e.getWhoClicked().closeInventory();
                Island island = IridiumSkyblock.getIslandManager().getIslandViaId(islands.get(e.getSlot()));
                if (island.isVisit() || User.getUser((OfflinePlayer) e.getWhoClicked()).bypassing) {
                    island.teleportHome((Player) e.getWhoClicked());
                } else {
                    e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().playersIslandIsPrivate.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            }
        }
    }
}
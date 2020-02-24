package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

public class LanguagesGUI extends GUI implements Listener {

    int page;

    public HashMap<Integer, LanguagesGUI> pages;

    public HashMap<Integer, String> languages;

    public LanguagesGUI() {
        pages = new HashMap<>();
        for (int i = 1; i <= Math.ceil(IridiumSkyblock.getInstance().languages.size() / 45.00); i++) {
            pages.put(i, new LanguagesGUI(i));
        }
    }

    public LanguagesGUI(int page) {
        super(54, "&7Languages");
        this.page = page;
        languages = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, IridiumSkyblock.getInstance());
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        languages.clear();
        int slot = 0;
        for (int i = 0; i < IridiumSkyblock.getInstance().languages.size(); i++) {
            if (i >= (page-1) * 45 && i < page * 54) {
                languages.put(slot, IridiumSkyblock.getInstance().languages.get(i));
                setItem(slot, Utils.makeItem(XMaterial.PAPER, 1, "&b&l" + IridiumSkyblock.getInstance().languages.get(i)));
                slot++;
            }
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (languages.containsKey(e.getSlot())) {
                IridiumSkyblock.getInstance().setLanguage(languages.get(e.getSlot()));
                e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().reloaded.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
    }
}

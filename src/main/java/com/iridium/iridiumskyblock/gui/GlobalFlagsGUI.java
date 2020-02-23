package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.GlobalPermissions;
import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.configs.Inventories;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GlobalFlagsGUI extends GUI implements Listener {

    public GlobalFlagsGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().globalsGUISize, IridiumSkyblock.getInventories().globalsGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getIsland() != null) {
            int i = 0;
            try {
                for (Field field : GlobalPermissions.class.getDeclaredFields()) {
                    GlobalPermission globalPermission = field.getAnnotation(GlobalPermission.class);

                    Object object = field.get(getIsland().getGlobals());
                    if (object instanceof Boolean) {
                        List<String> stateLore;

                        if ((Boolean) object) stateLore = Arrays.asList(ChatColor.GREEN.toString() + ChatColor.BOLD + "ENABLED");
                        else stateLore = Arrays.asList(ChatColor.RED.toString() + ChatColor.BOLD + "DISABLED");

                        Inventories.Item icon = new Inventories.Item(globalPermission.icon(), 1, "&b" + globalPermission.name(), stateLore);
                        setItem(i, Utils.makeItem(icon, Collections.singletonList(new Utils.Placeholder("permission", IridiumSkyblock.getMessages().permissions.getOrDefault(field.getName(), field.getName())))));
                    }
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setItem(45, Utils.makeItem(IridiumSkyblock.getInventories().goBackArrow, getIsland()));
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        int i = 0;
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            try {
                for (Field field : GlobalPermissions.class.getDeclaredFields()) {
                    Object object = field.get(getIsland().getGlobals());
                    if (i == e.getSlot()) {
                        field.setAccessible(true);
                        field.setBoolean(getIsland().getGlobals(), !(Boolean) object);
                        addContent();
                    }
                    i++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (e.getSlot() == 45) {
            e.getWhoClicked().closeInventory();
            Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is flags");
        }
    }
}

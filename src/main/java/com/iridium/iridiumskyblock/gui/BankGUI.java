package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.*;
import com.iridium.iridiumskyblock.support.Vault;
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
        if (IridiumSkyblock.getIslandManager().islands.containsKey(islandID)) {
            setItem(IridiumSkyblock.getInventories().money.slot == null ? 15 : IridiumSkyblock.getInventories().money.slot, Utils.makeItemHidden(IridiumSkyblock.getInventories().money, getIsland()));
        }
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            Player p = (Player) e.getWhoClicked();
            Island island = getIsland();
            User u = User.getUser(p);
            if (e.getSlot() == (IridiumSkyblock.getInventories().money.slot == null ? 13 : IridiumSkyblock.getInventories().money.slot)) {
                if (Vault.econ != null) {
                    if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                        if ((island.getPermissions((u.islandID == island.getId() || island.isCoop(u.getIsland())) ? (island.isCoop(u.getIsland()) ? Role.Member : u.getRole()) : Role.Visitor).withdrawBank) || u.bypassing) {
                            Vault.econ.depositPlayer(p, island.money);
                            island.money = 0;
                        }
                    } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                        island.money += Vault.econ.getBalance(p);
                        Vault.econ.withdrawPlayer(p, Vault.econ.getBalance(p));
                    } else if (e.getClick().equals(ClickType.RIGHT)) {
                        if (Vault.econ.getBalance(p) > 1000) {
                            island.money += 1000;
                            Vault.econ.withdrawPlayer(p, 1000);
                        } else {
                            island.money += Vault.econ.getBalance(p);
                            Vault.econ.withdrawPlayer(p, Vault.econ.getBalance(p));
                        }
                    } else if (e.getClick().equals(ClickType.LEFT)) {
                        if ((island.getPermissions((u.islandID == island.getId() || island.isCoop(u.getIsland())) ? (island.isCoop(u.getIsland()) ? Role.Member : u.getRole()) : Role.Visitor).withdrawBank) || u.bypassing) {
                            if (island.money > 1000) {
                                island.money -= 1000;
                                Vault.econ.depositPlayer(p, 1000);
                            } else {
                                Vault.econ.depositPlayer(p, island.money);
                                island.money = 0;
                            }
                        }
                    }
                }
            }
        }
    }
}
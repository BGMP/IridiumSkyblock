package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.MultiversionMaterials;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.configs.Shop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public class ShopGUI extends GUI implements Listener {

    public ShopGUI root;

    public Shop.ShopObject shop;

    public int page;

    public HashMap<Integer, ShopGUI> shops = new HashMap<>();

    public HashMap<Integer, Shop.ShopItem> items = new HashMap<>();

    public ShopGUI() {
        super(IridiumSkyblock.getInventories().shopGUISize, IridiumSkyblock.getInventories().shopGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    public ShopGUI(Shop.ShopObject shop, int page, ShopGUI root) {
        super(IridiumSkyblock.getInventories().shopGUISize, IridiumSkyblock.getInventories().shopGUITitle);
        this.shop = shop;
        this.page = page;
        this.root = root;
    }

    public ShopGUI(Shop.ShopObject shop, ShopGUI root) {
        scheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(IridiumSkyblock.getInstance(), this::addPages, 0, 5);
        this.shop = shop;
        this.root = root;

    }

    public void addPages() {
        for (Shop.ShopItem item : shop.items) {
            if (!shops.containsKey(item.page)) {
                shops.put(item.page, new ShopGUI(shop, item.page, this));
            }
        }
    }

    @Override
    public void addContent() {
        super.addContent();
        if (!IridiumSkyblock.getConfiguration().islandShop) return;
        if (shop == null) {
            for (Shop.ShopObject shop : IridiumSkyblock.getShop().shop) {
                setItem(shop.slot, Utils.makeItem(shop.display, 1, shop.displayName));
                if (!shops.containsKey(shop.slot)) {
                    shops.put(shop.slot, new ShopGUI(shop, this));
                }
            }
        } else {
            for (Shop.ShopItem item : shop.items) {
                if (item.page == page) {
                    items.put(item.slot, item);
                    setItem(item.slot, Utils.makeItem(item.material, item.amount, item.displayName, Utils.color(Utils.processMultiplePlaceholders(IridiumSkyblock.getShop().lore, Arrays.asList(new Utils.Placeholder("buyvaultprice", item.buyVault + ""), new Utils.Placeholder("sellvaultprice", item.sellVault + ""), new Utils.Placeholder("buycrystalprice", item.buyCrystals + ""), new Utils.Placeholder("sellcrystalprice", item.sellCrystals + ""))))));
                }
            }
            setItem(getInventory().getSize() - 3, Utils.makeItem(IridiumSkyblock.getInventories().nextPage));
            setItem(getInventory().getSize() - 5, Utils.makeItem(IridiumSkyblock.getInventories().back));
            setItem(getInventory().getSize() - 7, Utils.makeItem(IridiumSkyblock.getInventories().previousPage));
        }
    }

    public boolean contains(Player p, MultiversionMaterials materials, int amount) {
        int total = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item == null) continue;
            if (item.getType().equals(materials.parseMaterial())) {
                if (item.getData().getData() == materials.data) {
                    total += item.getAmount();
                }
            }
        }
        return total >= amount;
    }

    @EventHandler
    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (shop == null) {
                if (shops.containsKey(e.getSlot())) {
                    if (shops.get(e.getSlot()).shops.containsKey(1)) { // This should always be called, but just incase the user configured the plugin incorrectly
                        e.getWhoClicked().openInventory(shops.get(e.getSlot()).shops.get(1).getInventory());
                    }
                }
            } else {
                if (items.containsKey(e.getSlot())) {
                    Shop.ShopItem item = items.get(e.getSlot());
                    if (e.getClick().equals(ClickType.RIGHT)) {
                        if (contains((Player) e.getWhoClicked(), item.material, item.amount)) {
                            int removed = 0;
                            int index = 0;
                            for (ItemStack itemStack : e.getWhoClicked().getInventory().getContents()) {
                                if (removed >= item.amount) break;
                                if (itemStack != null) {
                                    if (itemStack.getType().equals(item.material.parseMaterial()) && itemStack.getData().getData() == item.material.data) {
                                        if (removed + itemStack.getAmount() <= item.amount) {
                                            removed += itemStack.getAmount();
                                            e.getWhoClicked().getInventory().setItem(index, null);
                                        } else {
                                            itemStack.setAmount(itemStack.getAmount() - (item.amount - removed));
                                            removed += item.amount;
                                        }
                                    }
                                }
                                index++;
                            }
                            Utils.pay((Player) e.getWhoClicked(), item.sellVault, item.sellCrystals);
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().cantSell.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        if (Utils.canBuy((Player) e.getWhoClicked(), item.buyVault)) {
                            e.getWhoClicked().getInventory().addItem(new ItemStack(item.material.parseMaterial(), item.amount, (short) item.material.data));
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().cantBuy.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        }
                    }
                }
                if (e.getSlot() == getInventory().getSize() - 3) {
                    if (root.shops.containsKey(page + 1)) {
                        e.getWhoClicked().openInventory(root.shops.get(page + 1).getInventory());
                    }
                }
                if (e.getSlot() == getInventory().getSize() - 5) {
                    e.getWhoClicked().openInventory(root.root.getInventory());
                }
                if (e.getSlot() == getInventory().getSize() - 7) {
                    if (root.shops.containsKey(page - 1)) {
                        e.getWhoClicked().openInventory(root.shops.get(page - 1).getInventory());
                    }
                }
            }
        }
        for (ShopGUI gui : shops.values()) {
            gui.onInventoryClick(e);
        }
    }
}
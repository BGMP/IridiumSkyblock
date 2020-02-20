package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.configs.Inventories;
import com.iridium.iridiumskyblock.configs.Missions;
import com.iridium.iridiumskyblock.support.Vault;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static Object getObject(List<String> fields) {
        Object object = IridiumSkyblock.getInstance();
        for (String field : fields) {
            try {
                if (object instanceof List) {
                    List<Object> objects = (List<Object>) object;
                    if (objects.size() > Integer.parseInt(field)) {
                        object = objects.get(Integer.parseInt(field));
                    } else {
                        return object;
                    }
                } else if (object instanceof HashSet) {
                    HashSet<Object> objects = (HashSet<Object>) object;
                    int i = 0;
                    for (Object o : objects) {
                        if ((i == Integer.parseInt(field))) {
                            object = o;
                        }
                        i++;
                    }
                } else {
                    object = object.getClass().getField(field).get(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static Field getField(List<String> fields) {
        Object object = IridiumSkyblock.getInstance();
        Field f = null;
        for (String field : fields) {
            try {
                if (object instanceof List) {
                    List<Object> objects = (List<Object>) object;
                    if (objects.size() > Integer.parseInt(field)) {
                        f = object.getClass().getField(field);
                        f.setAccessible(true);
                        object = objects.get(Integer.parseInt(field));
                    } else {
                        return f;
                    }
                } else if (object instanceof HashSet) {
                    HashSet<Object> objects = (HashSet<Object>) object;
                    int i = 0;
                    for (Object o : objects) {
                        if ((i == Integer.parseInt(field))) {
                            f = object.getClass().getField(field);
                            f.setAccessible(true);
                            object = o;
                        }
                        i++;
                    }
                } else {
                    f = object.getClass().getField(field);
                    f.setAccessible(true);
                    object = object.getClass().getField(field).get(object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static ItemStack makeItem(Material material, int amount, int type, String name, List<String> lore, Object object) {
        ItemStack item = new ItemStack(material, amount, (short) type);
        ItemMeta m = item.getItemMeta();
        m.setLore(lore);
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(MultiversionMaterials material, int amount, String name) {
        ItemStack item = new ItemStack(material.parseMaterial(), amount, (short) material.data);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(MultiversionMaterials material, int amount, String name, List<String> lore) {
        ItemStack item = new ItemStack(material.parseMaterial(), amount, (short) material.data);
        ItemMeta m = item.getItemMeta();
        m.setLore(Utils.color(lore));
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static ItemStack makeItem(Inventories.Item item, List<Placeholder> placeholders) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders));
            if (item.material == MultiversionMaterials.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(processMultiplePlaceholders(item.headOwner, placeholders));
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(MultiversionMaterials.STONE, item.amount, processMultiplePlaceholders(item.title, placeholders), processMultiplePlaceholders(item.lore, placeholders));
        }
    }

    public static ItemStack makeItem(Inventories.Item item) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, item.title, item.lore);
            if (item.material == MultiversionMaterials.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(MultiversionMaterials.STONE, item.amount, item.title, item.lore);
        }
    }

    public static ItemStack makeItem(Inventories.Item item, Island island) {
        try {
            ItemStack itemstack = makeItem(item.material, item.amount, processIslandPlaceholders(item.title, island), color(processIslandPlaceholders(item.lore, island)));
            if (item.material == MultiversionMaterials.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItem(MultiversionMaterials.STONE, item.amount, processIslandPlaceholders(item.title, island), color(processIslandPlaceholders(item.lore, island)));
        }
    }

    public static ItemStack makeItemHidden(Inventories.Item item) {
        try {
            ItemStack itemstack = makeItemHidden(item.material.parseMaterial(), item.amount, item.material.data, item.title, item.lore);
            if (item.material == MultiversionMaterials.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItemHidden(Material.STONE, item.amount, item.material.data, item.title, item.lore);
        }
    }

    public static ItemStack makeItemHidden(Inventories.Item item, Island island) {
        try {
            ItemStack itemstack = makeItemHidden(item.material.parseMaterial(), item.amount, item.material.data, processIslandPlaceholders(item.title, island), color(processIslandPlaceholders(item.lore, island)));
            if (item.material == MultiversionMaterials.PLAYER_HEAD && item.headOwner != null) {
                SkullMeta m = (SkullMeta) itemstack.getItemMeta();
                m.setOwner(item.headOwner);
                itemstack.setItemMeta(m);
            }
            return itemstack;
        } catch (Exception e) {
            return makeItemHidden(Material.STONE, item.amount, item.material.data, processIslandPlaceholders(item.title, island), color(processIslandPlaceholders(item.lore, island)));
        }
    }

    public static ItemStack makeItemHidden(Material material, int amount, int type, String name, List<String> lore) {
        ItemStack item = new ItemStack(material, amount, (short) type);
        ItemMeta m = item.getItemMeta();
        m.setLore(Utils.color(lore));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE);
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(m);
        return item;
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> strings) {
        return strings.stream().map(Utils::color).collect(Collectors.toList());
    }

    public static boolean isBlockValuable(Block b) {
        return IridiumSkyblock.getBlockValues().blockvalue.containsKey(MultiversionMaterials.fromMaterial(b.getType())) || b.getState() instanceof CreatureSpawner;
    }

    public static List<Island> getTopIslands() {
        List<Island> islands = new ArrayList<>(IridiumSkyblock.getIslandManager().islands.values());
        islands.sort(Comparator.comparingInt(Island::getValue));
        Collections.reverse(islands);
        return islands;
    }

    public static List<Island> getIslands() {
        List<Island> islands = new ArrayList<>(IridiumSkyblock.getIslandManager().islands.values());
        islands.sort(Comparator.comparingInt(Island::getVotes));
        Collections.reverse(islands);
        return islands;
    }

    public static boolean isSafe(Location loc, Island island) {
        if (!island.isInIsland(loc)) {
            return false;
        }
        if (!loc.getBlock().getType().equals(Material.AIR)) {
            return false;
        }
        if (loc.getBlock().isLiquid()) {
            return false;
        }
        if (loc.add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
            return false;
        }
        return true;
    }

    public static int getIslandRank(Island island) {
        int i = 1;
        for (Island is : getTopIslands()) {
            if (is.equals(island)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public static Location getNewHome(Island island, Location loc) {
        Block b = loc.getWorld().getHighestBlockAt(loc);
        if (isSafe(b.getLocation(), island)) {
            return b.getLocation().add(0.5, 1, 0.5);
        }

        for (double X = island.getPos1().getX(); X <= island.getPos2().getX(); X++) {
            for (double Z = island.getPos1().getZ(); Z <= island.getPos2().getZ(); Z++) {
                b = loc.getWorld().getHighestBlockAt((int) X, (int) Z);
                if (isSafe(b.getLocation(), island)) {
                    return b.getLocation().add(0.5, 1, 0.5);
                }
            }
        }
        return null;
    }

    public static String processIslandPlaceholders(String line, Island island) {
        List<Placeholder> placeholders = new ArrayList<>(Arrays.asList(
                // Upgrades
                new Placeholder("sizecost", IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.containsKey(island.getSizeLevel() + 1) ? IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(island.getSizeLevel() + 1).vaultCost + "" : IridiumSkyblock.getMessages().maxlevelreached),

                new Placeholder("sizeblocks", IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(island.getSizeLevel()).size + ""),

                new Placeholder("sizelevel", island.getSizeLevel() + ""),
                new Placeholder("memberlevel", island.getMemberLevel() + ""),
                new Placeholder("warplevel", island.getWarpLevel() + ""),
                new Placeholder("oreslevel", island.getOreLevel() + ""),
                new Placeholder("generatorlevel", island.getOreLevel() + ""),
                // Boosters
                new Placeholder("spawnerbooster", island.getSpawnerBooster() + ""),
                new Placeholder("farmingbooster", island.getFarmingBooster() + ""),
                new Placeholder("expbooster", island.getExpBooster() + ""),
                new Placeholder("flightbooster", island.getFlightBooster() + ""),

                new Placeholder("spawnerbooster_seconds", island.getSpawnerBooster() % 60 + ""),
                new Placeholder("farmingbooster_seconds", island.getFarmingBooster() % 60 + ""),
                new Placeholder("expbooster_seconds", island.getExpBooster() % 60 + ""),
                new Placeholder("flightbooster_seconds", island.getFlightBooster() % 60 + ""),
                new Placeholder("spawnerbooster_minutes", (int) Math.floor(island.getSpawnerBooster() / 60.00) + ""),
                new Placeholder("farmingbooster_minutes", (int) Math.floor(island.getFarmingBooster() / 60.00) + ""),
                new Placeholder("expbooster_minutes", (int) Math.floor(island.getExpBooster() / 60.00) + ""),
                new Placeholder("flightbooster_minutes", (int) Math.floor(island.getFlightBooster() / 60.00) + ""),
                new Placeholder("spawnerbooster_vaultcost", IridiumSkyblock.getBoosters().spawnerBooster.vaultCost + ""),

                //Bank
                new Placeholder("experience", island.exp + ""),
                new Placeholder("crystals", island.getCrystals() + ""),
                new Placeholder("money", island.money + "")));
        //Status amount crystals vault
        for (String mission : IridiumSkyblock.getMissions().mission.keySet()) {
            int amount = island.getMission(mission);
            if (!island.getMissionLevels().containsKey(mission)) island.getMissionLevels().put(mission, 1);
            Missions.Mission m = IridiumSkyblock.getMissions().mission.get(mission).get(island.getMissionLevels().get(mission));
            placeholders.add(new Placeholder(mission + "status", amount == Integer.MIN_VALUE ? IridiumSkyblock.getMessages().completed : amount + "/" + m.amount));
            placeholders.add(new Placeholder(mission + "amount", m.amount + ""));
            placeholders.add(new Placeholder(mission + "crystals", m.crystalReward + ""));
            placeholders.add(new Placeholder(mission + "vault", m.vaultReward + ""));
            placeholders.add(new Placeholder(mission + "level", island.getMissionLevels().get(mission) + ""));
        }
        return processMultiplePlaceholders(line, placeholders);
    }

    public static List<String> processIslandPlaceholders(List<String> lines, Island island) {
        List<String> newlist = new ArrayList<>();
        for (String string : lines) {
            newlist.add(processIslandPlaceholders(string, island));
        }
        return newlist;
    }

    public static List<String> processMultiplePlaceholders(List<String> lines, List<Placeholder> placeholders) {
        List<String> newlist = new ArrayList<>();
        for (String string : lines) {
            newlist.add(processMultiplePlaceholders(string, placeholders));
        }
        return newlist;
    }

    public static String processMultiplePlaceholders(String line, List<Placeholder> placeholders) {
        for (Placeholder placeholder : placeholders) {
            line = placeholder.process(line);
        }
        return color(line);
    }

    public static void pay(Player p, int vault, int crystals) {
        User u = User.getUser(p);
        if (u.getIsland() != null) {
            u.getIsland().setCrystals(u.getIsland().getCrystals() + crystals);
            if (Vault.econ == null) {
                u.getIsland().money += vault;
            } else {
                Vault.econ.depositPlayer(p, vault);
            }
        } else {
            if (Vault.econ == null) {
                IridiumSkyblock.getInstance().getLogger().warning("Vault plugin not found");
                return;
            }
            Vault.econ.depositPlayer(p, vault);
        }
    }

    public static boolean canBuy(Player p, int vault) {
        User u = User.getUser(p);
        if (u.getIsland() != null) {
            if (Vault.econ != null) {
                boolean canbuy = Vault.econ.getBalance(p) >= vault;
                if (canbuy) Vault.econ.withdrawPlayer(p, vault);
                return canbuy;
            } else {
                p.sendMessage(ChatColor.RED + "There is no economy on this server!");
                return false;
            }
        } else if (Vault.econ != null) {
            boolean canbuy = Vault.econ.getBalance(p) >= vault;
            if (canbuy) {
                Vault.econ.withdrawPlayer(p, vault);
            }
            return canbuy;
        }
        return false;
    }

    public static int getExpAtLevel(final int level) {
        if (level <= 15) {
            return (2 * level) + 7;
        } else if (level <= 30) {
            return (5 * level) - 38;
        }
        return (9 * level) - 158;
    }

    public static int getTotalExperience(final Player player) {
        int exp = Math.round(getExpAtLevel(player.getLevel()) * player.getExp());
        int currentLevel = player.getLevel();

        while (currentLevel > 0) {
            currentLevel--;
            exp += getExpAtLevel(currentLevel);
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public static void setTotalExperience(final Player player, final int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience is negative!");
        }
        player.setExp(0);
        player.setLevel(0);
        player.setTotalExperience(0);

        int amount = exp;
        while (amount > 0) {
            final int expToLevel = getExpAtLevel(player.getLevel());
            amount -= expToLevel;
            if (amount >= 0) {
                // give until next level
                player.giveExp(expToLevel);
            } else {
                // give the rest
                amount += expToLevel;
                player.giveExp(amount);
                amount = 0;
            }
        }
    }

    public static class Placeholder {

        private String key;
        private String value;

        public Placeholder(String key, String value) {
            this.key = "{" + key + "}";
            this.value = value;
        }

        public String process(String line) {
            return line.replace(key, value);
        }
    }
}

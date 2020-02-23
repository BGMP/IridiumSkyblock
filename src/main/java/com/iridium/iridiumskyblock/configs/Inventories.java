package com.iridium.iridiumskyblock.configs;

import com.iridium.iridiumskyblock.MultiversionMaterials;

import java.util.*;

public class Inventories {
    public String upgradeGUITitle = "&bUpgrade";
    public String boosterGUITitle = "&bBooster";
    public String confirmationGUITitle = "&b7Are you sure?";
    public String missionsGUITitle = "&bMissions";
    public String membersGUITitle = "&bMembers";
    public String coopGUITitle = "&bCo-op Islands";
    public String islandMenuGUITitle = "&bMenu";
    public String warpGUITitle = "&bWarps";
    public String topGUITitle = "&bTop Islands";
    public String borderColorGUITitle = "&bBorder Color";
    public String permissionsGUITitle = "&bPermissions";
    public String schematicselectGUITitle = "&bSelect an Island";
    public String bankGUITitle = "&bIsland Bank";
    public String visitGUITitle = "&bVisit an Island";
    public String shopGUITitle = "&bIsland Shop";
    public String flagsGUITitle = "&bIsland Flags";
    public String globalsGUITitle = "&bGlobal Flags";

    public int upgradeGUISize = 36;
    public int boosterGUISize = 36;
    public int missionsGUISize = 36;
    public int membersGUISize = 36;
    public int coopGUISize = 36;
    public int islandMenuGUISize = 45;
    public int warpGUISize = 36;
    public int topGUISize = 36;
    public int borderColorGUISize = 36;
    public int schematicselectGUISize = 36;
    public int bankGUISize = 36;
    public int visitGUISize = 54;
    public int shopGUISize = 54;
    public int permissionsGUISize = 36;
    public int flagsGUISize = 36;
    public int globalsGUISize = 54;

    public Item goBackArrow = new Item(MultiversionMaterials.ARROW, 1, "&aGo Back", Collections.emptyList());

    //Boosters
    public Item spawner = new Item(MultiversionMaterials.SPAWNER, 1, "&b&lIncreased Mobs", Arrays.asList("&7Are your spawners too slow? Buy this", "&7booster and increase spawner rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{spawnerbooster_minutes} minutes and {spawnerbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{spawnerbooster_crystalcost} Crystals and ${spawnerbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."));
    public Item farming = new Item(MultiversionMaterials.WHEAT, 1, "&b&lIncreased Crops", Arrays.asList("&7Are your crops too slow? Buy this", "&7booster and increase crop growth rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{farmingbooster_minutes} minutes and {farmingbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{farmingbooster_crystalcost} Crystals and ${farmingbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."));
    public Item exp = new Item(MultiversionMaterials.EXPERIENCE_BOTTLE, 1, "&b&lIncreased Experience", Arrays.asList("&7Takes too long to get exp? Buy this", "&7booster and exp rates x2.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{expbooster_minutes} minutes and {expbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{expbooster_crystalcost} Crystals and ${expbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."));
    public Item flight = new Item(MultiversionMaterials.FEATHER, 1, "&b&lIncreased Flight", Arrays.asList("&7Tired of falling off your island? Buy this", "&7booster and allow all members to fly.", "", "&b&lInformation:", "&b&l * &7Time Remaining: &b{flightbooster_minutes} minutes and {flightbooster_seconds}seconds", "&b&l * &7Booster Cost: &b{flightbooster_crystalcost} Crystals and ${flightbooster_vaultcost}", "", "&b&l[!] &bRight Click to Purchase this Booster."));

    //Upgrade
    public Item size = new Item(MultiversionMaterials.GRASS, 1, "&b&lIsland Size", Arrays.asList("&7Need more room to expand? Buy this", "&7upgrade to increase your island size.", "", "&b&lInformation:", "&b&l * &7Current Level: &b{sizelevel}", "&b&l * &7Current Size: &b{sizeblocks}x{sizeblocks} Blocks", "&b&l * &7Upgrade Cost: ${sizecost}", "&b&lLevels:", "&b&l * &7Level 1: &b50x50 Blocks", "&b&l * &7Level 2: &b100x100 Blocks", "&b&l * &7Level 3: &b150x150 Blocks", "&b&l * &7Level 4: &b250x250 Blocks", "&b&l * &7Level 5: &b350x350 Blocks", "&b&l * &7Level 6: &b500x500 Blocks", "", "&b&l[!] &bLeft Click to Purchase this Upgrade"));

    //Bank
    public Item deposit = new Item(MultiversionMaterials.IRON_INGOT, 11, 1, "&6&lDeposit", Arrays.asList("&7Deposit money into Island"));
    public Item withdraw = new Item(MultiversionMaterials.GOLD_INGOT, 13, 1, "&b&lWithdraw", Arrays.asList("&7Withdraw money from Island"));
    public Item money = new Item(MultiversionMaterials.PAPER, 15, 1, "&b&lBalance", Arrays.asList("&7Current Balance is: &a${money}"));

    public Item crystal = new Item(MultiversionMaterials.NETHER_STAR, 1, "&b*** &b&lIsland Crystal &b***", Arrays.asList("", "&b&l[!] &bto Redeem, go to your Island Bank"));

    public Item background = new Item(MultiversionMaterials.BLACK_STAINED_GLASS_PANE, 1, " ", new ArrayList<>());

    public Item nextPage = new Item(MultiversionMaterials.LIME_STAINED_GLASS_PANE, 1, "&a&lNext Page", new ArrayList<>());

    public Item previousPage = new Item(MultiversionMaterials.RED_STAINED_GLASS_PANE, 1, "&c&lPrevious Page", new ArrayList<>());

    public Item biome = new Item(MultiversionMaterials.GRASS, 1, "&b&l{biome} Biome", new ArrayList<>());

    public Item back = new Item(MultiversionMaterials.NETHER_STAR, 1, "&c&lBack", new ArrayList<>());

    public Item islandmember = new Item(MultiversionMaterials.PLAYER_HEAD, 1, "&b&l{player}", "{player}", Arrays.asList("&bRole: {role}", "", "&b&l[!] &bLeft Click to {demote}" + " this Player.", "&b&l[!] &bRight Click to Promote this Player."));
    public Item islandcoop = new Item(MultiversionMaterials.PLAYER_HEAD, 1, "&b&l{player}", "{player}", Arrays.asList("&b&l * &7Island: &b{name}", "&b&l * &7Rank: &b{rank}", "&b&l * &7Value: &b{value}", "", "&b&l[!] &bLeft Click to Teleport to this island.", "&b&l[!] &bRight Click to un co-op this island."));
    public Item[] islandRoles = {
            new Item(MultiversionMaterials.BEACON, 1, "&b&l{role}", Collections.emptyList()),
            new Item(MultiversionMaterials.EMERALD_BLOCK, 1, "&b&l{role}", Collections.emptyList()),
            new Item(MultiversionMaterials.GOLD_BLOCK, 1, "&b&l{role}", Collections.emptyList()),
            new Item(MultiversionMaterials.IRON_BLOCK, 1, "&b&l{role}", Collections.emptyList()),
            new Item(MultiversionMaterials.COAL_BLOCK, 1, "&b&l{role}", Collections.emptyList())
    };
    public Item islandPermissionAllow = new Item(MultiversionMaterials.LIME_STAINED_GLASS_PANE, 1, "&b&l{permission}", Collections.emptyList());
    public Item islandPermissionDeny = new Item(MultiversionMaterials.RED_STAINED_GLASS_PANE, 1, "&b&l{permission}", Collections.emptyList());
    public Item islandWarp = new Item(MultiversionMaterials.YELLOW_STAINED_GLASS_PANE, 1, "&b&l{warp}", Arrays.asList("", "&b&l[!] &bLeft Click to Teleport to this warp.", "&b&l[!] &bRight Click to Delete to warp."));
    public Item topisland = new Item(MultiversionMaterials.PLAYER_HEAD, 1, "&b&l{player}", "{player}", Arrays.asList("&b&l * &7Island: &b{name}", "&b&l * &7Rank: &b{rank}", "&b&l * &7Value: &b{value}", "", "&b&l[!] &bLeft Click to Teleport to this island."));
    public Item visitisland = new Item(MultiversionMaterials.PLAYER_HEAD, 1, "&b&l{player}", "{player}", Arrays.asList("&b&l * &7Island: &b{name}", "&b&l * &7Rank: &b{rank}", "&b&l * &7Value: &b{value}", "&b&l * &7Votes: &b{votes}", "", "&b&l[!] &bLeft Click to Teleport to this island.", "&b&l[!] &bRight Click to (un)vote for this island."));


    public Item red = new Item(MultiversionMaterials.RED_STAINED_GLASS_PANE, 1, "&c&lRed", new ArrayList<>());
    public Item green = new Item(MultiversionMaterials.LIME_STAINED_GLASS_PANE, 1, "&a&lGreen", new ArrayList<>());
    public Item blue = new Item(MultiversionMaterials.BLUE_STAINED_GLASS_PANE, 1, "&b&lBlue", new ArrayList<>());
    public Item off = new Item(MultiversionMaterials.WHITE_STAINED_GLASS_PANE, 1, "&f&lOFF", new ArrayList<>());

    public List<Item> missionsItems = Arrays.asList(
            new Item(MultiversionMaterials.EXPERIENCE_BOTTLE, 10, 1, "&b&lTreasure Hunter Level {Treasure Hunterlevel}", Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bCollect {Treasure Hunteramount} Experience", "&b&l * &7Current Status: &b{Treasure Hunterstatus}", "&b&l * &7Reward: &b{Treasure Huntercrystals} Island Crystals and ${Treasure Huntervault}", "", "&b&l[!] &bComplete this mission for rewards.")),
            new Item(MultiversionMaterials.GOLD_INGOT, 11, 1, "&b&lCompetitor Level {Competitorlevel}", Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bGain {Competitoramount} Island Value", "&b&l * &7Current Status: &b{Competitorstatus}", "&b&l * &7Reward: &b{Competitorcrystals} Island Crystals and ${Competitorvault}", "", "&b&l[!] &bComplete this mission for rewards.")),
            new Item(MultiversionMaterials.DIAMOND_ORE, 12, 1, "&b&lMiner Level {Minerlevel}", Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bDestroy {Mineramount} Ores", "&b&l * &7Current Status: &b{Minerstatus}", "&b&l * &7Reward: &b{Minercrystals} Island Crystals and ${Minervault}", "", "&b&l[!] &bComplete this mission for rewards.")),
            new Item(MultiversionMaterials.SUGAR_CANE, 13, 1, "&b&lFarmer Level {Farmerlevel}", Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bHarvest {Farmeramount} Crops", "&b&l * &7Current Status: &b{Farmerstatus}", "&b&l * &7Reward: &b{Farmercrystals} Island Crystals and ${Farmervault}", "", "&b&l[!] &bComplete this mission for rewards.")),
            new Item(MultiversionMaterials.BLAZE_POWDER, 14, 1, "&b&lHunter Level {Hunterlevel}", Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bKill {Hunteramount} Mobs", "&b&l * &7Current Status: &b{Hunterstatus}", "&b&l * &7Reward: &b{Huntercrystals} Island Crystals and ${Huntervault}", "", "&b&l[!] &bComplete this mission for rewards.")),
            new Item(MultiversionMaterials.FISHING_ROD, 15, 1, "&b&lFisherman Level {Fishermanlevel}", Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bCatch {Fishermanamount} Fish", "&b&l * &7Current Status: &b{Fishermanstatus}", "&b&l * &7Reward: &b{Fishermancrystals} Island Crystals and ${Fishermanvault}", "", "&b&l[!] &bComplete this mission for rewards.")),
            new Item(MultiversionMaterials.COBBLESTONE, 16, 1, "&b&lBuilder Level {Builderlevel}", Arrays.asList("&7Complete island missions to gain crystals", "&7that can be spent on Boosters and Upgrades.", "", "&b&lInformation:", "&b&l * &7Objective: &bPlace {Builderamount} Blocks", "&b&l * &7Current Status: &b{Builderstatus}", "&b&l * &7Reward: &b{Buildercrystals} Island Crystals and ${Buildervault}", "", "&b&l[!] &bComplete this mission for rewards."))
    );

    public HashMap<Item, String> menu = new HashMap<Item, String>() {{
        put(new Item(MultiversionMaterials.DIAMOND, 0, 1, "&b&lIsland Top", Collections.singletonList("&7View top islands.")), "is top");
        put(new Item(MultiversionMaterials.WHITE_BED, 4, 1, "&b&lIsland Home", Collections.singletonList("&7Teleport to your island home")), "is home");
        put(new Item(MultiversionMaterials.END_PORTAL_FRAME, 11, 1, "&b&lIsland Warps", Collections.singletonList("&7View your island warps.")), "is warps");
        put(new Item(MultiversionMaterials.PLAYER_HEAD, 15, 1, "&b&lIsland Members", "Peaches_MLG", Collections.singletonList("&7View your island Members.")), "is members");
        put(new Item(MultiversionMaterials.PLAYER_HEAD, 19, 1, "&b&lIsland Upgrades", "ABigDwarf", Collections.singletonList("&7Upgrade your island.")), "is upgrade");
        put(new Item(MultiversionMaterials.BEACON, 22, 1, "&b&lIsland Border", Collections.singletonList("&7Change your island border.")), "is border");
        put(new Item(MultiversionMaterials.EXPERIENCE_BOTTLE, 25, 1, "&b&lIsland Boosters", Collections.singletonList("&7Boost your island.")), "is booster");
        put(new Item(MultiversionMaterials.GOLD_INGOT, 29, 1, "&b&lIsland Bank", Collections.singletonList("&7View your Island Bank.")), "is bank");
        put(new Item(MultiversionMaterials.NAME_TAG, 33, 1, "&b&lIsland Coop", Collections.singletonList("&7View your Co-op Islands.")), "is coop");
        put(new Item(MultiversionMaterials.BOOK, 40, 1, "&b&lIsland Flags & Permissions", Collections.singletonList("&7Change island global flags & permissions.")), "is flags");
        put(new Item(MultiversionMaterials.BARRIER, 44, 1, "&b&lIsland Delete", Collections.singletonList("&7Delete your island.")), "is delete");
    }};

    public HashMap<Item, String> flags = new HashMap<Item, String>() {{
        put(new Item(MultiversionMaterials.BLACK_BANNER, 12, 1, "&c&lGlobal Flags", Collections.singletonList("&bGlobal Flags")), "is globals");
        put(new Item(MultiversionMaterials.WHITE_BANNER, 14, 1, "&a&lIsland Permissions", Collections.singletonList("&bRole-based Island Permissions")), "is permissions");
    }};

    public HashMap<Item, String> globals = new HashMap<Item, String>() {{
        put(new Item(MultiversionMaterials.TNT, 12, 1, "&c&l", Collections.singletonList("&bGlobal Flags")), "is globals");
        put(new Item(MultiversionMaterials.FIRE_CHARGE, 13, 1, "&a&lIsland Permissions", Collections.singletonList("&bRole-based Island Permissions")), "is permissions");
        put(new Item(MultiversionMaterials.COW_SPAWN_EGG, 14, 1, "&a&lIsland Permissions", Collections.singletonList("&bRole-based Island Permissions")), "is permissions");
        put(new Item(MultiversionMaterials.ZOMBIE_SPAWN_EGG, 15, 1, "&c&lGlobal Flags", Collections.singletonList("&bGlobal Flags")), "is globals");
    }};

    public static class Item {

        public MultiversionMaterials material;
        public int amount;
        public String title;
        public String headOwner;
        public List<String> lore;
        public Integer slot;

        public Item(MultiversionMaterials material, int amount, String title, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
        }

        public Item(MultiversionMaterials material, int slot, int amount, String title, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
            this.slot = slot;
        }

        public Item(MultiversionMaterials material, int slot, int amount, String title, String headOwner, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
            this.headOwner = headOwner;
            this.slot = slot;
        }

        public Item(MultiversionMaterials material, int amount, String title, String headOwner, List<String> lore) {
            this.material = material;
            this.amount = amount;
            this.lore = lore;
            this.title = title;
            this.headOwner = headOwner;
        }
    }
}

package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.gui.Permission;

public class Permissions {

    @Permission(
            id = "block_break",
            name = "Break Blocks",
            icon = XMaterial.IRON_PICKAXE
    )
    public boolean breakBlocks;

    @Permission(
            id = "block_place",
            name = "Place Blocks",
            icon = XMaterial.COBBLESTONE
    )
    public boolean placeBlocks;

    @Permission(
            id = "interact",
            name = "Interact",
            icon = XMaterial.OAK_DOOR
    )
    public boolean interact;

    @Permission(
            id = "kick",
            name = "Kick Members",
            icon = XMaterial.PLAYER_HEAD
    )
    public boolean kickMembers;

    @Permission(
            id = "invite_players",
            name = "Invite Players",
            icon = XMaterial.WRITABLE_BOOK
    )
    public boolean inviteMembers;

    @Permission(
            id = "island_private",
            name = "Island Private",
            icon = XMaterial.IRON_BARS
    )
    public boolean islandprivate;

    @Permission(
            id = "promote",
            name = "Promote",
            icon = XMaterial.LADDER
    )
    public boolean promote;

    @Permission(
            id = "demote",
            name = "Demote",
            icon = XMaterial.OAK_TRAPDOOR
    )
    public boolean demote;

    @Permission(
            id = "use_nether",
            name = "Use Nether Portal",
            icon = XMaterial.OBSIDIAN
    )
    public boolean useNetherPortal;

    @Permission(
            id = "use_warps",
            name = "Use Warps",
            icon = XMaterial.END_PORTAL_FRAME
    )
    public boolean useWarps;

    @Permission(
            id = "coop",
            name = "Coop",
            icon = XMaterial.NAME_TAG
    )
    public boolean coop;

    @Permission(
            id = "withdraw_bank",
            name = "Withdraw Bank",
            icon = XMaterial.GOLD_INGOT
    )
    public boolean withdrawBank;

    @Permission(
            id = "deposit_bank",
            name = "Deposit Bank",
            icon = XMaterial.IRON_INGOT
    )
    public boolean depositBank;

    @Permission(
            id = "kill_mobs",
            name = "Kill Mobs",
            icon = XMaterial.IRON_SWORD
    )
    public boolean killMobs;

    @Permission(
            id = "pickup_items",
            name = "Pickup Items",
            icon = XMaterial.HOPPER
    )
    public boolean pickupItems;

    @Permission(
            id = "fly_command",
            name = "Fly",
            icon = XMaterial.FEATHER
    )
    public boolean fly;

    @Permission(
            id = "ban_members",
            name = "Ban Members",
            icon = XMaterial.IRON_AXE
    )
    public boolean ban;

    @Permission(
            id = "manage_flags",
            name = "Manage Flags",
            icon = XMaterial.BLACK_BANNER
    )
    public boolean manageFlags;

    @Permission(
            id = "use_beacon",
            name = "Use Beacon",
            icon = XMaterial.BEACON
    )
    public boolean useBeacon;

    @Permission(
            id = "break_valuables",
            name = "Break Valuables",
            icon = XMaterial.DIAMOND_PICKAXE
    )
    public boolean breakValuables;

    @Permission(
            id = "use_enderpearl",
            name = "Use Enderpearl",
            icon = XMaterial.ENDER_PEARL
    )
    public boolean useEnderpearl;

    @Permission(
            id = "fall_damage",
            name = "Fall Damage",
            icon = XMaterial.IRON_BOOTS
    )
    public boolean fallDamage;

    @Permission(
            id = "flint_and_steel",
            name = "Flint and Steel",
            icon = XMaterial.FLINT_AND_STEEL
    )
    public boolean flintAndSteel;

    @Permission(
            id = "fish",
            name = "Fish",
            icon = XMaterial.FISHING_ROD
    )
    public boolean fish;

    @Permission(
            id = "use_item_frames",
            name = "Use Item Frames",
            icon = XMaterial.ITEM_FRAME
    )
    public boolean useItemFrames;

    @Permission(
            id = "leash",
            name = "Use Leash",
            icon = XMaterial.LEAD
    )
    public boolean leash;

    @Permission(
            id = "milk",
            name = "Milk Cows",
            icon = XMaterial.MILK_BUCKET
    )
    public boolean milk;

    @Permission(
            id = "use_vehicles",
            name = "Use Vehicles",
            icon = XMaterial.OAK_BOAT
    )
    public boolean useVehicles;

    @Permission(
            id = "pvp",
            name = "PVP",
            icon = XMaterial.DIAMOND_SWORD
    )
    public boolean pvp;

    @Permission(
            id = "sethome",
            name = "Set Home",
            icon = XMaterial.RED_BED
    )
    public boolean sethome;

    @Permission(
            id = "spawn_eggs",
            name = "Spawn Eggs",
            icon = XMaterial.BAT_SPAWN_EGG
    )
    public boolean spawnEggs;

    @Permission(
            id = "throw_potions",
            name = "Throw Potions",
            icon = XMaterial.POTION
    )
    public boolean throwPotions;

    @Permission(
            id = "use_shears",
            name = "Use Shears",
            icon = XMaterial.SHEARS
    )
    public boolean useShears;

    @Permission(
            id = "use_lava_bucket",
            name = "Use Lava Bucket",
            icon = XMaterial.LAVA_BUCKET
    )
    public boolean useLavaBucket;

    @Permission(
            id = "use_water_bucket",
            name = "Use Water Bucket",
            icon = XMaterial.WATER_BUCKET
    )
    public boolean useWaterBucket;

    @Permission(
            id = "throw_chicken_egg",
            name = "Throw Chicken Eggs",
            icon = XMaterial.EGG
    )
    public boolean throwChickenEgg;

    @Permission(
            id = "use_anvil",
            name = "Use Anvil",
            icon = XMaterial.ANVIL
    )
    public boolean useAnvil;

    @Permission(
            id = "redstone",
            name = "Use Redstone",
            icon = XMaterial.REDSTONE
    )
    public boolean redstone;

    @Permission(
            id = "armor_stand",
            name = "Use Armor Stand",
            icon = XMaterial.ARMOR_STAND
    )
    public boolean useArmorStand;

    public Permissions(boolean breakBlocks,
                       boolean placeBlocks,
                       boolean interact,
                       boolean kickMembers,
                       boolean inviteMembers,
                       boolean islandprivate,
                       boolean promote,
                       boolean demote,
                       boolean useNetherPortal,
                       boolean useWarps,
                       boolean coop,
                       boolean withdrawBank,
                       boolean depositBank,
                       boolean killMobs,
                       boolean pickupItems,
                       boolean fly,
                       boolean ban,
                       boolean manageFlags,
                       boolean useBeacon,
                       boolean breakValuables,
                       boolean useEnderpearl,
                       boolean fallDamage,
                       boolean flintAndSteel,
                       boolean fish,
                       boolean useItemFrames,
                       boolean leash,
                       boolean milk,
                       boolean useVehicles,
                       boolean pvp,
                       boolean sethome,
                       boolean spawnEggs,
                       boolean throwPotions,
                       boolean useShears,
                       boolean useLavaBucket,
                       boolean useWaterBucket,
                       boolean throwChickenEgg,
                       boolean useAnvil,
                       boolean redstone,
                       boolean useArmorStand
    ) {
        this.breakBlocks = breakBlocks;
        this.placeBlocks = placeBlocks;
        this.interact = interact;
        this.kickMembers = kickMembers;
        this.inviteMembers = inviteMembers;
        this.islandprivate = islandprivate;
        this.promote = promote;
        this.demote = demote;
        this.useNetherPortal = useNetherPortal;
        this.useWarps = useWarps;
        this.coop = coop;
        this.withdrawBank = withdrawBank;
        this.depositBank = depositBank;
        this.killMobs = killMobs;
        this.pickupItems = pickupItems;
        this.fly = fly;
        this.ban = ban;
        this.manageFlags = manageFlags;
        this.useBeacon = useBeacon;
        this.breakValuables = breakValuables;
        this.useEnderpearl = useEnderpearl;
        this.fallDamage = fallDamage;
        this.flintAndSteel = flintAndSteel;
        this.fish = fish;
        this.useItemFrames = useItemFrames;
        this.leash = leash;
        this.milk = milk;
        this.useVehicles = useVehicles;
        this.pvp = pvp;
        this.sethome = sethome;
        this.spawnEggs = spawnEggs;
        this.throwPotions = throwPotions;
        this.useShears = useShears;
        this.useLavaBucket = useLavaBucket;
        this.useWaterBucket = useWaterBucket;
        this.throwChickenEgg = throwChickenEgg;
        this.useAnvil = useAnvil;
        this.redstone = redstone;
        this.useArmorStand = useArmorStand;
    }

    public Permissions() {
        breakBlocks = true;
        placeBlocks = true;
        interact = true;
        kickMembers = true;
        inviteMembers = true;
        islandprivate = true;
        promote = true;
        demote = true;
        useNetherPortal = true;
        useWarps = true;
        coop = true;
        withdrawBank = true;
        depositBank = true;
        killMobs = true;
        pickupItems = true;
        fly = true;
        ban = true;
        manageFlags = true;
        useBeacon = true;
        breakValuables = true;
        useEnderpearl = true;
        fallDamage = true;
        flintAndSteel = true;
        fish = true;
        useItemFrames = true;
        leash = true;
        milk = true;
        useVehicles = true;
        pvp = true;
        sethome = true;
        spawnEggs = true;
        throwPotions = true;
        useShears = true;
        useLavaBucket = true;
        useWaterBucket = true;
        throwChickenEgg = true;
        useAnvil = true;
        redstone = true;
        useArmorStand = true;
    }
}
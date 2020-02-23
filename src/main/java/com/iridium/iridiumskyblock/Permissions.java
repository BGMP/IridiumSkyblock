package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.gui.Permission;

public class Permissions {

    @Permission(
            id = "block_break",
            name = "Break Blocks",
            icon = MultiversionMaterials.IRON_PICKAXE
    )
    public boolean breakBlocks;

    @Permission(
            id = "block_place",
            name = "Place Blocks",
            icon = MultiversionMaterials.COBBLESTONE
    )
    public boolean placeBlocks;

    @Permission(
            id = "interact",
            name = "Interact",
            icon = MultiversionMaterials.OAK_DOOR
    )
    public boolean interact;

    @Permission(
            id = "kick",
            name = "Kick Members",
            icon = MultiversionMaterials.PLAYER_HEAD
    )
    public boolean kickMembers;

    @Permission(
            id = "invite_players",
            name = "Invite Players",
            icon = MultiversionMaterials.WRITABLE_BOOK
    )
    public boolean inviteMembers;

    @Permission(
            id = "island_private",
            name = "Island Private",
            icon = MultiversionMaterials.IRON_BARS
    )
    public boolean islandprivate;

    @Permission(
            id = "promote",
            name = "Promote",
            icon = MultiversionMaterials.LADDER
    )
    public boolean promote;

    @Permission(
            id = "demote",
            name = "Demote",
            icon = MultiversionMaterials.OAK_TRAPDOOR
    )
    public boolean demote;

    @Permission(
            id = "use_nether",
            name = "Use Nether Portal",
            icon = MultiversionMaterials.OBSIDIAN
    )
    public boolean useNetherPortal;

    @Permission(
            id = "use_warps",
            name = "Use Warps",
            icon = MultiversionMaterials.END_PORTAL_FRAME
    )
    public boolean useWarps;

    @Permission(
            id = "coop",
            name = "Coop",
            icon = MultiversionMaterials.NAME_TAG
    )
    public boolean coop;

    @Permission(
            id = "withdraw_bank",
            name = "Withdraw Bank",
            icon = MultiversionMaterials.GOLD_INGOT
    )
    public boolean withdrawBank;

    @Permission(
            id = "kill_mobs",
            name = "Kill Mobs",
            icon = MultiversionMaterials.IRON_SWORD
    )
    public boolean killMobs;

    @Permission(
            id = "pickup_items",
            name = "Pickup Items",
            icon = MultiversionMaterials.HOPPER
    )
    public boolean pickupItems;

    @Permission(
            id = "fly_command",
            name = "Fly",
            icon = MultiversionMaterials.FEATHER
    )
    public boolean fly;

    @Permission(
            id = "ban_members",
            name = "Ban Members",
            icon = MultiversionMaterials.IRON_AXE
    )
    public boolean ban;

    @Permission(
            id = "manage_flags",
            name = "Manage Flags",
            icon = MultiversionMaterials.BLACK_BANNER
    )
    public boolean manageFlags;

    @Permission(
            id = "use_beacon",
            name = "Use Beacon",
            icon = MultiversionMaterials.BEACON
    )
    public boolean useBeacon;

    @Permission(
            id = "break_valuables",
            name = "Break Valuables",
            icon = MultiversionMaterials.DIAMOND_PICKAXE
    )
    public boolean breakValuables;

    @Permission(
            id = "use_enderpearl",
            name = "Use Enderpearl",
            icon = MultiversionMaterials.ENDER_PEARL
    )
    public boolean useEnderpearl;

    @Permission(
            id = "fall_damage",
            name = "Fall Damage",
            icon = MultiversionMaterials.IRON_BOOTS
    )
    public boolean fallDamage;

    @Permission(
            id = "flint_and_steel",
            name = "Flint and Steel",
            icon = MultiversionMaterials.FLINT_AND_STEEL
    )
    public boolean flintAndSteel;

    @Permission(
            id = "fish",
            name = "Fish",
            icon = MultiversionMaterials.FISHING_ROD
    )
    public boolean fish;

    @Permission(
            id = "use_item_frames",
            name = "Use Item Frames",
            icon = MultiversionMaterials.ITEM_FRAME
    )
    public boolean useItemFrames;

    @Permission(
            id = "leash",
            name = "Use Leash",
            icon = MultiversionMaterials.LEAD
    )
    public boolean leash;

    @Permission(
            id = "milk",
            name = "Milk Cows",
            icon = MultiversionMaterials.MILK_BUCKET
    )
    public boolean milk;

    @Permission(
            id = "use_vehicles",
            name = "Use Vehicles",
            icon = MultiversionMaterials.OAK_BOAT
    )
    public boolean useVehicles;

    @Permission(
            id = "pvp",
            name = "PVP",
            icon = MultiversionMaterials.DIAMOND_SWORD
    )
    public boolean pvp;

    @Permission(
            id = "sethome",
            name = "Set Home",
            icon = MultiversionMaterials.RED_BED
    )
    public boolean sethome;

    @Permission(
            id = "spawn_eggs",
            name = "Spawn Eggs",
            icon = MultiversionMaterials.BAT_SPAWN_EGG
    )
    public boolean spawnEggs;

    @Permission(
            id = "throw_potions",
            name = "Throw Potions",
            icon = MultiversionMaterials.POTION
    )
    public boolean throwPotions;

    @Permission(
            id = "use_shears",
            name = "Use Shears",
            icon = MultiversionMaterials.SHEARS
    )
    public boolean useShears;

    @Permission(
            id = "use_lava_bucket",
            name = "Use Lava Bucket",
            icon = MultiversionMaterials.LAVA_BUCKET
    )
    public boolean useLavaBucket;

    @Permission(
            id = "use_water_bucket",
            name = "Use Water Bucket",
            icon = MultiversionMaterials.WATER_BUCKET
    )
    public boolean useWaterBucket;

    @Permission(
            id = "throw_chicken_egg",
            name = "Throw Chicken Eggs",
            icon = MultiversionMaterials.EGG
    )
    public boolean throwChickenEgg;

    @Permission(
            id = "use_anvil",
            name = "Use Anvil",
            icon = MultiversionMaterials.ANVIL
    )
    public boolean useAnvil;

    @Permission(
            id = "redstone",
            name = "Use Redstone",
            icon = MultiversionMaterials.ANVIL
    )
    public boolean redstone;

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
                       boolean redstone
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
        killMobs = true;
        pickupItems = true;
        fly = true;
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
    }
}
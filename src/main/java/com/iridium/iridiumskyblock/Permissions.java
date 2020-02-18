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
            id = "regen",
            name = "Regenerate",
            icon = MultiversionMaterials.GOLDEN_APPLE
    )
    public boolean regen;

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
            icon = MultiversionMaterials.ROTTEN_FLESH
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

    public Permissions(boolean breakBlocks,
                       boolean placeBlocks,
                       boolean interact,
                       boolean kickMembers,
                       boolean inviteMembers,
                       boolean regen,
                       boolean islandprivate,
                       boolean promote,
                       boolean demote,
                       boolean useNetherPortal,
                       boolean useWarps,
                       boolean coop,
                       boolean withdrawBank,
                       boolean killMobs,
                       boolean pickupItems,
                       boolean fly
    ) {
        this.breakBlocks = breakBlocks;
        this.placeBlocks = placeBlocks;
        this.interact = interact;
        this.kickMembers = kickMembers;
        this.inviteMembers = inviteMembers;
        this.regen = regen;
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
    }

    public Permissions() {
        breakBlocks = true;
        placeBlocks = true;
        interact = true;
        kickMembers = true;
        inviteMembers = true;
        regen = true;
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
    }
}
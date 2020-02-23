package com.iridium.iridiumskyblock.configs;

import com.iridium.iridiumskyblock.GlobalPermissions;
import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.MissionRestart;
import com.iridium.iridiumskyblock.MultiversionMaterials;
import com.iridium.iridiumskyblock.Permissions;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.gui.GlobalPermission;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {

    public Config() {
        try {
            netherBiome = Biome.HELL;
        } catch (NoSuchFieldError e) {
            netherBiome = Biome.valueOf("NETHER");
        }
    }

    public String prefix = "&6&lCores&c&lMC &8»";
    public String worldName = "SkyBlock";
    public String chatRankPlaceholder = "[ISLAND_RANK]";
    public String chatValuePlaceholder = "[ISLAND_VALUE]";
    public String chatNAMEPlaceholder = "[ISLAND_NAME]";
    public boolean createCooldown = true;
    public boolean doIslandBackup = true;
    public boolean islandShop = true;
    public boolean automaticUpdate = true;
    public boolean defaultIslandPublic = true;
    public boolean netherIslands = true;
    public boolean islandMenu = true;
    public boolean voidTeleport = true;
    public boolean notifyAvailableUpdate = true;
    public boolean disableExplosions = true;
    public boolean clearInventories = false;
    public boolean debugSchematics = false;
    public boolean restartUpgradesOnRegen = true;
    public boolean allowWaterInNether = true;
    public int deleteBackupsAfterDays = 7;
    public int regenCooldown = 3600;
    public int distance = 500;
    public int backupIntervalMinutes = 60;
    public int blocksPerTick = 25;
    public int islandsUpdateInterval = 5;
    public double valuePerLevel = 100.00;
    public double dailyMoneyInterest = 0.5;
    public double dailyExpInterest = 0.01;
    public Biome defaultBiome = Biome.PLAINS;
    public Biome netherBiome;
    public MissionRestart missionRestart = MissionRestart.Daily;
    public HashMap<Role, Permissions> defaultPermissions = new HashMap<Role, Permissions>() {{
        for (Role role : Role.values()) {
            if (role == Role.Visitor) {
                put(role, new Permissions
                        (
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false
                        )
                );
            } else {
                put(role, new Permissions());
            }
        }
    }};

    public GlobalPermissions defaultGlobals = new GlobalPermissions
            (
            false,
            true,
            true,
            true,
            true
            );

    public HashMap<Integer, Integer> islandTopSlots = new HashMap<Integer, Integer>() {{
        put(1, 4);
        put(2, 12);
        put(3, 14);
        put(4, 19);
        put(5, 20);
        put(6, 21);
        put(7, 22);
        put(8, 23);
        put(9, 24);
        put(10, 25);
    }};

    public HashMap<MultiversionMaterials, Integer> blockvalue = null;
    public HashMap<String, Integer> spawnervalue = null;
}

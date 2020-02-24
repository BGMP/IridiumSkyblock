package com.iridium.iridiumskyblock.configs;

import com.iridium.iridiumskyblock.GlobalPermissions;
import com.iridium.iridiumskyblock.MissionRestart;
import com.iridium.iridiumskyblock.Permissions;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Config {
    public String prefix = "&6&lCores&c&lMC &8»";
    public String worldName = "SkyBlock";
    public String chatRankPlaceholder = "[ISLAND_RANK]";
    public String chatValuePlaceholder = "[ISLAND_VALUE]";
    public String chatNAMEPlaceholder = "[ISLAND_NAME]";
    public boolean createCooldown = true;
    public boolean doIslandBackup = true;
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
    public int blocksPerTick = 25;
    public int deleteBackupsAfterDays = 7;
    public int regenCooldown = 3600;
    public int distance = 1500;
    public int backupIntervalMinutes = 60;
    public double valuePerLevel = 100.00;
    public double dailyMoneyInterest = 0.5;
    public double dailyExpInterest = 0.01;
    public XBiome defaultBiome = XBiome.PLAINS;
    public XBiome defaultNetherBiome = XBiome.NETHER;
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

    public HashMap<XMaterial, Double> blockvalue = null;
    public HashMap<String, Double> spawnervalue = null;
    public List<XBiome> biomes = Arrays.asList(XBiome.values());
}

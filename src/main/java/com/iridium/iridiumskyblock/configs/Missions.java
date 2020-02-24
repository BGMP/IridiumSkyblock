package com.iridium.iridiumskyblock.configs;

import com.iridium.iridiumskyblock.MissionType;
import com.iridium.iridiumskyblock.XMaterial;
import org.bukkit.CropState;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Missions {
    public HashMap<String, HashMap<Integer, Mission>> mission = new HashMap<String, HashMap<Integer, Mission>>() {{
        put("Treasure Hunter", new HashMap<Integer, Mission>() {{
            put(1, new Mission(5, 1000, 100, MissionType.EXPERIENCE, Collections.emptyList()));
            put(2, new Mission(10, 5000, 1000, MissionType.EXPERIENCE, Collections.emptyList()));
            put(3, new Mission(15, 10000, 10000, MissionType.EXPERIENCE, Collections.emptyList()));
        }});
        put("Competitor", new HashMap<Integer, Mission>() {{
            put(1, new Mission(5, 10000, 100, MissionType.VALUE_INCREASE, Collections.emptyList()));
            put(2, new Mission(10, 10000, 500, MissionType.VALUE_INCREASE, Collections.emptyList()));
            put(3, new Mission(15, 10000, 1000, MissionType.VALUE_INCREASE, Collections.emptyList()));
            put(4, new Mission(15, 10000, 5000, MissionType.VALUE_INCREASE, Collections.emptyList()));
            put(5, new Mission(15, 10000, 10000, MissionType.VALUE_INCREASE, Collections.emptyList()));
        }});
        put("Miner", new HashMap<Integer, Mission>() {{
            put(1, new Mission(1, 10000, 50, MissionType.BLOCK_BREAK, Arrays.asList(XMaterial.COAL_ORE.toString(), XMaterial.DIAMOND_ORE.toString(), XMaterial.EMERALD_ORE.toString(), XMaterial.GOLD_ORE.toString(), XMaterial.IRON_ORE.toString(), XMaterial.LAPIS_ORE.toString(), XMaterial.NETHER_QUARTZ_ORE.toString(), XMaterial.REDSTONE_ORE.toString())));
            put(2, new Mission(5, 10000, 100, MissionType.BLOCK_BREAK, Arrays.asList(XMaterial.COAL_ORE.toString(), XMaterial.DIAMOND_ORE.toString(), XMaterial.EMERALD_ORE.toString(), XMaterial.GOLD_ORE.toString(), XMaterial.IRON_ORE.toString(), XMaterial.LAPIS_ORE.toString(), XMaterial.NETHER_QUARTZ_ORE.toString(), XMaterial.REDSTONE_ORE.toString())));
            put(3, new Mission(10, 10000, 500, MissionType.BLOCK_BREAK, Arrays.asList(XMaterial.COAL_ORE.toString(), XMaterial.DIAMOND_ORE.toString(), XMaterial.EMERALD_ORE.toString(), XMaterial.GOLD_ORE.toString(), XMaterial.IRON_ORE.toString(), XMaterial.LAPIS_ORE.toString(), XMaterial.NETHER_QUARTZ_ORE.toString(), XMaterial.REDSTONE_ORE.toString())));
            put(4, new Mission(15, 10000, 1000, MissionType.BLOCK_BREAK, Arrays.asList(XMaterial.COAL_ORE.toString(), XMaterial.DIAMOND_ORE.toString(), XMaterial.EMERALD_ORE.toString(), XMaterial.GOLD_ORE.toString(), XMaterial.IRON_ORE.toString(), XMaterial.LAPIS_ORE.toString(), XMaterial.NETHER_QUARTZ_ORE.toString(), XMaterial.REDSTONE_ORE.toString())));
            put(5, new Mission(15, 10000, 2000, MissionType.BLOCK_BREAK, Arrays.asList(XMaterial.COAL_ORE.toString(), XMaterial.DIAMOND_ORE.toString(), XMaterial.EMERALD_ORE.toString(), XMaterial.GOLD_ORE.toString(), XMaterial.IRON_ORE.toString(), XMaterial.LAPIS_ORE.toString(), XMaterial.NETHER_QUARTZ_ORE.toString(), XMaterial.REDSTONE_ORE.toString())));
        }});
        put("Farmer", new HashMap<Integer, Mission>() {{
            put(1, new Mission(1, 10000, 50, MissionType.BLOCK_BREAK, Collections.singletonList(CropState.RIPE.toString())));
            put(2, new Mission(5, 10000, 100, MissionType.BLOCK_BREAK, Collections.singletonList(CropState.RIPE.toString())));
            put(3, new Mission(10, 10000, 500, MissionType.BLOCK_BREAK, Collections.singletonList(CropState.RIPE.toString())));
            put(4, new Mission(15, 10000, 1000, MissionType.BLOCK_BREAK, Collections.singletonList(CropState.RIPE.toString())));
            put(5, new Mission(15, 10000, 5000, MissionType.BLOCK_BREAK, Collections.singletonList(CropState.RIPE.toString())));
        }});
        put("Hunter", new HashMap<Integer, Mission>() {{
            put(1, new Mission(1, 10000, 10, MissionType.ENTITY_KILL, Collections.emptyList()));
            put(2, new Mission(5, 10000, 50, MissionType.ENTITY_KILL, Collections.emptyList()));
            put(3, new Mission(10, 10000, 100, MissionType.ENTITY_KILL, Collections.emptyList()));
            put(4, new Mission(15, 10000, 500, MissionType.ENTITY_KILL, Collections.emptyList()));
            put(5, new Mission(15, 10000, 1000, MissionType.ENTITY_KILL, Collections.emptyList()));
        }});
        put("Fisherman", new HashMap<Integer, Mission>() {{
            put(1, new Mission(1, 10000, 5, MissionType.FISH_CATCH, Collections.emptyList()));
            put(2, new Mission(5, 10000, 10, MissionType.FISH_CATCH, Collections.emptyList()));
            put(3, new Mission(10, 10000, 50, MissionType.FISH_CATCH, Collections.emptyList()));
            put(4, new Mission(15, 10000, 100, MissionType.FISH_CATCH, Collections.emptyList()));
            put(5, new Mission(15, 10000, 500, MissionType.FISH_CATCH, Collections.emptyList()));
        }});
        put("Builder", new HashMap<Integer, Mission>() {{
            put(1, new Mission(1, 10000, 100, MissionType.BLOCK_PLACE, Collections.emptyList()));
            put(2, new Mission(5, 10000, 500, MissionType.BLOCK_PLACE, Collections.emptyList()));
            put(3, new Mission(10, 10000, 1000, MissionType.BLOCK_PLACE, Collections.emptyList()));
            put(4, new Mission(15, 10000, 5000, MissionType.BLOCK_PLACE, Collections.emptyList()));
            put(5, new Mission(15, 10000, 10000, MissionType.BLOCK_PLACE, Collections.emptyList()));
            put(6, new Mission(15, 10000, 50000, MissionType.BLOCK_PLACE, Collections.emptyList()));
        }});
    }};

    public static class Mission {
        public int crystalReward;
        public int vaultReward;
        public int amount;
        public MissionType type;
        public List<String> conditions;

        public Mission(int crystalReward, int vaultReward, int amount, MissionType type, List<String> conditions) {
            this.crystalReward = crystalReward;
            this.vaultReward = vaultReward;
            this.amount = amount;
            this.type = type;
            this.conditions = conditions;
        }
    }
}

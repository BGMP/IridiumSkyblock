package com.iridium.iridiumskyblock.configs;

import java.util.HashMap;
import java.util.List;

public class Upgrades {

    public Upgrade sizeUpgrade = new Upgrade(true, 13, new HashMap<Integer, IslandUpgrade>() {{
        put(1, new IslandUpgrade(15, 0, 50));
        put(2, new IslandUpgrade(15, 0, 100));
        put(3, new IslandUpgrade(15, 0, 150));
        put(4, new IslandUpgrade(15, 0, 250));
        put(5, new IslandUpgrade(15, 0, 350));
        put(6, new IslandUpgrade(15, 0, 600));
    }});

    public static class Upgrade {
        public boolean enabled;
        public int slot;
        public HashMap<Integer, IslandUpgrade> upgrades;

        public Upgrade(boolean enabled, int slot, HashMap<Integer, IslandUpgrade> upgrades) {
            this.enabled = enabled;
            this.slot = slot;
            this.upgrades = upgrades;
        }
    }

    public static class IslandUpgrade {
        public int crystalsCost;
        public int vaultCost;
        public int size;
        public List<String> ores;
        public List<String> netherores;

        public IslandUpgrade(int crystalsCost, int vaultCost, List<String> ores, List<String> netherores) {
            this.crystalsCost = crystalsCost;
            this.vaultCost = vaultCost;
            this.ores = ores;
            this.netherores = netherores;
        }

        public IslandUpgrade(int crystalsCost, int vaultCost, int size) {
            this.crystalsCost = crystalsCost;
            this.vaultCost = vaultCost;
            this.size = size;
        }
    }
}

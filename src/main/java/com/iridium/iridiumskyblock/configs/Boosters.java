package com.iridium.iridiumskyblock.configs;

public class Boosters {

    public Booster spawnerBooster = new Booster(0, 3600, true, 13);

    public static class Booster {
        public int vaultCost;
        public int time;
        public boolean enabled;
        public int slot;

        public Booster(int vaultCost, int time, boolean enabled, int slot) {
            this.vaultCost = vaultCost;
            this.time = time;
            this.enabled = enabled;
            this.slot = slot;
        }
    }
}

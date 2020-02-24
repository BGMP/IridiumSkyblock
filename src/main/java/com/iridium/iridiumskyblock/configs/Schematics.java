package com.iridium.iridiumskyblock.configs;

import com.iridium.iridiumskyblock.XMaterial;

import java.util.Collections;
import java.util.List;

public class Schematics {
    public List<FakeSchematic> schematics = Collections.singletonList(new FakeSchematic("island.schematic", "nether.schematic", -0.5, 96.00, -2.5, "", XMaterial.GRASS_BLOCK, "&b&lDefault Island", Collections.singletonList("&7The default island"), 0));

    public static class FakeSchematic {
        public String name;
        public double x;
        public double y;
        public double z;
        public String netherisland;
        public String permission;
        public XMaterial item;
        public String displayname;
        public List<String> lore;
        public Integer slot;

        public FakeSchematic(String name, String netherisland, double x, double y, double z, String permission, XMaterial item, String displayname, List<String> lore, int slot) {
            this.name = name;
            this.netherisland = netherisland;
            this.permission = permission;
            this.item = item;
            this.displayname = displayname;
            this.lore = lore;
            this.x = x;
            this.y = y;
            this.z = z;
            this.slot = slot;
        }
    }
}

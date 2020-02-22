package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.gui.GlobalPermission;

public class GlobalPermissions {

    @GlobalPermission(
            id = "explosion_damage",
            name = "Explosion Damage",
            icon = MultiversionMaterials.TNT
    )
    public boolean explosionDamage;

    @GlobalPermission(
            id = "explosion_damage",
            name = "Fire Spread",
            icon = MultiversionMaterials.FIRE_CHARGE
    )
    public boolean fireSpread;

    @GlobalPermission(
            id = "animal_spawning",
            name = "Animal Spawning",
            icon = MultiversionMaterials.CARROT_ON_A_STICK
    )
    public boolean animalSpawning;

    @GlobalPermission(
            id = "monster_spawning",
            name = "Monster Spawning",
            icon = MultiversionMaterials.BLAZE_ROD
    )
    public boolean mobSpawning;

    public GlobalPermissions(boolean explosionDamage, boolean fireSpread, boolean animalSpawning, boolean mobSpawning) {
        this.explosionDamage = explosionDamage;
        this.fireSpread = fireSpread;
        this.animalSpawning = animalSpawning;
        this.mobSpawning = mobSpawning;
    }

    public GlobalPermissions() {
        explosionDamage = false;
        fireSpread = false;
        animalSpawning = true;
        mobSpawning = true;
    }
}

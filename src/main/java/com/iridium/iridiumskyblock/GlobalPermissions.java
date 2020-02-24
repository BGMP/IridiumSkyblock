package com.iridium.iridiumskyblock;

import com.iridium.iridiumskyblock.gui.GlobalPermission;

public class GlobalPermissions {

    @GlobalPermission(
            id = "explosion_damage",
            name = "Explosion Damage",
            icon = XMaterial.TNT
    )
    public boolean explosionDamage;

    @GlobalPermission(
            id = "explosion_damage",
            name = "Fire Spread",
            icon = XMaterial.FIRE_CHARGE
    )
    public boolean fireSpread;

    @GlobalPermission(
            id = "animal_spawning",
            name = "Animal Spawning",
            icon = XMaterial.CARROT_ON_A_STICK
    )
    public boolean animalSpawning;

    @GlobalPermission(
            id = "monster_spawning",
            name = "Monster Spawning",
            icon = XMaterial.BLAZE_ROD
    )
    public boolean mobSpawning;

    @GlobalPermission(
            id = "breed_animals",
            name = "Breed Animals",
            icon = XMaterial.WHEAT
    )
    public boolean breedAnimals;


    public GlobalPermissions(
            boolean explosionDamage,
            boolean fireSpread,
            boolean animalSpawning,
            boolean mobSpawning,
            boolean breedAnimals
    ) {
        this.explosionDamage = explosionDamage;
        this.fireSpread = fireSpread;
        this.animalSpawning = animalSpawning;
        this.mobSpawning = mobSpawning;
        this.breedAnimals = breedAnimals;
    }

    public GlobalPermissions() {
        explosionDamage = false;
        fireSpread = false;
        animalSpawning = true;
        mobSpawning = true;
        breedAnimals = true;
    }
}

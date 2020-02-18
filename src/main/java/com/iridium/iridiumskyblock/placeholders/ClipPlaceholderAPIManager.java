package com.iridium.iridiumskyblock.placeholders;


import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClipPlaceholderAPIManager extends PlaceholderExpansion {

    // Identifier for this expansion
    @Override
    public String getIdentifier() {
        return "iridiumskyblock";
    }

    @Override
    public String getAuthor() {
        return "Peaches_MLG";
    }

    // Since we are registering this expansion from the dependency, this can be null
    @Override
    public String getPlugin() {
        return null;
    }

    // Return the plugin version since this expansion is bundled with the dependency
    @Override
    public String getVersion() {
        return IridiumSkyblock.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String placeholder) {
        if (player == null || placeholder == null) {
            return "";
        }

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long time = (c.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        int day = (int) TimeUnit.SECONDS.toDays(time);
        int hours = (int) Math.floor(TimeUnit.SECONDS.toHours(time - day * 86400));
        int minute = (int) Math.floor((time - day * 86400 - hours * 3600) / 60.00);
        int second = (int) Math.floor((time - day * 86400 - hours * 3600) % 60.00);

        User user = User.getUser(player);

        switch (placeholder) {
            case "island_value":
                return user.getIsland() != null ? NumberFormat.getInstance().format(user.getIsland().getValue()) + "" : "N/A";
            case "island_level":
                return user.getIsland() != null ? NumberFormat.getInstance().format(Math.floor(user.getIsland().getValue() / IridiumSkyblock.getConfiguration().valuePerLevel)) + "" : "N/A";
            case "island_rank":
                return user.getIsland() != null ? NumberFormat.getInstance().format(Utils.getIslandRank(user.getIsland())) + "" : "N/A";
            case "island_owner":
                return user.getIsland() != null ? User.getUser(user.getIsland().getOwner()).name : "N/A";
            case "island_name":
                return user.getIsland() != null ? user.getIsland().getName() : "N/A";
            case "island_crystals":
                return user.getIsland() != null ? NumberFormat.getInstance().format(user.getIsland().getCrystals()) + "" : "N/A";
            case "island_members":
                return user.getIsland() != null ? user.getIsland().getMembers().size() + "" : "N/A";
            case "island_upgrade_member_level":
                return user.getIsland() != null ? NumberFormat.getInstance().format(user.getIsland().getMemberLevel()) + "" : "N/A";
            case "island_upgrade_size_level":
                return user.getIsland() != null ? user.getIsland().getSizeLevel() + "" : "N/A";
            case "island_upgrade_ore_level":
                return user.getIsland() != null ? user.getIsland().getOreLevel() + "" : "N/A";
            case "island_upgrade_warp_level":
                return user.getIsland() != null ? user.getIsland().getWarpLevel() + "" : "N/A";
            case "island_booster_spawner":
                return user.getIsland() != null ? user.getIsland().getSpawnerBooster() + "" : "N/A";
            case "island_booster_exp":
                return user.getIsland() != null ? user.getIsland().getExpBooster() + "" : "N/A";
            case "island_booster_farming":
                return user.getIsland() != null ? user.getIsland().getFarmingBooster() + "" : "N/A";
            case "island_booster_flight":
                return user.getIsland() != null ? user.getIsland().getFlightBooster() + "" : "N/A";
            case "island_bank_vault":
                return user.getIsland() != null ? user.getIsland().money + "" : "N/A";
            case "island_bank_experience":
                return user.getIsland() != null ? user.getIsland().exp + "" : "N/A";
            case "island_biome":
                return user.getIsland() != null ? user.getIsland().getBiome().name() + "" : "N/A";
            case "midnight_seconds":
                return second + "";
            case "midnight_minutes":
                return minute + "";
            case "midnight_hours":
                return hours + "";
        }
        if (placeholder.startsWith("island_top_name_")) {
            try {
                Integer integer = Integer.parseInt(placeholder.replace("island_top_name_", ""));
                List<Island> islands = Utils.getTopIslands();
                return islands.size() > integer - 1 ? User.getUser(Utils.getTopIslands().get(integer - 1).getOwner()).name : "N/A";
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        if (placeholder.startsWith("island_top_value_")) {
            try {
                int integer = Integer.parseInt(placeholder.replace("island_top_value_", ""));
                List<Island> islands = Utils.getTopIslands();
                return islands.size() > integer - 1 ? NumberFormat.getInstance().format(Utils.getTopIslands().get(integer - 1).getValue()) + "" : "N/A";
            } catch (NumberFormatException ignored) {

            }
        }
        if (placeholder.startsWith("island_top_level_")) {
            try {
                int integer = Integer.parseInt(placeholder.replace("island_top_level_", ""));
                List<Island> islands = Utils.getTopIslands();
                return islands.size() > integer - 1 ? NumberFormat.getInstance().format(Math.floor(Utils.getTopIslands().get(integer - 1).getValue() / IridiumSkyblock.getConfiguration().valuePerLevel)) + "" : "N/A";
            } catch (NumberFormatException ignored) {

            }
        }
        return null;
    }
}
package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class BanCommand extends Command {

    public BanCommand() {
        super(Collections.singletonList("ban"), "Ban a player from visiting your island", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(Utils.color(IridiumSkyblock.getConfiguration().prefix) + "/is ban <player>");
            sender.sendMessage("/is ban <player>");
            return;
        }
        Player p = (Player) sender;
        User user = User.getUser(p);

        if (user.getIsland() != null) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            if (player != null) {
                Island island = user.getIsland();
                if (!island.equals(User.getUser(player).getIsland())) {
                    if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).ban && !user.bypassing) {
                        p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantBan.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        return;
                    }
                    user.getIsland().addBan(User.getUser(player));
                    sender.sendMessage(Utils.color(IridiumSkyblock.getMessages().playerBanned.replace("%player%", player.getName()).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    if (player.getPlayer() != null) {
                        if (user.getIsland().isInIsland(player.getPlayer().getLocation())) {
                            player.getPlayer().sendMessage(Utils.color(IridiumSkyblock.getMessages().bannedFromIsland.replace("%player%", player.getName()).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                            user.getIsland().spawnPlayer(player.getPlayer());
                        }
                    }
                }
            } else {
                sender.sendMessage(Utils.color(IridiumSkyblock.getMessages().playerOffline.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        } else {
            sender.sendMessage(Utils.color(IridiumSkyblock.getMessages().noIsland.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}

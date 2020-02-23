package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SetNameCommand extends Command {

    public SetNameCommand() {
        super(Collections.singletonList("setname"), "Set your islands name", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if (args.length != 2) {
            sender.sendMessage(Utils.color(IridiumSkyblock.getConfiguration().prefix) + "/is setname <Island Name>");
            return;
        }
        if (user.getIsland() != null) {
            if (user.role.equals(Role.Owner)) {
                if (user.bypassing) {
                    user.getIsland().setName(args[1]);
                    for (String member : user.getIsland().getMembers()) {
                        Player player = Bukkit.getPlayer(User.getUser(member).name);
                        if (player != null) {
                            player.sendMessage(Utils.color(IridiumSkyblock.getMessages().changesIslandName.replace("%player%", p.getName()).replace("%name%", args[1]).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        }
                    }
                } else {
                    sender.sendMessage(Utils.color(IridiumSkyblock.getMessages().noPermission.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            } else {
                sender.sendMessage(Utils.color(IridiumSkyblock.getMessages().mustBeIslandOwner.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
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

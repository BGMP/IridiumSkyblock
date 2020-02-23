package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class SetHomeCommand extends Command {

    public SetHomeCommand() {
        super(Collections.singletonList("sethome"), "Set your island home", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if (user.getIsland() != null) {
            if (Utils.isSafe(p.getLocation(), user.getIsland()) && p.getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld())) {
                Island island = user.getIsland();
                if (!island.equals(User.getUser(p).getIsland())) {
                    if (island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).sethome && !user.bypassing) {
                        user.getIsland().setHome(p.getLocation());
                        p.sendMessage(Utils.color(IridiumSkyblock.getMessages().setHome.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    } else {
                        p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseFlags.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    }
                }
            } else {
                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().isNotSafe.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        } else {
            p.sendMessage(Utils.color(IridiumSkyblock.getMessages().noIsland.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}

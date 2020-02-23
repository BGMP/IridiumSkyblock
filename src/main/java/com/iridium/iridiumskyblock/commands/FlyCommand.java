package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class FlyCommand extends Command {

    public FlyCommand() {
        super(Arrays.asList("fly", "flight"), "Toggle your ability to fly", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        User user = User.getUser(p);
        if (user.getIsland() != null) {
            if (user.getIsland().isInIsland(p.getLocation())) {
                if (p.hasPermission("iridiumskyblock.Fly")) {
                    Island island = IridiumSkyblock.getIslandManager().getIslandViaLocation(p.getLocation());

                    if (island != null) {
                        if (user.islandID != island.getId()) {
                            if ((!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).fly) && !user.bypassing) {
                                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantFly.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                                return;
                            }
                        }
                    }

                    if (p.getAllowFlight()) {
                        p.setAllowFlight(false);
                        p.setFlying(false);
                        p.sendMessage(Utils.color(IridiumSkyblock.getMessages().flightDisabled.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    } else {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        p.sendMessage(Utils.color(IridiumSkyblock.getMessages().flightEnabled.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    }
                    user.flying = p.isFlying();
                } else {
                    p.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantFly.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            }else{
                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().mustBeInIsland.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
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

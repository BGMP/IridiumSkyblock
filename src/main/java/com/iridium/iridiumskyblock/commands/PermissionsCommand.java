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

public class PermissionsCommand extends Command {

    public PermissionsCommand() {
        super(Collections.singletonList("permissions"), "Edit Island Permissions", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        User user = User.getUser(player);
        Island island = user.getIsland();

        if (island != null) {
            if (!island.equals(User.getUser(player).getIsland())) {
                if (!island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).manageFlags && !user.bypassing) {
                    player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantUseFlags.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                    return;
                }
            }

            player.openInventory(island.getPermissionsGUI().getInventory());
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}

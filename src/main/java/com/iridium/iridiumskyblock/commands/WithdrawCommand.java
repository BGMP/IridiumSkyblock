package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.Island;
import com.iridium.iridiumskyblock.Role;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.support.Vault;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class WithdrawCommand extends Command {

    public WithdrawCommand() {
        super(Arrays.asList("withdraw"), "Withdraw money from your Island", "", true);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (Vault.econ == null) {
            player.sendMessage(ChatColor.RED + "The server has no economy.");
            return;
        }

        if (args.length == 1) {
            player.sendMessage(ChatColor.RED + "You must specify an amount.");
            return;
        }

        User user = User.getUser(player);
        Island island = user.getIsland();
        if (island == null) {
            player.sendMessage(ChatColor.RED + "You must have an island to withdraw money from!");
            return;
        }

        if (args[1] == null) player.sendMessage(ChatColor.RED + "You must specify an amount.");

        String amountInput = args[1];
        try {
            int amount = Integer.parseInt(amountInput);
            if (amount <= 0) {
                player.sendMessage(ChatColor.RED + "Invalid amount.");
                return;
            }

            if (island.money > amount && (island.getPermissions((user.islandID == island.getId() || island.isCoop(user.getIsland())) ? (island.isCoop(user.getIsland()) ? Role.Member : user.getRole()) : Role.Visitor).withdrawBank && !user.bypassing)) {
                island.money -= amount;
                Vault.econ.depositPlayer(player, amount);
                player.sendMessage(Utils.color(IridiumSkyblock.getMessages().withdrawCommand.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix).replace("%amount%", amountInput)));
            } else player.sendMessage(Utils.color(IridiumSkyblock.getMessages().cantWithdraw.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
        } catch (NumberFormatException nfe) {
            player.sendMessage(ChatColor.RED + "Invalid number: " + amountInput);
            nfe.printStackTrace();
        }
    }

    @Override
    public List<String> TabComplete(CommandSender cs, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
package com.iridium.iridiumskyblock.commands;

import com.iridium.iridiumskyblock.IridiumSkyblock;
import com.iridium.iridiumskyblock.User;
import com.iridium.iridiumskyblock.Utils;
import com.iridium.iridiumskyblock.configs.Schematics;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {
    public List<com.iridium.iridiumskyblock.commands.Command> commands = new ArrayList<>();

    public CommandManager(String command) {
        IridiumSkyblock.getInstance().getCommand(command).setExecutor(this);
        IridiumSkyblock.getInstance().getCommand(command).setTabCompleter(this);
    }

    public void registerCommands() {
        registerCommand(IridiumSkyblock.getCommands().aboutCommand);
        registerCommand(IridiumSkyblock.getCommands().boosterCommand);
        registerCommand(IridiumSkyblock.getCommands().bypassCommand);
        registerCommand(IridiumSkyblock.getCommands().createCommand);
        registerCommand(IridiumSkyblock.getCommands().deleteCommand);
        registerCommand(IridiumSkyblock.getCommands().flyCommand);
        registerCommand(IridiumSkyblock.getCommands().homeCommand);
        registerCommand(IridiumSkyblock.getCommands().inviteCommand);
        registerCommand(IridiumSkyblock.getCommands().joinCommand);
        registerCommand(IridiumSkyblock.getCommands().kickCommand);
        registerCommand(IridiumSkyblock.getCommands().leaveCommand);
        registerCommand(IridiumSkyblock.getCommands().membersCommand);
        registerCommand(IridiumSkyblock.getCommands().privateCommand);
        registerCommand(IridiumSkyblock.getCommands().publicCommand);
        registerCommand(IridiumSkyblock.getCommands().regenCommand);
        registerCommand(IridiumSkyblock.getCommands().reloadCommand);
        registerCommand(IridiumSkyblock.getCommands().topCommand);
        registerCommand(IridiumSkyblock.getCommands().upgradeCommand);
        registerCommand(IridiumSkyblock.getCommands().valueCommand);
        registerCommand(IridiumSkyblock.getCommands().visitCommand);
        registerCommand(IridiumSkyblock.getCommands().warpCommand);
        registerCommand(IridiumSkyblock.getCommands().warpsCommand);
        registerCommand(IridiumSkyblock.getCommands().worldBorderCommand);
        registerCommand(IridiumSkyblock.getCommands().setHomeCommand);
        registerCommand(IridiumSkyblock.getCommands().permissionsCommand);
        registerCommand(IridiumSkyblock.getCommands().transferCommand);
        registerCommand(IridiumSkyblock.getCommands().adminCommand);
        registerCommand(IridiumSkyblock.getCommands().giveBoosterCommand);
        registerCommand(IridiumSkyblock.getCommands().banCommand);
        registerCommand(IridiumSkyblock.getCommands().unBanCommand);
        registerCommand(IridiumSkyblock.getCommands().coopCommand);
        registerCommand(IridiumSkyblock.getCommands().unCoopCommand);
        registerCommand(IridiumSkyblock.getCommands().setNameCommand);
        registerCommand(IridiumSkyblock.getCommands().bankCommand);
        registerCommand(IridiumSkyblock.getCommands().chatCommand);
        registerCommand(IridiumSkyblock.getCommands().giveUpgradeCommand);
        registerCommand(IridiumSkyblock.getCommands().depositCommand);
        registerCommand(IridiumSkyblock.getCommands().withdrawCommand);
        registerCommand(IridiumSkyblock.getCommands().globalFlagsCommand);
        registerCommand(IridiumSkyblock.getCommands().flagsCommand);
        if (IridiumSkyblock.getConfiguration().islandShop) registerCommand(IridiumSkyblock.getCommands().shopCommand);
        registerCommand(IridiumSkyblock.getCommands().helpCommand);
    }

    public void registerCommand(com.iridium.iridiumskyblock.commands.Command command) {
        commands.add(command);
    }

    public void unRegisterCommand(com.iridium.iridiumskyblock.commands.Command command) {
        commands.remove(command);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (args.length != 0) {
                for (com.iridium.iridiumskyblock.commands.Command command : commands) {
                    if (command.getAliases().contains(args[0]) && command.isEnabled()) {
                        if (command.isPlayer() && !(cs instanceof Player)) {
                            // Must be a player
                            cs.sendMessage(Utils.color(IridiumSkyblock.getMessages().mustBeAPlayer.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                            return true;
                        }
                        if ((cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("iridiumskyblock.")) && command.isEnabled()) {
                            command.execute(cs, args);
                        } else {
                            // No permission
                            cs.sendMessage(Utils.color(IridiumSkyblock.getMessages().noPermission.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        }
                        return true;
                    }
                }
            } else {
                if (cs instanceof Player) {
                    Player p = (Player) cs;
                    User u = User.getUser(p);
                    if (u.getIsland() != null) {
                        if (u.getIsland().getSchematic() == null) {
                            if (IridiumSkyblock.getInstance().schems.size() == 1) {
                                for (Schematics.FakeSchematic schematic : IridiumSkyblock.getInstance().schems.keySet()) {
                                    u.getIsland().setSchematic(schematic.name);
                                }
                            } else {
                                p.openInventory(u.getIsland().getSchematicSelectGUI().getInventory());
                                return true;
                            }
                        }
                        if (IridiumSkyblock.getConfiguration().islandMenu) {
                            p.openInventory(u.getIsland().getIslandMenuGUI().getInventory());
                        } else {
                            u.getIsland().teleportHome(p);
                        }
                        return true;
                    } else {
                        IridiumSkyblock.getIslandManager().createIsland(p);
                        return true;
                    }
                }
            }
            int current = 0;
            cs.sendMessage(Utils.color(IridiumSkyblock.getMessages().helpHeader));
            for (com.iridium.iridiumskyblock.commands.Command command : IridiumSkyblock.getCommandManager().commands) {
                if ((cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("iridiumskyblock.")) && command.isEnabled()) {
                    if (current < 18)
                        cs.sendMessage(Utils.color(IridiumSkyblock.getMessages().helpMessage.replace("%command%", command.getAliases().get(0)).replace("%description%", command.getDescription())));
                    current++;
                }
            }
        } catch (Exception e) {
            IridiumSkyblock.getInstance().sendErrorMessage(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String s, String[] args) {
        try {
            if (args.length == 1) {
                ArrayList<String> result = new ArrayList<>();
                for (com.iridium.iridiumskyblock.commands.Command command : commands) {
                    for (String alias : command.getAliases()) {
                        if (alias.toLowerCase().startsWith(args[0].toLowerCase()) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("iridiumskyblock.")))) {
                            result.add(alias);
                        }
                    }
                }
                return result;
            }
            for (com.iridium.iridiumskyblock.commands.Command command : commands) {
                if (command.getAliases().contains(args[0]) && (command.isEnabled() && (cs.hasPermission(command.getPermission()) || command.getPermission().equalsIgnoreCase("iridiumskyblock.")))) {
                    return command.TabComplete(cs, cmd, s, args);
                }
            }
        } catch (Exception e) {
            IridiumSkyblock.getInstance().sendErrorMessage(e);
        }
        return null;
    }
}

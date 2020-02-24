package com.iridium.iridiumskyblock.gui;

import com.iridium.iridiumskyblock.*;
import com.iridium.iridiumskyblock.api.IslandDemoteEvent;
import com.iridium.iridiumskyblock.api.IslandPromoteEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public class MembersGUI extends GUI implements Listener {

    public HashMap<Integer, User> users = new HashMap<>();

    public MembersGUI(Island island) {
        super(island, IridiumSkyblock.getInventories().membersGUISize, IridiumSkyblock.getInventories().membersGUITitle);
        IridiumSkyblock.getInstance().registerListeners(this);
    }

    @Override
    public void addContent() {
        super.addContent();
        if (getInventory().getViewers().isEmpty()) return;
        users.clear();
        Island island = getIsland();
        if (island != null) {
            int i = 0;
            for (String member : island.getMembers()) {
                User u = User.getUser(member);
                users.put(i, u);
                ItemStack head = Utils.makeItem(IridiumSkyblock.getInventories().islandmember, Arrays.asList(new Utils.Placeholder("demote", u.getRole().equals(Role.Visitor) ? IridiumSkyblock.getMessages().Kick : IridiumSkyblock.getMessages().Demote), new Utils.Placeholder("player", User.getUser(member).name), new Utils.Placeholder("role", u.getRole().toString())));
                setItem(i, head);
                i++;
            }
            setItem(27, Utils.makeItem(IridiumSkyblock.getInventories().goBackArrow, getIsland()));
        }
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().equals(getInventory())) {
            e.setCancelled(true);
            if (e.getClickedInventory() == null || !e.getClickedInventory().equals(getInventory())) return;
            if (User.getUser((OfflinePlayer) e.getWhoClicked()).bypassing || getIsland().equals(User.getUser((OfflinePlayer) e.getWhoClicked()).getIsland())) {
                if (e.getSlot() == 27) {
                    e.getWhoClicked().closeInventory();
                    Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is");
                    return;
                }
                if (users.containsKey(e.getSlot())) {
                    User u = users.get(e.getSlot());
                    User user = User.getUser((Player) e.getWhoClicked());
                    if (e.getClick().equals(ClickType.LEFT)) {
                        if (user.getIsland().getPermissions(user.role).demote) {
                            if (u.getRole().getRank() < user.role.getRank()) {
                                if (u.getRole().equals(Role.Member)) {
                                    if (user.getIsland().getPermissions(user.role).kickMembers) {
                                        user.getIsland().removeUser(u);
                                        getInventory().clear();
                                        Player player = Bukkit.getPlayer(u.name);
                                        if (player != null)
                                            player.sendMessage(Utils.color(IridiumSkyblock.getMessages().youHaveBeenKicked.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                                    } else {
                                        e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().noPermission.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                                    }
                                } else {
                                    IslandDemoteEvent event = new IslandDemoteEvent(u.getIsland(), u, user, Role.getViaRank(u.getRole().getRank() + 1));
                                    Bukkit.getPluginManager().callEvent(event);
                                    if (!event.isCancelled()) {
                                        u.role = Role.getViaRank(u.getRole().getRank() - 1);
                                        for (String member : u.getIsland().getMembers()) {
                                            Player p = Bukkit.getPlayer(User.getUser(member).name);
                                            if (p != null) {
                                                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().playerDemoted.replace("%rank%", u.getRole().toString()).replace("%player%", u.name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                                            }
                                        }
                                    }
                                }
                            } else {
                                e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().cantDemoteUser.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                            }
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().noPermission.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        }
                    } else {
                        if (e.getSlot() == 27) {
                            e.getWhoClicked().closeInventory();
                            Bukkit.getServer().dispatchCommand(e.getWhoClicked(), "is");
                            return;
                        }
                        if (user.getIsland().getPermissions(user.role).promote) {
                            if (!(u.getRole().equals(Role.Owner))) {
                                if (u.getRole().getRank() < user.role.getRank()) {
                                    if (u.getRole().getRank() >= Role.CoOwner.getRank()) {
                                        e.getWhoClicked().openInventory(new ConfirmationGUI(user.getIsland(), () -> {
                                            OfflinePlayer p = Bukkit.getOfflinePlayer(u.name);
                                            if (p != null) {
                                                u.getIsland().setOwner(p);
                                            }
                                        }, IridiumSkyblock.getMessages().transferAction.replace("%player%", u.name)).getInventory());
                                    } else {
                                        IslandPromoteEvent event = new IslandPromoteEvent(u.getIsland(), u, user, Role.getViaRank(u.getRole().getRank() + 1));
                                        Bukkit.getPluginManager().callEvent(event);
                                        if (!event.isCancelled()) {
                                            u.role = Role.getViaRank(u.getRole().getRank() + 1);
                                            for (String member : u.getIsland().getMembers()) {
                                                Player p = Bukkit.getPlayer(User.getUser(member).name);
                                                if (p != null) {
                                                    p.sendMessage(Utils.color(IridiumSkyblock.getMessages().playerPromoted.replace("%rank%", u.getRole().toString()).replace("%player%", u.name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().cantPromoteUser.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                                }
                            } else {
                                e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().noPermission.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                            }
                        } else {
                            e.getWhoClicked().sendMessage(Utils.color(IridiumSkyblock.getMessages().cantDemoteOwner.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                        }
                    }
                }
            }
        }
    }
}

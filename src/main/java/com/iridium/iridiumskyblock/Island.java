package com.iridium.iridiumskyblock;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.spawn.EssentialsSpawn;
import com.iridium.iridiumskyblock.api.IslandCreateEvent;
import com.iridium.iridiumskyblock.api.IslandDeleteEvent;
import com.iridium.iridiumskyblock.configs.Schematics;
import com.iridium.iridiumskyblock.gui.*;
import com.iridium.iridiumskyblock.support.EpicSpawners;
import com.iridium.iridiumskyblock.support.MergedSpawners;
import com.iridium.iridiumskyblock.support.UltimateStacker;
import com.iridium.iridiumskyblock.support.Wildstacker;
import net.md_5.bungee.api.chat.*;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Island {

    public static class Warp {
        Location location;
        String name;
        String password;

        public Warp(Location location, String name, String password) {
            this.location = location;
            this.name = name;
            this.password = password;
        }

        public Location getLocation() {
            return location;
        }

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
    }

    private String owner;
    private HashSet<String> members;
    private Location pos1;
    private Location pos2;
    private Location center;
    private Location home;
    private Location netherhome;

    private transient UpgradeGUI upgradeGUI;
    private transient BoosterGUI boosterGUI;
    private transient MissionsGUI missionsGUI;
    private transient MembersGUI membersGUI;
    private transient WarpGUI warpGUI;
    private transient BorderColorGUI borderColorGUI;
    private transient SchematicSelectGUI schematicSelectGUI;
    private transient PermissionsGUI permissionsGUI;
    private transient IslandMenuGUI islandMenuGUI;
    private transient CoopGUI coopGUI;
    private transient BankGUI bankGUI;
    private transient FlagsGUI flagsGUI;
    private transient GlobalFlagsGUI globalsGUI;

    private int id;

    private int spawnerBooster;
    private int farmingBooster;
    private int expBooster;
    private int flightBooster;

    private int boosterid;

    private int sizeLevel;
    private int memberLevel;
    private int warpLevel;
    private int oreLevel;

    private int a;

    public transient int genearteID;

    private int value;

    public List<Location> blocks;
    public transient int lastblocks = 0;

    private List<Warp> warps;

    private int startvalue;

    private HashMap<String, Integer> missions = new HashMap<>();

    private HashMap<String, Integer> missionLevels = new HashMap<>();

    private boolean visit;

    private NMSUtils.Color borderColor;

    private HashMap<Role, Permissions> permissions;

    private GlobalPermissions globals;

    private String schematic;

    private HashSet<String> bans;

    private HashSet<String> votes;

    private HashSet<Integer> coop;

    public transient HashSet<Integer> coopInvites;

    private String name;

    public int money;
    public int exp;

    public Biome biome;

    public transient HashSet<Location> failedGenerators;

    private Date lastRegen;

    public Island(Player owner, Location pos1, Location pos2, Location center, Location home, Location netherhome, int id) {
        User user = User.getUser(owner);
        user.role = Role.Owner;
        this.biome = IridiumSkyblock.getConfiguration().defaultBiome;
        blocks = new ArrayList<>();
        this.owner = user.player;
        this.name = user.name;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.center = center;
        this.home = home;
        this.netherhome = netherhome;
        this.members = new HashSet<>(Collections.singletonList(user.player));
        this.id = id;
        spawnerBooster = 0;
        farmingBooster = 0;
        expBooster = 0;
        flightBooster = 0;
        sizeLevel = 1;
        memberLevel = 1;
        warpLevel = 1;
        oreLevel = 1;
        value = 0;
        warps = new ArrayList<>();
        startvalue = -1;
        borderColor = NMSUtils.Color.Off;
        visit = IridiumSkyblock.getConfiguration().defaultIslandPublic;
        permissions = (HashMap<Role, Permissions>) IridiumSkyblock.getConfiguration().defaultPermissions.clone();
        globals = IridiumSkyblock.getConfiguration().defaultGlobals;
        this.coop = new HashSet<>();
        this.bans = new HashSet<>();
        this.votes = new HashSet<>();
        init();
        Bukkit.getPluginManager().callEvent(new IslandCreateEvent(owner, this));
    }

    public void resetMissions() {
        if (missions == null) missions = new HashMap<>();
        for (String mission : missions.keySet()) {
            //Check if mission is complete
            if (!getMissionLevels().containsKey(mission)) getMissionLevels().put(mission, 1);
            if (missions.get(mission).equals(Integer.MIN_VALUE)) {
                if (IridiumSkyblock.getMissions().mission.get(mission).containsKey(getMissionLevels().get(mission) + 1)) {
                    //We have a new mission available
                    getMissionLevels().put(mission, getMissionLevels().get(mission) + 1);
                }
            }
        }
        missions.clear();
    }

    public Permissions getPermissions(Role role) {
        if (permissions == null)
            permissions = (HashMap<Role, Permissions>) IridiumSkyblock.getConfiguration().defaultPermissions.clone();
        if (!permissions.containsKey(role)) {
            permissions.put(role, new Permissions());
        }
        return permissions.get(role);
    }

    public GlobalPermissions getGlobals() {
        if (globals == null) {
            return IridiumSkyblock.getConfiguration().defaultGlobals;
        } else return globals;
    }

    public void sendBorder() {
        for (Player p : getPlayersOnIsland()) {
            sendBorder(p);
        }
    }

    public void hideBorder() {
        for (Player p : getPlayersOnIsland()) {
            hideBorder(p);
        }
    }

    public void sendBorder(Player p) {
        if (p.getLocation().getWorld().equals(IridiumSkyblock.getIslandManager().getWorld())) {
            NMSUtils.sendWorldBorder(p, borderColor, IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(sizeLevel).size + 1, getCenter());
        } else if (IridiumSkyblock.getConfiguration().netherIslands) {
            Location loc = getCenter().clone();
            loc.setWorld(IridiumSkyblock.getIslandManager().getNetherWorld());
            NMSUtils.sendWorldBorder(p, borderColor, IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(sizeLevel).size + 1, loc);
        }
    }

    public void hideBorder(Player p) {
        NMSUtils.sendWorldBorder(p, borderColor, Integer.MAX_VALUE, getCenter().clone());
    }

    public void calculateIslandValue() {
        if (blocks == null) blocks = new ArrayList<>();
        if (blocks.hashCode() == lastblocks) return;
        int value = 0;
        ListIterator<Location> locations = blocks.listIterator();
        while (locations.hasNext()) {
            Location loc = locations.next();
            Block block = loc.getBlock();
            if (IridiumSkyblock.getBlockValues().blockvalue.containsKey(MultiversionMaterials.fromMaterial(block.getType()))) {
                value += IridiumSkyblock.getBlockValues().blockvalue.get(MultiversionMaterials.fromMaterial(block.getType()));
            } else if (loc.getBlock().getState() instanceof CreatureSpawner) {
                CreatureSpawner spawner = (CreatureSpawner) block.getState();
                if (IridiumSkyblock.getBlockValues().spawnervalue.containsKey(spawner.getSpawnedType().name())) {
                    int temp = IridiumSkyblock.getBlockValues().spawnervalue.get(spawner.getSpawnedType().name());
                    if (Wildstacker.enabled) {
                        temp *= Wildstacker.getSpawnerAmount((CreatureSpawner) loc.getBlock().getState());
                    } else if (MergedSpawners.enabled) {
                        temp *= MergedSpawners.getSpawnerAmount((CreatureSpawner) loc.getBlock().getState());
                    } else if (UltimateStacker.enabled) {
                        temp *= UltimateStacker.getSpawnerAmount((CreatureSpawner) loc.getBlock().getState());
                    } else if (EpicSpawners.enabled) {
                        temp *= EpicSpawners.getSpawnerAmount((CreatureSpawner) loc.getBlock().getState());
                    }
                    value += temp;
                } else {
                    locations.remove();
                }
            } else {
                locations.remove();
            }
        }
        this.value = value;
        if (startvalue == -1) startvalue = value;
        lastblocks = blocks.hashCode();
    }

    /**
     * TODO: Fix Island value
     **/
    public void initBlocks() {
        this.a = Bukkit.getScheduler().scheduleSyncRepeatingTask(IridiumSkyblock.getInstance(), new Runnable() {
            int world = 0;
            double X = pos1.getX();
            double Y = 0;
            double Z = pos1.getZ();

            @Override
            public void run() {
                try {
                    for (int i = 0; i < IridiumSkyblock.getConfiguration().blocksPerTick; i++) {
                        if (X < pos2.getX()) {
                            X++;
                        } else if (Z < pos2.getZ()) {
                            X = pos1.getX();
                            Z++;
                        } else if (Y <= IridiumSkyblock.getIslandManager().getWorld().getMaxHeight()) {
                            X = pos1.getX();
                            Z = pos1.getZ();
                            Y++;
                        } else if (world <= 1 && IridiumSkyblock.getConfiguration().netherIslands) {
                            world++;
                            X = pos1.getX();
                            Y = 0;
                            Z = pos1.getZ();
                        } else {
                            Bukkit.getScheduler().cancelTask(a);
                            a = -1;
                            IridiumSkyblock.getInstance().updatingBlocks = false;
                        }
                        if (IridiumSkyblock.getInstance().updatingBlocks) {
                            if (world == 0) {
                                Location loc = new Location(IridiumSkyblock.getIslandManager().getWorld(), X, Y, Z);
                                if (Utils.isBlockValuable(loc.getBlock()) && !blocks.contains(loc)) {
                                    blocks.add(loc);
                                }
                            } else if (IridiumSkyblock.getConfiguration().netherIslands) {
                                Location loc = new Location(IridiumSkyblock.getIslandManager().getNetherWorld(), X, Y, Z);
                                if (Utils.isBlockValuable(loc.getBlock()) && !blocks.contains(loc)) {
                                    blocks.add(loc);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    IridiumSkyblock.getInstance().sendErrorMessage(e);
                }
            }
        }, 0, 1);
    }

    public void addWarp(Player player, Location location, String name, String password) {
        if (warps.size() < 20) {
            warps.add(new Warp(location, name, password));
            player.sendMessage(Utils.color(IridiumSkyblock.getMessages().warpAdded.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
        } else {
            player.sendMessage(Utils.color(IridiumSkyblock.getMessages().maxWarpsReached.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
        }
    }

    public void addUser(User user) {
        if (members.size() < 20) {

            for (String player : members) {
                User u = User.getUser(player);
                Player p = Bukkit.getPlayer(u.name);
                if (p != null) {
                    p.sendMessage(Utils.color(IridiumSkyblock.getMessages().playerJoinedYourIsland.replace("%player%", user.name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            }
            bans.remove(user.player);
            user.islandID = id;
            user.role = Role.Member;
            user.invites.clear();
            members.add(user.player);
            teleportHome(Bukkit.getPlayer(user.name));
            user.invites.clear();
        } else {
            if (Bukkit.getPlayer(user.name) != null) {
                Bukkit.getPlayer(user.name).sendMessage(Utils.color(IridiumSkyblock.getMessages().maxMemberCount.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
        getMembersGUI().getInventory().clear();
        getMembersGUI().addContent();
    }

    public void removeUser(User user) {
        user.islandID = 0;
        Player player = Bukkit.getPlayer(user.name);
        if (player != null) {
            spawnPlayer(player);
            player.setFlying(false);
            player.setAllowFlight(false);
        }
        members.remove(user.player);
        user.role = Role.Visitor;
        for (String member : members) {
            User u = User.getUser(member);
            Player p = Bukkit.getPlayer(u.name);
            if (p != null) {
                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().kickedMember.replace("%member%", user.name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
        getMembersGUI().getInventory().clear();
        getMembersGUI().addContent();
    }

    public boolean isInIsland(Location location) {
        return (location.getX() > getPos1().getX() - 1 && location.getX() < getPos2().getX() + 1) && (location.getZ() > getPos1().getZ() - 1 && location.getZ() < getPos2().getZ() + 1);
    }

    public void init() {
        if (biome == null) biome = IridiumSkyblock.getConfiguration().defaultBiome;
        if (blocks == null) blocks = new ArrayList<>();
        if (members == null) {
            members = new HashSet<>();
            members.add(owner);
        }

        blocks = new ArrayList<>(new HashSet<>(blocks));

        upgradeGUI = new UpgradeGUI(this);
        boosterGUI = new BoosterGUI(this);
        missionsGUI = new MissionsGUI(this);
        membersGUI = new MembersGUI(this);
        warpGUI = new WarpGUI(this);
        borderColorGUI = new BorderColorGUI(this);
        schematicSelectGUI = new SchematicSelectGUI(this);
        permissionsGUI = new PermissionsGUI(this);
        islandMenuGUI = new IslandMenuGUI(this);
        coopGUI = new CoopGUI(this);
        bankGUI = new BankGUI(this);
        flagsGUI = new FlagsGUI(this);
        globalsGUI = new GlobalFlagsGUI(this);
        failedGenerators = new HashSet<>();
        coopInvites = new HashSet<>();
        boosterid = Bukkit.getScheduler().scheduleAsyncRepeatingTask(IridiumSkyblock.getInstance(), () -> {
            if (spawnerBooster > 0) spawnerBooster--;
            if (farmingBooster > 0) farmingBooster--;
            if (expBooster > 0) expBooster--;
            if (flightBooster == 1) {
                for (String player : members) {
                    Player p = Bukkit.getPlayer(player);
                    if (p != null) {
                        if (!p.hasPermission("IridiumSkyblock.Fly") && p.getGameMode().equals(GameMode.SURVIVAL)) {
                            p.setAllowFlight(false);
                            p.setFlying(false);
                            User.getUser(p).flying = false;
                        }
                    }
                }
            }
            if (flightBooster > 0) flightBooster--;
        }, 0, 20);
        if (permissions == null) {
            permissions = new HashMap<Role, Permissions>() {{
                for (Role role : Role.values()) {
                    put(role, new Permissions());
                }
            }};
        }
        sendBorder();
    }

    public long canGenerate() {
        if (lastRegen == null) return 0;
        if (new Date().after(lastRegen)) {
            return 0;
        }
        return lastRegen.getTime() - System.currentTimeMillis();
    }

    public Location getSchematicLocation(Schematics.FakeSchematic fakeSchematic) {
        Schematic schematic = IridiumSkyblock.getInstance().schems.get(fakeSchematic);
        Location loc = getCenter().clone();
        return loc;
    }

    public Location getNetherSchematicLocation(Schematics.FakeSchematic fakeSchematic) {
        Schematic schematic = IridiumSkyblock.getInstance().netherschems.get(fakeSchematic);
        Location loc = getCenter().clone();
        loc.setWorld(IridiumSkyblock.getIslandManager().getNetherWorld());
        return loc;
    }

    public void pasteSchematic(boolean deleteBlocks) {
        //TODO
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, IridiumSkyblock.getConfiguration().regenCooldown);
        lastRegen = c.getTime();
        if (deleteBlocks) deleteBlocks();
        getCenter().getBlock().setType(Material.STONE);
        if (IridiumSkyblock.worldEdit == null) {
            pasteSchematic();
        } else {
            File schematicFolder = new File(IridiumSkyblock.getInstance().getDataFolder(), "schematics");
            for (Schematics.FakeSchematic fakeSchematic : IridiumSkyblock.getInstance().schems.keySet()) {
                if (fakeSchematic.name.equals(schematic)) {
                    IridiumSkyblock.worldEdit.paste(new File(schematicFolder, fakeSchematic.name), getSchematicLocation(fakeSchematic), this);
                    if (IridiumSkyblock.getConfiguration().netherIslands) {
                        IridiumSkyblock.worldEdit.paste(new File(schematicFolder, fakeSchematic.netherisland), getNetherSchematicLocation(fakeSchematic), this);
                    }
                    break;
                }
            }
        }
        killEntities();
        //Reset island home
        for (Schematics.FakeSchematic schematic : IridiumSkyblock.getInstance().schems.keySet()) {
            if (!schematic.name.equals(this.schematic)) continue;
            home = new Location(IridiumSkyblock.getIslandManager().getWorld(), getCenter().getX() + schematic.x, schematic.y, getCenter().getZ() + schematic.z);
        }
    }

    public void teleportPlayersHome() {
        for (Player p : getPlayersOnIsland()) {
            teleportHome(p);
        }
    }

    public void pasteSchematic(Player player, boolean deleteBlocks) {
        pasteSchematic(deleteBlocks);
        User.getUser(player).teleportingHome = false;
        teleportHome(player);
        sendBorder(player);
        NMSUtils.sendTitle(player, IridiumSkyblock.getMessages().islandCreated, 20, 40, 20);
        if (!IridiumSkyblock.getMessages().islandCreatedSubtitle.isEmpty())
            NMSUtils.sendSubTitle(player, IridiumSkyblock.getMessages().islandCreatedSubtitle, 20, 40, 20);
    }

    private int getHeight() {
        for (Schematics.FakeSchematic fakeSchematic : IridiumSkyblock.getInstance().schems.keySet()) {
            if (fakeSchematic.name.equals(schematic)) {
                return getCenter().getBlockY() - (IridiumSkyblock.getInstance().schems.get(fakeSchematic).getHeight() / 2) - 1;
            }
        }
        return 0;
    }

    private int getMax() {
        for (Schematics.FakeSchematic fakeSchematic : IridiumSkyblock.getInstance().schems.keySet()) {
            if (fakeSchematic.name.equals(schematic)) {
                return getCenter().getBlockY() + (IridiumSkyblock.getInstance().schems.get(fakeSchematic).getHeight() / 2) + 1;
            }
        }
        return 0;
    }

    private void pasteSchematic() {
        for (Schematics.FakeSchematic fakeSchematic : IridiumSkyblock.getInstance().schems.keySet()) {
            if (fakeSchematic.name.equals(schematic)) {
                blocks.addAll(IridiumSkyblock.getInstance().schems.get(fakeSchematic).pasteSchematic(getCenter().clone(), this));
                if (IridiumSkyblock.getConfiguration().debugSchematics) {
                    File schematicFolder = new File(IridiumSkyblock.getInstance().getDataFolder(), "schematics");
                    try {
                        Schematic.debugSchematic(new File(schematicFolder, fakeSchematic.name));
                        if (IridiumSkyblock.getConfiguration().netherIslands)
                            Schematic.debugSchematic(new File(schematicFolder, fakeSchematic.netherisland));
                    } catch (IOException e) {
                    }
                }
                Location center = getCenter().clone();
                if (IridiumSkyblock.getConfiguration().netherIslands) {
                    center.setWorld(IridiumSkyblock.getIslandManager().getNetherWorld());
                    blocks.addAll(IridiumSkyblock.getInstance().netherschems.get(fakeSchematic).pasteSchematic(center, this));
                }
            }
        }
    }

    public void clearInventories() {
        if (IridiumSkyblock.getConfiguration().clearInventories) {
            for (String player : members) {
                User user = User.getUser(player);
                if (Bukkit.getPlayer(user.name) != null) Bukkit.getPlayer(user.name).getInventory().clear();
            }
        }
    }

    public void teleportHome(Player p) {
        if (User.getUser(p).teleportingHome) {
            return;
        }
        if (isBanned(User.getUser(p)) && !members.contains(p.getUniqueId().toString())) {
            p.sendMessage(Utils.color(IridiumSkyblock.getMessages().bannedFromIsland.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            return;
        }
        if (getSchematic() == null) {
            User u = User.getUser(p);
            if (u.getIsland().equals(this)) {
                if (IridiumSkyblock.getInstance().schems.size() == 1) {
                    for (Schematics.FakeSchematic schematic : IridiumSkyblock.getInstance().schems.keySet()) {
                        setSchematic(schematic.name);
                    }
                } else {
                    p.openInventory(getSchematicSelectGUI().getInventory());
                }
            }
            return;
        }
        p.setFallDistance(0);
        if (members.contains(p.getUniqueId().toString())) {
            p.sendMessage(Utils.color(IridiumSkyblock.getMessages().teleportingHome.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
        } else {
            p.sendMessage(Utils.color(IridiumSkyblock.getMessages().visitingIsland.replace("%player%", User.getUser(owner).name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            for (String pl : members) {
                Player player = Bukkit.getPlayer(User.getUser(pl).name);
                if (player != null) {
                    player.sendMessage(Utils.color(IridiumSkyblock.getMessages().visitedYourIsland.replace("%player%", p.getName()).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            }
        }
        if (Utils.isSafe(getHome(), this)) {
            p.teleport(getHome().add(0, 2, 0));
            sendBorder(p);
        } else {
            Location loc = Utils.getNewHome(this, this.home);
            if (loc != null) {
                this.home = loc;
                p.teleport(this.home.add(0, 2, 0));
                sendBorder(p);
            } else {
                User.getUser(p).teleportingHome = true;
                pasteSchematic(p, false);
            }
        }
    }

    public void teleportNetherHome(Player p) {
        if (User.getUser(p).teleportingHome) {
            return;
        }
        if (isBanned(User.getUser(p)) && !members.contains(p.getUniqueId().toString())) {
            p.sendMessage(Utils.color(IridiumSkyblock.getMessages().bannedFromIsland.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            return;
        }
        if (getSchematic() == null) {
            User u = User.getUser(p);
            if (u.getIsland().equals(this)) {
                if (IridiumSkyblock.getInstance().schems.size() == 1) {
                    for (Schematics.FakeSchematic schematic : IridiumSkyblock.getInstance().schems.keySet()) {
                        setSchematic(schematic.name);
                    }
                } else {
                    p.openInventory(getSchematicSelectGUI().getInventory());
                }
            }
            return;
        }
        p.setFallDistance(0);
        if (members.contains(p.getUniqueId().toString())) {
            p.sendMessage(Utils.color(IridiumSkyblock.getMessages().teleportingHome.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
        } else {
            p.sendMessage(Utils.color(IridiumSkyblock.getMessages().visitingIsland.replace("%player%", User.getUser(owner).name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            for (String pl : members) {
                Player player = Bukkit.getPlayer(User.getUser(pl).name);
                if (player != null) {
                    player.sendMessage(Utils.color(IridiumSkyblock.getMessages().visitedYourIsland.replace("%player%", p.getName()).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
                }
            }
        }
        if (Utils.isSafe(getNetherhome(), this)) {
            p.teleport(getNetherhome().add(0, 2, 0));
            sendBorder(p);
        } else {

            Location loc = Utils.getNewHome(this, this.netherhome);
            if (loc != null) {
                this.netherhome = loc;
                p.teleport(this.netherhome.add(0, 2, 0));
                sendBorder(p);
            } else {
                User.getUser(p).teleportingHome = true;
                pasteSchematic(p, false);
            }
        }
    }

    public void delete() {
        Bukkit.getPluginManager().callEvent(new IslandDeleteEvent(this));

        Bukkit.getScheduler().cancelTask(getMembersGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getBoosterGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getMissionsGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getUpgradeGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getWarpGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getPermissionsGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getIslandMenuGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getCoopGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getBankGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getFlagsGUI().scheduler);
        Bukkit.getScheduler().cancelTask(getGlobalsGUI().scheduler);
        if (genearteID != -1) Bukkit.getScheduler().cancelTask(genearteID);
        permissions.clear();
        if (a != -1) Bukkit.getScheduler().cancelTask(a);
        clearInventories();
        spawnPlayers();
        for (String player : members) {
            User.getUser(player).islandID = 0;
            Player p = Bukkit.getPlayer(User.getUser(player).name);
            if (p != null) {
                p.closeInventory();
                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().islandDeleted.replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
        killEntities();
        deleteBlocks();
        for (int id : coop) {
            IridiumSkyblock.getIslandManager().getIslandViaId(id).coop.remove(getId());
        }
        coop = null;
        hideBorder();
        this.owner = null;
        this.pos1 = null;
        this.pos2 = null;
        this.members = null;
        this.center = null;
        this.home = null;
        IridiumSkyblock.getIslandManager().islands.remove(this.id);
        this.id = 0;
        IridiumSkyblock.getInstance().saveConfigs();
        Bukkit.getScheduler().cancelTask(boosterid);
        boosterid = -1;
    }

    public void removeBan(User user) {
        if (bans == null) bans = new HashSet<>();
        bans.remove(user.player);
    }

    public void addBan(User user) {
        if (bans == null) bans = new HashSet<>();
        bans.add(user.player);
    }

    public void removeVote(User user) {
        if (votes == null) votes = new HashSet<>();
        votes.remove(user.player);
    }

    public void addVote(User user) {
        if (votes == null) votes = new HashSet<>();
        votes.add(user.player);
    }

    public boolean hasVoted(User user) {
        if (votes == null) votes = new HashSet<>();
        return votes.contains(user.player);
    }

    public int getVotes() {
        if (votes == null) votes = new HashSet<>();
        return votes.size();
    }

    public boolean isBanned(User user) {
        if (bans == null) bans = new HashSet<>();
        return bans.contains(user.player);
    }

    public void addCoop(Island island) {
        if (coop == null) coop = new HashSet<>();
        for (String member : island.getMembers()) {
            Player pl = Bukkit.getPlayer(User.getUser(member).name);
            if (pl != null) {
                pl.sendMessage(Utils.color(IridiumSkyblock.getMessages().coopGiven.replace("%player%", User.getUser(owner).name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
        for (String member : getMembers()) {
            Player pl = Bukkit.getPlayer(User.getUser(member).name);
            if (pl != null) {
                pl.sendMessage(Utils.color(IridiumSkyblock.getMessages().coopAdded.replace("%player%", User.getUser(island.getOwner()).name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
        coop.add(island.id);
        if (island.coop == null) island.coop = new HashSet<>();
        island.coop.add(id);
    }

    public void inviteCoop(Island island) {
        if (coopInvites == null) coopInvites = new HashSet<>();
        coopInvites.add(island.getId());
        for (String member : getMembers()) {
            Player pl = Bukkit.getPlayer(User.getUser(member).name);
            if (pl != null) {
                BaseComponent[] components = TextComponent.fromLegacyText(Utils.color(IridiumSkyblock.getMessages().coopInvite.replace("%player%", User.getUser(island.getOwner()).name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));

                ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/is coop " + User.getUser(island.getOwner()).name);
                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to coop players island!").create());
                for (BaseComponent component : components) {
                    component.setClickEvent(clickEvent);
                    component.setHoverEvent(hoverEvent);
                }
                pl.getPlayer().spigot().sendMessage(components);
            }
        }
    }

    public void removeCoop(Island island) {
        if (coop == null) coop = new HashSet<>();
        coop.remove(island.id);
        if (island.coop == null) island.coop = new HashSet<>();
        island.coop.remove(id);
        for (String member : island.getMembers()) {
            Player pl = Bukkit.getPlayer(User.getUser(member).name);
            if (pl != null) {
                pl.sendMessage(Utils.color(IridiumSkyblock.getMessages().coopTaken.replace("%player%", User.getUser(owner).name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
        for (String member : getMembers()) {
            Player pl = Bukkit.getPlayer(User.getUser(member).name);
            if (pl != null) {
                pl.sendMessage(Utils.color(IridiumSkyblock.getMessages().coopTaken.replace("%player%", User.getUser(island.getOwner()).name).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));
            }
        }
        getCoopGUI().getInventory().clear();
        getCoopGUI().addContent();
        island.getCoopGUI().getInventory().clear();
        island.getCoopGUI().addContent();
    }

    public void removeCoop(int id) {
        if (coop == null) coop = new HashSet<>();
        coop.remove(id);
    }

    public boolean isCoop(Island island) {
        if (coop == null) coop = new HashSet<>();
        if (island == null) return false;
        return coop.contains(island.id);
    }

    public HashSet<Integer> getCoop() {
        if (coop == null) coop = new HashSet<>();
        return coop;
    }

    public void spawnPlayers() {
        for (Player p : getPlayersOnIsland()) {
            spawnPlayer(p);
        }
    }

    public List<Player> getPlayersOnIsland() {
        List<Player> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (isInIsland(p.getLocation())) {
                players.add(p);
            }
        }
        return players;
    }

    public void spawnPlayer(Player player) {
        if (player == null) return;
        if (Bukkit.getPluginManager().isPluginEnabled("EssentialsSpawn")) {
            EssentialsSpawn essentialsSpawn = (EssentialsSpawn) Bukkit.getPluginManager().getPlugin("EssentialsSpawn");
            Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
            player.teleport(essentialsSpawn.getSpawn(essentials.getUser(player).getGroup()));
        } else {
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        }
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
        for (double X = getPos1().getX(); X <= getPos2().getX(); X++) {
            for (double Z = getPos1().getZ(); Z <= getPos2().getZ(); Z++) {
                IridiumSkyblock.getIslandManager().getWorld().setBiome((int) X, (int) Z, biome);
            }
        }
        for (int X = getPos1().getChunk().getX(); X <= getPos2().getChunk().getX(); X++) {
            for (int Z = getPos1().getChunk().getZ(); Z <= getPos2().getChunk().getZ(); Z++) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    NMSUtils.sendChunk(p, IridiumSkyblock.getIslandManager().getWorld().getChunkAt(X, Z));
                }
            }
        }

    }

    public void deleteBlocks() {
        blocks.clear();
        for (int X = getPos1().getBlockX(); X <= getPos2().getBlockX(); X++) {
            for (int Y = 0; Y <= 255; Y++) {
                for (int Z = getPos1().getBlockZ(); Z <= getPos2().getBlockZ(); Z++) {
                    NMSUtils.setBlockFast(IridiumSkyblock.getIslandManager().getWorld(), X, Y, Z, 0, (byte) 0);
                }
            }
        }
        if (IridiumSkyblock.getConfiguration().netherIslands) {
            for (int X = getPos1().getBlockX(); X <= getPos2().getBlockX(); X++) {
                for (int Y = 0; Y <= 255; Y++) {
                    for (int Z = getPos1().getBlockZ(); Z <= getPos2().getBlockZ(); Z++) {
                        NMSUtils.setBlockFast(IridiumSkyblock.getIslandManager().getNetherWorld(), X, Y, Z, 0, (byte) 0);
                    }
                }
            }
        }
    }

    public void killEntities() {
        for (int X = getPos1().getChunk().getX(); X <= getPos2().getChunk().getX(); X++) {
            for (int Z = getPos1().getChunk().getZ(); Z <= getPos2().getChunk().getZ(); Z++) {
                Chunk overworld = IridiumSkyblock.getIslandManager().getWorld().getChunkAt(X, Z);
                Chunk nether = IridiumSkyblock.getIslandManager().getWorld().getChunkAt(X, Z);
                for (Entity e : overworld.getEntities()) {
                    if (!e.getType().equals(EntityType.PLAYER)) {
                        e.remove();
                    }
                }
                for (Entity e : nether.getEntities()) {
                    if (!e.getType().equals(EntityType.PLAYER)) {
                        e.remove();
                    }
                }
            }
        }
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public Location getCenter() {
        return center;
    }

    public Location getHome() {
        return home;
    }

    public Location getNetherhome() {
        if (netherhome == null) {
            netherhome = getHome().clone();
            netherhome.setWorld(IridiumSkyblock.getIslandManager().getNetherWorld());
        }
        return netherhome;
    }

    public void setHome(Location home) {
        this.home = home;
    }

    public void setNetherhome(Location netherhome) {
        this.netherhome = netherhome;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(OfflinePlayer owner) {

        for (String player : members) {
            User user = User.getUser(player);
            Player p = Bukkit.getPlayer(user.name);
            if (p != null) {
                p.sendMessage(Utils.color(IridiumSkyblock.getMessages().transferdOwnership.replace("%player%", owner.getName()).replace("%prefix%", IridiumSkyblock.getConfiguration().prefix)));

            }
        }
        User.getUser(getOwner()).role = Role.CoOwner;
        this.owner = owner.getUniqueId().toString();
        User.getUser(getOwner()).role = Role.Owner;
    }

    public int getId() {
        return id;
    }

    public BankGUI getBankGUI() {
        return bankGUI;
    }

    public CoopGUI getCoopGUI() {
        return coopGUI;
    }

    public UpgradeGUI getUpgradeGUI() {
        return upgradeGUI;
    }

    public BoosterGUI getBoosterGUI() {
        return boosterGUI;
    }

    public SchematicSelectGUI getSchematicSelectGUI() {
        return schematicSelectGUI;
    }

    public MissionsGUI getMissionsGUI() {
        return missionsGUI;
    }

    public MembersGUI getMembersGUI() {
        return membersGUI;
    }

    public WarpGUI getWarpGUI() {
        return warpGUI;
    }

    public PermissionsGUI getPermissionsGUI() {
        return permissionsGUI;
    }

    public IslandMenuGUI getIslandMenuGUI() {
        return islandMenuGUI;
    }

    public BorderColorGUI getBorderColorGUI() {
        return borderColorGUI;
    }

    public FlagsGUI getFlagsGUI() {
        return flagsGUI;
    }

    public GlobalFlagsGUI getGlobalsGUI() {
        return globalsGUI;
    }

    public int getSpawnerBooster() {
        return spawnerBooster;
    }

    public void setSpawnerBooster(int spawnerBooster) {
        this.spawnerBooster = spawnerBooster;
    }

    public int getFarmingBooster() {
        return farmingBooster;
    }

    public void setFarmingBooster(int farmingBooster) {
        this.farmingBooster = farmingBooster;
    }

    public int getExpBooster() {
        return expBooster;
    }

    public void setExpBooster(int expBooster) {
        this.expBooster = expBooster;
    }

    public int getFlightBooster() {
        return flightBooster;
    }

    public void setFlightBooster(int flightBooster) {
        this.flightBooster = flightBooster;
    }

    public HashSet<String> getMembers() {
        return members;
    }

    public int getSizeLevel() {
        return sizeLevel;
    }

    public void setSizeLevel(int sizeLevel) {
        this.sizeLevel = sizeLevel;

        pos1 = getCenter().clone().subtract(IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(sizeLevel).size / 2.00, 0, IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(sizeLevel).size / 2.00);
        pos2 = getCenter().clone().add(IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(sizeLevel).size / 2.00, 0, IridiumSkyblock.getUpgrades().sizeUpgrade.upgrades.get(sizeLevel).size / 2.00);
        sendBorder();
    }

    public int getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public int getWarpLevel() {
        return warpLevel;
    }

    public void setWarpLevel(int warpLevel) {
        this.warpLevel = warpLevel;
    }

    public int getOreLevel() {
        return oreLevel;
    }

    public void setOreLevel(int oreLevel) {
        this.oreLevel = oreLevel;
    }

    public void removeWarp(Warp warp) {
        warps.remove(warp);
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public int getValue() {
        return value;
    }

    public NMSUtils.Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(NMSUtils.Color borderColor) {
        this.borderColor = borderColor;
    }

    public boolean isVisit() {
        return visit;
    }

    public void setVisit(boolean visit) {
        this.visit = visit;
    }

    public String getSchematic() {
        return schematic;
    }

    public void setSchematic(String schematic) {
        this.schematic = schematic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        if (name == null) name = User.getUser(getOwner()).name;
        return name;
    }

    public Biome getBiome() {
        return biome;
    }

    public HashMap<String, Integer> getMissionLevels() {
        if (missionLevels == null) missionLevels = new HashMap<>();
        return missionLevels;
    }
}

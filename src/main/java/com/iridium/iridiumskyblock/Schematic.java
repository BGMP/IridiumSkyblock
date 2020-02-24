package com.iridium.iridiumskyblock;

import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;
import org.jnbt.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schematic {

    public static HashMap<String, Schematic> cache = new HashMap<>();

    public enum SchematicVersion {
        v1_13, v_1_8
    }

    private short width;
    private short length;
    private short height;
    private List<Tag> tileEntities;

    private byte[] blocks;
    private byte[] data;
    private byte[] blockdata;
    private Map<String, Tag> palette;

    private Integer version;

    private SchematicVersion schematicVersion;

    public Schematic(short width, short length, short height, List<Tag> tileEntities, byte[] blocks, byte[] data, List<Tag> entities) {
        this.blocks = blocks;
        this.data = data;
        this.width = width;
        this.length = length;
        this.height = height;
        this.tileEntities = tileEntities;
        this.schematicVersion = SchematicVersion.v_1_8;
    }

    public Schematic(short width, short length, short height, byte[] blockdata, Map<String, Tag> palette, int version) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.palette = palette;
        this.blockdata = blockdata;
        this.schematicVersion = SchematicVersion.v1_13;
        this.version = version;
    }

    public Schematic(short width, short length, short height, List<Tag> tileEntities, byte[] blockdata, Map<String, Tag> palette, int version) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.palette = palette;
        this.blockdata = blockdata;
        this.schematicVersion = SchematicVersion.v1_13;
        this.tileEntities = tileEntities;
        this.version = version;
    }

    /**
     * @return the blocks
     */
    public byte[] getBlocks() {
        return blocks;
    }

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @return the width
     */
    public short getWidth() {
        return width;
    }

    /**
     * @return the length
     */
    public short getLength() {
        return length;
    }

    /**
     * @return the height
     */
    public short getHeight() {
        return height;
    }

    public void pasteSchematic(Location loc, Island island) {
        short length = getLength();
        short width = getWidth();
        short height = getHeight();
        loc.subtract(width / 2.00, height / 2.00, length / 2.00); // Centers the schematic
        loc.getBlock().setType(Material.STONE, true);//Just incase something fails ?
        if (schematicVersion == SchematicVersion.v_1_8) {

            byte[] blocks = getBlocks();
            byte[] blockData = getData();

            //LoadBlocks
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    for (int z = 0; z < length; ++z) {
                        int index = y * width * length + z * width + x;
                        Block block = new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                        IridiumSkyblock.nms.setBlockFast(block, blocks[index], blockData[index]);
                    }
                }
            }
            //Tile Entities
            for (Tag tag : tileEntities) {
                if (!(tag instanceof CompoundTag))
                    continue;
                CompoundTag t = (CompoundTag) tag;
                Map<String, Tag> tags = t.getValue();

                int x = getChildTag(tags, "x", IntTag.class).getValue();
                int y = getChildTag(tags, "y", IntTag.class).getValue();
                int z = getChildTag(tags, "z", IntTag.class).getValue();
                Block block = new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();

                String id = getChildTag(tags, "id", StringTag.class).getValue().toLowerCase().replace("minecraft:", "");
                if (id.equalsIgnoreCase("chest")) {
                    List<Tag> items = getChildTag(tags, "Items", ListTag.class).getValue();
                    if (block.getState() instanceof Chest) {
                        Chest chest = (Chest) block.getState();
                        for (Tag item : items) {
                            if (!(item instanceof CompoundTag))
                                continue;
                            Map<String, Tag> itemtag = ((CompoundTag) item).getValue();
                            byte slot = getChildTag(itemtag, "Slot", ByteTag.class).getValue();
                            String name = (getChildTag(itemtag, "id", StringTag.class).getValue()).toLowerCase().replace("minecraft:", "");
                            Byte amount = getChildTag(itemtag, "Count", ByteTag.class).getValue();
                            short damage = getChildTag(itemtag, "Damage", ShortTag.class).getValue();
                            XMaterial material = XMaterial.requestOldXMaterial(name.toUpperCase(), (byte) damage);
                            if (material != null) {
                                ItemStack itemStack = material.parseItem(true);
                                if (itemStack != null) {
                                    itemStack.setAmount(amount);
                                    chest.getBlockInventory().setItem(slot, itemStack);
                                }
                            }
                        }
                    }
                } else if (id.equalsIgnoreCase("sign")) {
                    if (block.getState() instanceof Sign) {
                        Sign sign = (Sign) block.getState();
                        JsonParser parser = new JsonParser();
                        String line1 = parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsString().isEmpty() ? "" : parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                        String line2 = parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsString().isEmpty() ? "" : parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                        String line3 = parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsString().isEmpty() ? "" : parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                        String line4 = parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsString().isEmpty() ? "" : parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                        if (!parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsString().isEmpty() && parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                            line1 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line1;
                        if (!parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsString().isEmpty() && parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                            line2 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line2;
                        if (!parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsString().isEmpty() && parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                            line3 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line3;
                        if (!parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsString().isEmpty() && parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                            line4 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line4;
                        sign.setLine(0, line1);
                        sign.setLine(1, line2);
                        sign.setLine(2, line3);
                        sign.setLine(3, line4);
                        sign.update(true);
                    }
                }
            }
        } else {
            //LoadBlocks
            if (XMaterial.ISFLAT) {
                try {
                    for (int x = 0; x < width; ++x) {
                        for (int y = 0; y < height; ++y) {
                            for (int z = 0; z < length; ++z) {
                                int index = y * width * length + z * width + x;
                                Block block = new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                                for (String s : palette.keySet()) {
                                    int i = getChildTag(palette, s, IntTag.class).getValue();
                                    if (blockdata[index] == i) {
                                        block.setBlockData(Bukkit.createBlockData(s), false);
                                    }
                                }
                            }
                        }
                    }
                    if (version == 2) {
                        //Tile Entities
                        for (Tag tag : tileEntities) {
                            if (!(tag instanceof CompoundTag))
                                continue;
                            CompoundTag t = (CompoundTag) tag;
                            Map<String, Tag> tags = t.getValue();

                            int[] pos = getChildTag(tags, "Pos", IntArrayTag.class).getValue();

                            int x = pos[0];
                            int y = pos[1];
                            int z = pos[2];

                            Block block = new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock();
                            String id = getChildTag(tags, "Id", StringTag.class).getValue().toLowerCase().replace("minecraft:", "");
                            if (id.equalsIgnoreCase("chest")) {
                                List<Tag> items = getChildTag(tags, "Items", ListTag.class).getValue();
                                if (block.getState() instanceof Chest) {
                                    Chest chest = (Chest) block.getState();
                                    for (Tag item : items) {
                                        if (!(item instanceof CompoundTag))
                                            continue;
                                        Map<String, Tag> itemtag = ((CompoundTag) item).getValue();
                                        byte slot = getChildTag(itemtag, "Slot", ByteTag.class).getValue();
                                        String name = (getChildTag(itemtag, "id", StringTag.class).getValue()).toLowerCase().replace("minecraft:", "");
                                        Byte amount = getChildTag(itemtag, "Count", ByteTag.class).getValue();
                                        XMaterial material = XMaterial.requestOldXMaterial(name.toUpperCase(), (byte) -1);
                                        if (material != null) {
                                            ItemStack itemStack = material.parseItem(true);
                                            if (itemStack != null) {
                                                itemStack.setAmount(amount);
                                                chest.getBlockInventory().setItem(slot, itemStack);
                                            }
                                        }
                                    }
                                }
                            } else if (id.equalsIgnoreCase("sign")) {
                                if (block.getState() instanceof Sign) {
                                    Sign sign = (Sign) block.getState();
                                    JsonParser parser = new JsonParser();
                                    String line1 = parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                                    String line2 = parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                                    String line3 = parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                                    String line4 = parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsString().replace("[ISLAND_OWNER]", User.getUser(island.getOwner()).name);
                                    if (parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                                        line1 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text1", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line1;
                                    if (parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                                        line2 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text2", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line2;
                                    if (parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                                        line3 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text3", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line3;
                                    if (parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().has("color"))
                                        line4 = ChatColor.valueOf(parser.parse(getChildTag(tags, "Text4", StringTag.class).getValue()).getAsJsonObject().get("extra").getAsJsonArray().get(0).getAsJsonObject().get("color").getAsString().toUpperCase()) + line4;
                                    sign.setLine(0, line1);
                                    sign.setLine(1, line2);
                                    sign.setLine(2, line3);
                                    sign.setLine(3, line4);
                                    sign.update(true);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    IridiumSkyblock.getInstance().sendErrorMessage(e);
                }
            }
        }
    }

    public static void debugSchematic(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        NBTInputStream nbtStream = new NBTInputStream(stream);

        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        stream.close();
        nbtStream.close();
        Map<String, Tag> schematic = schematicTag.getValue();

        for (String s : schematic.keySet()) {
            System.out.println(s + " - " + schematic.get(s));
        }
    }

    public static Schematic loadSchematic(File file) throws IOException {
        if (cache.containsKey(file.getAbsolutePath())) return cache.get(file.getAbsolutePath());
        FileInputStream stream = new FileInputStream(file);
        NBTInputStream nbtStream = new NBTInputStream(stream);

        CompoundTag schematicTag = (CompoundTag) nbtStream.readTag();
        stream.close();
        nbtStream.close();
        Map<String, Tag> schematic = schematicTag.getValue();

        short width = getChildTag(schematic, "Width", ShortTag.class).getValue();
        short length = getChildTag(schematic, "Length", ShortTag.class).getValue();
        short height = getChildTag(schematic, "Height", ShortTag.class).getValue();
        if (!schematic.containsKey("Blocks")) {
            // 1.13 Schematic
            int version = getChildTag(schematic, "Version", IntTag.class).getValue();
            Map<String, Tag> palette = getChildTag(schematic, "Palette", CompoundTag.class).getValue();
            byte[] blockdata = getChildTag(schematic, "BlockData", ByteArrayTag.class).getValue();
            if (version == 1) {
                List<Tag> TileEntities = getChildTag(schematic, "TileEntities", ListTag.class).getValue();
                cache.put(file.getAbsolutePath(), new Schematic(width, length, height, TileEntities, blockdata, palette, version));
                return new Schematic(width, length, height, TileEntities, blockdata, palette, version);
            } else if (version == 2) {
                List<Tag> BlockEntities = getChildTag(schematic, "BlockEntities", ListTag.class).getValue();
                cache.put(file.getAbsolutePath(), new Schematic(width, length, height, blockdata, palette, version));
                return new Schematic(width, length, height, BlockEntities, blockdata, palette, version);
            } else {
                cache.put(file.getAbsolutePath(), new Schematic(width, length, height, blockdata, palette, version));
                return new Schematic(width, length, height, blockdata, palette, version);
            }

        } else {

            List<Tag> TileEntities = getChildTag(schematic, "TileEntities", ListTag.class).getValue();
            String materials = getChildTag(schematic, "Materials", StringTag.class).getValue();
            if (!materials.equals("Alpha")) {
                throw new IllegalArgumentException("Schematic file is not an Alpha schematic");
            }

            byte[] blocks = getChildTag(schematic, "Blocks", ByteArrayTag.class).getValue();
            byte[] blockData = getChildTag(schematic, "Data", ByteArrayTag.class).getValue();
            List<Tag> entities = getChildTag(schematic, "Entities", ListTag.class).getValue();
            cache.put(file.getAbsolutePath(), new Schematic(width, length, height, TileEntities, blocks, blockData, entities));
            return new Schematic(width, length, height, TileEntities, blocks, blockData, entities);
        }
    }

    public static <T extends Tag> T getChildTag(Map<String, Tag> items, String key, Class<T> expected) throws
            IllegalArgumentException {
        if (!items.containsKey(key)) {
            throw new IllegalArgumentException("Schematic file is missing a \"" + key + "\" tag");
        }
        Tag tag = items.get(key);
        if (!expected.isInstance(tag)) {
            throw new IllegalArgumentException(key + " tag is not of tag type " + expected.getName());
        }
        return expected.cast(tag);
    }
}

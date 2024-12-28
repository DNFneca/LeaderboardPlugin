package me.DNFneca.leaderboard.utils.board;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class BoardRow {
    private String id;
    private String srtText;
    private String worldUUID;
    private double x;
    private double y;
    private double z;

    public BoardRow(String text, Location location) {
        ArmorStand nameStand = location.getWorld().spawn(location, ArmorStand.class);
        nameStand.customName(Component.text(text));
        nameStand.setGravity(false);
        nameStand.setInvisible(true);
        nameStand.setCustomNameVisible(true);
        if(text == "") nameStand.setCustomNameVisible(false);
        this.id = nameStand.getUniqueId().toString();
        this.srtText = text;
        worldUUID = location.getWorld().getUID().toString();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public BoardRow() {}

    public String getId() {
        return id;
    }

    public String getText() {
        return srtText;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        ArmorStand row = (ArmorStand) Bukkit.getServer().getEntity(UUID.fromString(id));
        if (row == null) return;
        if(text == "") row.setCustomNameVisible(false);
        else row.setCustomNameVisible(true);
        row.customName(Component.text(text));
        this.srtText = text;
    }

    public Location getLocation() {
        return new Location(Bukkit.getServer().getWorld(UUID.fromString(worldUUID)), x, y, z);
    }

    public void setLocation(Location location) {
        ArmorStand row = (ArmorStand) Bukkit.getServer().getEntity(UUID.fromString(id));
        row.teleport(location);
        worldUUID = location.getWorld().getUID().toString();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public void setWorldUUID(String worldUUID) {
        this.worldUUID = worldUUID;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
        ArmorStand row = (ArmorStand) Bukkit.getServer().getEntity(UUID.fromString(id));
        row.teleport(new Location(Bukkit.getServer().getWorld(UUID.fromString(worldUUID)), x, y, z));
    }

    public double getY() {
        return y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}

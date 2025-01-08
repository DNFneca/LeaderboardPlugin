package me.DNFneca.leaderboard.utils.board;

import me.DNFneca.leaderboard.utils.component.ComponentUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.serializer.ComponentDecoder;
import net.kyori.adventure.text.serializer.ComponentEncoder;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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

    public BoardRow(Component text, Location location) {
        ArmorStand nameStand = location.getWorld().spawn(location, ArmorStand.class);
        nameStand.customName(text);
        nameStand.setGravity(false);
        nameStand.setInvisible(true);
        nameStand.setCustomNameVisible(true);
        if(PlainTextComponentSerializer.plainText().serialize(text).isEmpty()) nameStand.setCustomNameVisible(false);
        this.id = nameStand.getUniqueId().toString();
        this.srtText = ComponentUtils.serializeComponent(text);
        worldUUID = location.getWorld().getUID().toString();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public String getId() {
        return id;
    }

    public Component getText() {
        return ComponentUtils.deserializeComponent(srtText);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(Component text) {
        ArmorStand row = (ArmorStand) Bukkit.getServer().getEntity(UUID.fromString(id));
        if (row == null) return;
        if(PlainTextComponentSerializer.plainText().serialize(text).isEmpty()) row.setCustomNameVisible(false);
        else row.setCustomNameVisible(true);
        row.customName(text);
        this.srtText = ComponentUtils.serializeComponent(text);
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

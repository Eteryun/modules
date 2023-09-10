package com.eteryun.boss;

import com.eteryun.boss.network.client.ClientboundPacketBoss;
import com.eteryun.core.network.PacketsProtocol;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class BossInfo {
    private final List<UUID> players = new CopyOnWriteArrayList<>();
    private String title;
    private int range;
    private String color;
    private String image;
    private double health;
    private double maxHealth;

    public BossInfo(int range, String color, String image) {
        this.range = range;
        this.color = color;
        this.image = image;
        this.health = 0;
        this.maxHealth = 0;
        this.title = "";
    }

    public Collection<UUID> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        this.addPlayer(player.getUniqueId());
    }

    public void addPlayer(UUID uuid) {
        this.players.add(uuid);
        ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createAddPacket(this);
        PacketsProtocol.sendPacket(Bukkit.getPlayer(uuid), clientboundPacketBoss);
    }

    public void removePlayer(Player player) {
        this.removePlayer(player.getUniqueId());
    }

    public void removePlayer(UUID uuid) {
        this.players.remove(uuid);
        ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createRemovePacket();
        PacketsProtocol.sendPacket(Bukkit.getPlayer(uuid), clientboundPacketBoss);
    }

    public void removeAll() {
        this.players.forEach(uuid -> {
            ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createRemovePacket();
            PacketsProtocol.sendPacket(Bukkit.getPlayer(uuid), clientboundPacketBoss);
        });
        this.players.clear();
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        if (health == this.health)
            return;
        this.health = health;
        this.players.forEach(uuid -> {
            ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createUpdateHealth(health, maxHealth);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                PacketsProtocol.sendPacket(player, clientboundPacketBoss);
            else
                this.players.remove(uuid);
        });
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        if (maxHealth == this.maxHealth)
            return;
        this.maxHealth = maxHealth;
        this.players.forEach(uuid -> {
            ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createUpdateHealth(health, maxHealth);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                PacketsProtocol.sendPacket(player, clientboundPacketBoss);
            else
                this.players.remove(uuid);
        });
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == this.title)
            return;
        this.title = title;
        this.players.forEach(uuid -> {
            ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createUpdateTitle(title);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                PacketsProtocol.sendPacket(player, clientboundPacketBoss);
            else
                this.players.remove(uuid);
        });
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color == this.color)
            return;
        this.color = color;
        this.players.forEach(uuid -> {
            ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createUpdateStyle(color, image);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                PacketsProtocol.sendPacket(player, clientboundPacketBoss);
            else
                this.players.remove(uuid);
        });
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image == this.image)
            return;
        this.image = image;
        this.players.forEach(uuid -> {
            ClientboundPacketBoss clientboundPacketBoss = ClientboundPacketBoss.createUpdateStyle(color, image);
            Player player = Bukkit.getPlayer(uuid);
            if (player != null)
                PacketsProtocol.sendPacket(player, clientboundPacketBoss);
            else
                this.players.remove(uuid);
        });
    }

    public boolean isViewing(UUID uuid) {
        return this.players.contains(uuid);
    }
}

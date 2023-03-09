package com.eteryun.skills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerDataUpdateSkillEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private int slot;

    public PlayerDataUpdateSkillEvent(@NotNull Player who) {
        super(who, true);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

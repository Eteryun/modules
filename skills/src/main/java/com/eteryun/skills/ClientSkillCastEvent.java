package com.eteryun.skills;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class ClientSkillCastEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    private int slot;

    public ClientSkillCastEvent(@NotNull Player who, int slot) {
        super(who);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

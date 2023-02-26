package com.eteryun.stats.network.client;

import com.eteryun.core.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;

public class ClientboundPacketPlayerStats implements IPacket {
    private double value;
    private PlayerStats playerStats;

    public ClientboundPacketPlayerStats(double value, PlayerStats playerStats) {
        this.playerStats = playerStats;
        this.value = value;
    }

    public ClientboundPacketPlayerStats(FriendlyByteBuf buffer) {
        this.playerStats = buffer.readEnum(PlayerStats.class);
        this.value = buffer.readDouble();
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeEnum(this.playerStats);
        pBuffer.writeDouble(this.value);
    }

    @Override
    public void handle() {
        // nope
    }

    public enum PlayerStats {
        MANA,
        MAX_MANA
    }
}

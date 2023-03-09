package com.eteryun.stats;

import com.eteryun.core.network.PacketsProtocol;
import com.eteryun.stats.network.client.ClientboundPacketPlayerStats;
import org.bukkit.entity.Player;

public class StatsUtils {
    public static void sendStats(Player player, double value, ClientboundPacketPlayerStats.PlayerStats stat) {
        ClientboundPacketPlayerStats playerStats = new ClientboundPacketPlayerStats(value, stat);
        PacketsProtocol.sendPacket(player, playerStats);
    }
}

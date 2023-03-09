package com.eteryun.stats;

import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import com.eteryun.core.network.PacketsProtocol;
import com.eteryun.stats.network.client.ClientboundPacketPlayerStats;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class StatsModule extends Module {
    public StatsModule(Logger logger, ModuleConfig config, Path path) {
        super(logger, config, path);
    }

    @Override
    public void onLoad() {
        PacketsProtocol.registerPacket(0, PacketFlow.CLIENTBOUND, ClientboundPacketPlayerStats.class, ClientboundPacketPlayerStats::new);
    }
}
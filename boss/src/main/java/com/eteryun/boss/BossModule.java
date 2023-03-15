package com.eteryun.boss;

import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import com.eteryun.boss.network.client.ClientboundPacketBoss;
import com.eteryun.core.network.PacketsProtocol;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class BossModule extends Module {
    public BossModule(Logger logger, ModuleConfig config, Path path) {
        super(logger, config, path);
    }

    @Override
    public void onLoad() {
        PacketsProtocol.registerPacket(3, PacketFlow.CLIENTBOUND, ClientboundPacketBoss.class, ClientboundPacketBoss::new);
    }
}
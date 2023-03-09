package com.eteryun.backtool;

import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import com.eteryun.backtool.network.server.ServerboundPacketPlayerAction;
import com.eteryun.core.network.PacketsProtocol;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public class BacktoolModule extends Module {
    public BacktoolModule(Logger logger, ModuleConfig config, Path path) {
        super(logger, config, path);
    }

    @Override
    public void onLoad() {
        PacketsProtocol.registerPacket(0, PacketFlow.SERVERBOUND, ServerboundPacketPlayerAction.class, ServerboundPacketPlayerAction::new);
    }
}

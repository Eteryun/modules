package com.eteryun.skills;

import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import com.eteryun.core.network.PacketsProtocol;
import com.eteryun.skills.network.client.ClientboundPacketPlayerCastSkill;
import com.eteryun.skills.network.client.ClientboundPacketPlayerSkills;
import com.eteryun.skills.network.server.ServerboundPacketPlayerCastSkill;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.Logger;
import org.bukkit.event.Listener;

import java.nio.file.Path;


public class SkillsModule extends Module implements Listener {
    public SkillsModule(Logger logger, ModuleConfig config, Path path) {
        super(logger, config, path);
    }

    @Override
    public void onLoad() {
        PacketsProtocol.registerPacket(1, PacketFlow.CLIENTBOUND, ClientboundPacketPlayerSkills.class, ClientboundPacketPlayerSkills::new);
        PacketsProtocol.registerPacket(2, PacketFlow.CLIENTBOUND, ClientboundPacketPlayerCastSkill.class, ClientboundPacketPlayerCastSkill::new);

        PacketsProtocol.registerPacket(1, PacketFlow.SERVERBOUND, ServerboundPacketPlayerCastSkill.class, ServerboundPacketPlayerCastSkill::new);
    }
}

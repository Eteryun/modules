package com.eteryun.skills;

import com.eteryun.api.module.Module;
import com.eteryun.api.module.ModuleConfig;
import com.eteryun.core.network.PacketsProtocol;
import com.eteryun.skills.network.client.ClientboundPacketPlayerCastSkill;
import com.eteryun.skills.network.client.ClientboundPacketPlayerSkills;
import com.eteryun.skills.network.server.ServerboundPacketPlayerCastSkill;
import net.minecraft.network.protocol.PacketFlow;
import org.apache.logging.log4j.Logger;
import org.bukkit.entity.Player;

import java.nio.file.Path;
import java.util.ArrayList;

public class SkillsModule extends Module {
    public SkillsModule(Logger logger, ModuleConfig config, Path path) {
        super(logger, config, path);
    }

    @Override
    public void onLoad() {
        PacketsProtocol.registerPacket(PacketFlow.CLIENTBOUND, ClientboundPacketPlayerSkills.class, ClientboundPacketPlayerSkills::new);
        PacketsProtocol.registerPacket(PacketFlow.CLIENTBOUND, ClientboundPacketPlayerCastSkill.class, ClientboundPacketPlayerCastSkill::new);

        PacketsProtocol.registerPacket(PacketFlow.SERVERBOUND, ServerboundPacketPlayerCastSkill.class, ServerboundPacketPlayerCastSkill::new);
    }

    public static void sendPlayerSkills(Player player, ArrayList<ClientboundPacketPlayerSkills.Skill> skills, ArrayList<ClientboundPacketPlayerSkills.Skill> passives) {
        PacketsProtocol.sendPacket(player, new ClientboundPacketPlayerSkills(skills, passives));
    }

    public static void sendPlayerCastSkill(Player player, String id) {
        PacketsProtocol.sendPacket(player, new ClientboundPacketPlayerCastSkill(id));
    }
}

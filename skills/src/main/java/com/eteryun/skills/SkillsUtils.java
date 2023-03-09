package com.eteryun.skills;

import com.eteryun.core.network.PacketsProtocol;
import com.eteryun.skills.network.client.ClientboundPacketPlayerCastSkill;
import com.eteryun.skills.network.client.ClientboundPacketPlayerSkills;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SkillsUtils {
    public static void sendSkills(Player player, ArrayList<ClientboundPacketPlayerSkills.Skill> skills, ArrayList<ClientboundPacketPlayerSkills.Skill> passives) {
        ClientboundPacketPlayerSkills packetPlayerSkills = new ClientboundPacketPlayerSkills(skills, passives);
        PacketsProtocol.sendPacket(player, packetPlayerSkills);
    }

    public  static void sendCastSkill(Player player, String id) {
        ClientboundPacketPlayerCastSkill packetPlayerCastSkill = new ClientboundPacketPlayerCastSkill(id);
        PacketsProtocol.sendPacket(player, packetPlayerCastSkill);
    }
}

package com.eteryun.skills.network.server;

import com.eteryun.core.network.IPacket;
import com.eteryun.skills.events.ClientSkillCastEvent;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.Bukkit;

import java.util.UUID;

public class ServerboundPacketPlayerCastSkill implements IPacket {
    private UUID uuid;
    int slot;

    public ServerboundPacketPlayerCastSkill(UUID uuid, int slot) {
        this.uuid = uuid;
        this.slot = slot;
    }

    public ServerboundPacketPlayerCastSkill(FriendlyByteBuf buffer) {
        this.uuid = buffer.readUUID();
        this.slot = buffer.readInt();
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {

    }

    @Override
    public void handle() {
        ClientSkillCastEvent clientSkillCastEvent = new ClientSkillCastEvent(Bukkit.getPlayer(uuid), slot);
        Bukkit.getPluginManager().callEvent(clientSkillCastEvent);
    }
}

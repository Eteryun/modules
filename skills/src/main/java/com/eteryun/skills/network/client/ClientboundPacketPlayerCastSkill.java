package com.eteryun.skills.network.client;

import com.eteryun.core.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;

public class ClientboundPacketPlayerCastSkill implements IPacket {
    String id;

    public ClientboundPacketPlayerCastSkill(String id) {
        this.id = id;
    }

    public ClientboundPacketPlayerCastSkill(FriendlyByteBuf buffer) {
        this.id =  buffer.readUtf();
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeUtf(this.id);
    }

    @Override
    public void handle() {

    }
}

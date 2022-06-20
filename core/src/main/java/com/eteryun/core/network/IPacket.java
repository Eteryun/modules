package com.eteryun.core.network;

import net.minecraft.network.FriendlyByteBuf;

public interface IPacket {
    void write(FriendlyByteBuf pBuffer);

    void handle();
}

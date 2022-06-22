package com.eteryun.core.mixin.network;

import com.eteryun.core.network.IPacket;
import com.eteryun.core.network.PacketsProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void handleCustomPayload(ServerboundCustomPayloadPacket pPacket, CallbackInfo ci){
        if (pPacket.getIdentifier().equals(new ResourceLocation("eteryun", "packets"))) {
            ci.cancel();

            FriendlyByteBuf byteBuf = pPacket.getData();
            int id = byteBuf.readInt();
            IPacket packet = PacketsProtocol.createPacket(PacketFlow.SERVERBOUND, id, byteBuf);
            if (packet != null) {
                packet.handle();
            }
        }
    }
}

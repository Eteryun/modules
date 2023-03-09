package com.eteryun.core.network;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class PacketsProtocol {
    public static final Map<PacketFlow, PacketSet> flows = new HashMap<>();

    public static <P extends IPacket> void registerPacket(PacketFlow pDirection, Class<P> pPacketClass,
                                      Function<FriendlyByteBuf, P> pDeserializer) {
        PacketSet packetSet = flows.get(pDirection);
        if (packetSet == null)
            packetSet = new PacketSet();

        flows.put(pDirection, packetSet.addPacket(pPacketClass, pDeserializer));
    }

    public static <P extends IPacket> void registerPacket(int id, PacketFlow pDirection, Class<P> pPacketClass,
                                                          Function<FriendlyByteBuf, P> pDeserializer) {
        PacketSet packetSet = flows.get(pDirection);
        if (packetSet == null)
            packetSet = new PacketSet();

        flows.put(pDirection, packetSet.addPacket(id, pPacketClass, pDeserializer));
    }
    @Nullable
    public static IPacket createPacket(PacketFlow pDirection, int pPacketId, FriendlyByteBuf pBuffer){
        return flows.get(pDirection).createPacket(pPacketId, pBuffer);
    }

    @Nullable
    public static Integer getPacketId(PacketFlow pDirection, IPacket pPacket) {
        return flows.get(pDirection).getId(pPacket.getClass());
    }

    public static void sendPacket(Player player, IPacket packet){
        FriendlyByteBuf byteBuf = new FriendlyByteBuf(Unpooled.buffer());

        // write id
        byteBuf.writeInt(getPacketId(PacketFlow.CLIENTBOUND, packet));
        // write custom data
        packet.write(byteBuf);

        ClientboundCustomPayloadPacket customPayloadPacket = new ClientboundCustomPayloadPacket(new ResourceLocation("eteryun", "packets"), byteBuf);
        ((CraftPlayer) player).getHandle().connection.send(customPayloadPacket);
    }

    static class PacketSet {
        final Object2IntMap<Class<? extends IPacket>> classToId = make(new Object2IntOpenHashMap<>(),
                (map) -> {
                    map.defaultReturnValue(-1);
                });
        private final List<Function<FriendlyByteBuf, ? extends IPacket>> idToDeserializer = Lists.newArrayList();

        public <P extends IPacket> PacketSet addPacket(Class<P> pPacketClass,
                                                      Function<FriendlyByteBuf, P> pDeserializer) {
            int i = this.idToDeserializer.size();
            int j = this.classToId.put(pPacketClass, i);

            if (j != -1) {
                String s = "Packet " + pPacketClass + " is already registered to ID " + j;
                throw new IllegalArgumentException(s);
            } else {
                this.idToDeserializer.add(pDeserializer);
                return this;
            }
        }

        public <P extends IPacket> PacketSet addPacket(int id, Class<P> pPacketClass,
                                                       Function<FriendlyByteBuf, P> pDeserializer) {
            int j = this.classToId.put(pPacketClass, id);

            if (j != -1) {
                String s = "Packet " + pPacketClass + " is already registered to ID " + j;
                throw new IllegalArgumentException(s);
            } else {
                this.idToDeserializer.add(pDeserializer);
                return this;
            }
        }

        @Nullable
        public Integer getId(Class<?> pPacketClass) {
            int i = this.classToId.getInt(pPacketClass);
            return i == -1 ? null : i;
        }

        @Nullable
        public IPacket createPacket(int pPacketId, FriendlyByteBuf pBuffer) {
            Function<FriendlyByteBuf, ? extends IPacket> function = this.idToDeserializer.get(pPacketId);
            return function != null ? function.apply(pBuffer) : null;
        }

        public Iterable<Class<? extends IPacket>> getAllPackets() {
            return Iterables.unmodifiableIterable(this.classToId.keySet());
        }
    }

    static class ProtocolBuilder {
        final Map<PacketFlow, PacketSet> flows = Maps.newEnumMap(PacketFlow.class);

        public ProtocolBuilder addFlow(PacketFlow pPacketFlow, PacketSet pPacketSet) {
            this.flows.put(pPacketFlow, pPacketSet);
            return this;
        }
    }

    private static ProtocolBuilder protocol() {
        return new ProtocolBuilder();
    }

    static <T> T make(T pObject, Consumer<T> pConsumer) {
        pConsumer.accept(pObject);
        return pObject;
    }
}

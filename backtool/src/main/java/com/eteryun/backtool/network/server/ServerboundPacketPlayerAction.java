package com.eteryun.backtool.network.server;

import com.eteryun.core.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;

import java.util.UUID;

public class ServerboundPacketPlayerAction implements IPacket {
    private UUID uuid;
    private Action action;

    public ServerboundPacketPlayerAction(UUID uuid, Action action) {
        this.uuid = uuid;
        this.action = action;
    }

    public ServerboundPacketPlayerAction(FriendlyByteBuf buffer) {
        this.uuid = buffer.readUUID();
        this.action = buffer.readEnum(Action.class);
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        // nope
    }

    @Override
    public void handle() {
        switch (action) {
            case SWAP_BACKTOOL -> {
                CraftPlayer craftPlayer = (CraftPlayer) Bukkit.getPlayer(uuid);
                Player player = craftPlayer.getHandle();
                ItemStack mainHand = player.getMainHandItem();
                ItemStack backTool = player.getItemBySlot(EquipmentSlot.valueOf("BACKTOOL"));
                if (mainHand.getItem() instanceof SwordItem || mainHand.getItem() instanceof DiggerItem ||
                        mainHand.getItem() instanceof ProjectileWeaponItem || mainHand.getItem() instanceof AirItem) {
                    player.setItemSlot(EquipmentSlot.valueOf("BACKTOOL"), mainHand);
                    player.setItemSlot(EquipmentSlot.MAINHAND, backTool);
                }
                break;
            }
        }
    }

    public static enum Action {
        SWAP_BACKTOOL
    }
}

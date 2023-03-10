package com.eteryun.skills.network.server;

import com.eteryun.core.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.PluginClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
        try {
            Class<?> playerDataClass = getClass("MMOCore", "net.Indyuce.mmocore.api.player.PlayerData");
            Object playerData = playerDataClass.getMethod("get", UUID.class).invoke(null, uuid);
            Object classSkill = playerDataClass.getMethod("getBoundSkill", int.class).invoke(playerData, slot);
            if (classSkill != null) {
                Object mmoPlayerData = playerDataClass.getMethod("getMMOPlayerData").invoke(playerData);
                Class<?> mmoPlayerDataClass = mmoPlayerData.getClass();
                Object statMap = mmoPlayerDataClass.getMethod("getStatMap").invoke(mmoPlayerData);

                Class<?> equipmentSlotClass = getClass("MythicLib", "io.lumine.mythic.lib.api.player.EquipmentSlot");
                Object mainHandEquipmentSlot = equipmentSlotClass.getDeclaredField("MAIN_HAND").get(null);
                Class<?> statMapClass = statMap.getClass();
                Object playerMetaData = statMapClass.getMethod("cache", equipmentSlotClass).invoke(statMap, mainHandEquipmentSlot);

                Class<?> triggerClass = getClass("MythicLib", "io.lumine.mythic.lib.skill.trigger.TriggerMetadata");
                Constructor triggerConstructor = null;
                for (Constructor constructor : triggerClass.getConstructors()) {
                    if (constructor.getParameterCount() == 3) {
                        triggerConstructor = constructor;
                        break;
                    }
                }
                if (triggerConstructor != null) {
                    Object triggerMetadata = triggerConstructor.newInstance(playerMetaData, null, null);
                    Class<?> classSkillClass = classSkill.getClass();
                    Object castableSkill = classSkillClass.getMethod("toCastable", playerDataClass).invoke(classSkill, playerData);
                    Class<?> castableSkillClass = castableSkill.getClass();
                    castableSkillClass.getMethod("cast", triggerClass).invoke(castableSkill, triggerMetadata);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> getClass(String owner, String className) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(owner);
        PluginClassLoader classLoader = (PluginClassLoader) plugin.getClass().getClassLoader();
        Method findClass = classLoader.getClass().getDeclaredMethod("findClass", String.class);
        findClass.setAccessible(true);

        return (Class<?>) findClass.invoke(classLoader, className);
    }
}

package com.eteryun.skills.network.client;

import com.eteryun.core.network.IPacket;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;

public class ClientboundPacketPlayerSkills implements IPacket {
    private ArrayList<Skill> skills;
    private ArrayList<Skill> passives;

    public ClientboundPacketPlayerSkills(ArrayList<Skill> skills, ArrayList<Skill> passives) {
        this.skills = skills;
        this.passives = passives;
    }

    public ClientboundPacketPlayerSkills(FriendlyByteBuf buffer) {
        this.skills = new ArrayList<>();
        this.passives = new ArrayList<>();
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeInt(this.skills.size());
        for (Skill skill : this.skills) {
            pBuffer.writeUtf(skill.getId());
            pBuffer.writeUtf(skill.getIcon());
            pBuffer.writeDouble(skill.getCost());
            pBuffer.writeDouble(skill.getCooldown());
            pBuffer.writeInt(skill.getSlot());
        }
        pBuffer.writeInt(this.passives.size());
        for (Skill passive : this.passives) {
            pBuffer.writeUtf(passive.getId());
            pBuffer.writeUtf(passive.getIcon());
            pBuffer.writeDouble(passive.getCooldown());
        }
    }

    @Override
    public void handle() {

    }

    public class Skill {
        private String id;
        private String icon;
        private double cost;
        private double cooldown;
        private int slot;

        public Skill(String id, String icon, double cost, double cooldown, int slot) {
            this.id = id;
            this.icon = icon;
            this.cost = cost;
            this.cooldown = cooldown;
            this.slot = slot;
        }

        public Skill(String id, String icon, double cooldown) {
            this.id = id;
            this.icon = icon;
            this.cost = 0;
            this.cooldown = cooldown;
            this.slot = -1;
        }

        public String getId() {
            return id;
        }

        public String getIcon() {
            return icon;
        }

        public double getCost() {
            return cost;
        }

        public double getCooldown() {
            return cooldown;
        }

        public int getSlot() {
            return slot;
        }
    }
}

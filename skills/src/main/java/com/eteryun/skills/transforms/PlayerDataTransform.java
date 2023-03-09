package com.eteryun.skills.transforms;

import com.eteryun.api.asm.ITransform;
import org.objectweb.asm.*;

import java.util.List;

import static org.objectweb.asm.Opcodes.*;

public class PlayerDataTransform implements ITransform {
    @Override
    public String getTarget() {
        return "net/Indyuce/mmocore/api/player/PlayerData";
    }

    @Override
    public byte[] transform(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        reader.accept(new PlayerDataClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public static final class PlayerDataClassVisitor extends ClassVisitor {
        public PlayerDataClassVisitor(final ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            final MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
            List<String> names = List.of(new String[]{"unbindSkill", "bindActiveSkill", "unbindPassiveSkill", "bindPassiveSkill", "setFullyLoaded"});
            return names.contains(name) ? new PlayerDataMethodVisitor(mv) : mv;
        }

        public static final class PlayerDataMethodVisitor extends MethodVisitor {

            public PlayerDataMethodVisitor(MethodVisitor visitor) {
                super(Opcodes.ASM9, visitor);
            }

            public void visitInsn(int opcode) {
                if (opcode == RETURN) {
                    mv.visitMethodInsn(INVOKESTATIC, "org/bukkit/Bukkit", "getPluginManager", "()Lorg/bukkit/plugin/PluginManager;", false);
                    mv.visitTypeInsn(NEW, "com/eteryun/skills/events/PlayerDataUpdateSkillEvent");
                    mv.visitInsn(DUP);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getPlayer", "()Lorg/bukkit/entity/Player;", false);
                    mv.visitMethodInsn(INVOKESPECIAL, "com/eteryun/skills/events/PlayerDataUpdateSkillEvent", "<init>", "(Lorg/bukkit/entity/Player;)V", false);
                    mv.visitMethodInsn(INVOKEINTERFACE, "org/bukkit/plugin/PluginManager", "callEvent", "(Lorg/bukkit/event/Event;)V", true);
                }
                super.visitInsn(opcode);
            }
        }
    }
}

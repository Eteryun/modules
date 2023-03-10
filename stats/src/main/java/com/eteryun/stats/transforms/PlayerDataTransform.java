package com.eteryun.stats.transforms;

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
        public void visitEnd() {
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "updateEteryunMana", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getStats", "()Lnet/Indyuce/mmocore/api/player/stats/PlayerStats;", false);
            mv.visitLdcInsn("MAX_MANA");
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/stats/PlayerStats", "getStat", "(Ljava/lang/String;)D", false);
            mv.visitVarInsn(DSTORE, 4);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getMana", "()D", false);
            mv.visitVarInsn(DSTORE, 6);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getPlayer", "()Lorg/bukkit/entity/Player;", false);
            mv.visitVarInsn(ASTORE, 3);

            // 4 = MAX_MANA, 6 = MANA, 3 = PLAYER

            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(DLOAD, 4);
            mv.visitFieldInsn(GETSTATIC, "com/eteryun/stats/network/client/ClientboundPacketPlayerStats$PlayerStats", "MAX_MANA", "Lcom/eteryun/stats/network/client/ClientboundPacketPlayerStats$PlayerStats;");
            mv.visitMethodInsn(INVOKESTATIC, "com/eteryun/stats/StatsUtils", "sendStats", "(Lorg/bukkit/entity/Player;DLcom/eteryun/stats/network/client/ClientboundPacketPlayerStats$PlayerStats;)V", false);

            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(DLOAD, 6);
            mv.visitFieldInsn(GETSTATIC, "com/eteryun/stats/network/client/ClientboundPacketPlayerStats$PlayerStats", "MANA", "Lcom/eteryun/stats/network/client/ClientboundPacketPlayerStats$PlayerStats;");
            mv.visitMethodInsn(INVOKESTATIC, "com/eteryun/stats/StatsUtils", "sendStats", "(Lorg/bukkit/entity/Player;DLcom/eteryun/stats/network/client/ClientboundPacketPlayerStats$PlayerStats;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(4, 4);
            mv.visitEnd();
            super.visitEnd();
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            final MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
            List<String> names = List.of(new String[]{"giveMana", "setFullyLoaded"});
            return names.contains(name) ? new PlayerDataMethodVisitor(mv) : mv;
        }

        public static final class PlayerDataMethodVisitor extends MethodVisitor {

            public PlayerDataMethodVisitor(MethodVisitor visitor) {
                super(Opcodes.ASM9, visitor);
            }

            public void visitInsn(int opcode) {
                if (opcode == RETURN) {
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "updateEteryunMana", "()V", false);
                }
                super.visitInsn(opcode);
            }
        }
    }
}

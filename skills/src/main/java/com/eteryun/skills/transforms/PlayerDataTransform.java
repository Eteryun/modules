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
        public void visitEnd() {
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "updateEteryunSkills", "()V", null, null);
            mv.visitCode();

            mv.visitTypeInsn(NEW, "java/util/ArrayList");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
            mv.visitVarInsn(ASTORE, 1);

            mv.visitTypeInsn(NEW, "java/util/ArrayList");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
            mv.visitVarInsn(ASTORE, 2);

            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 3);

            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_APPEND, 3, new Object[]{"java/util/ArrayList", "java/util/ArrayList", Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getBoundSkills", "()Ljava/util/List;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            Label l3 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l3);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(18, l4);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getBoundSkill", "(I)Lnet/Indyuce/mmocore/skill/ClassSkill;", false);
            mv.visitVarInsn(ASTORE, 4);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitVarInsn(ALOAD, 4);
            Label l6 = new Label();
            mv.visitJumpInsn(IFNONNULL, l6);
            Label l7 = new Label();
            mv.visitJumpInsn(GOTO, l7);
            mv.visitLabel(l6);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"net/Indyuce/mmocore/skill/ClassSkill"}, 0, null);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/ClassSkill", "toEteryunSkill", "(Lnet/Indyuce/mmocore/api/player/PlayerData;I)Lcom/eteryun/skills/network/client/ClientboundPacketPlayerSkills$Skill;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
            mv.visitInsn(POP);
            mv.visitLabel(l7);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitIincInsn(3, 1);
            mv.visitJumpInsn(GOTO, l2);
            mv.visitLabel(l3);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            
            mv.visitInsn(ICONST_0);
            mv.visitVarInsn(ISTORE, 3);
            Label l8 = new Label();
            mv.visitLabel(l8);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getBoundPassiveSkills", "()Ljava/util/List;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "size", "()I", true);
            Label l9 = new Label();
            mv.visitJumpInsn(IF_ICMPGE, l9);
            Label l10 = new Label();
            mv.visitLabel(l10);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getBoundPassiveSkill", "(I)Lio/lumine/mythic/lib/player/skill/PassiveSkill;", false);
            mv.visitVarInsn(ASTORE, 4);
            Label l11 = new Label();
            mv.visitLabel(l11);
            mv.visitVarInsn(ALOAD, 4);
            Label l12 = new Label();
            mv.visitJumpInsn(IFNONNULL, l12);
            Label l13 = new Label();
            mv.visitJumpInsn(GOTO, l13);
            mv.visitLabel(l12);
            mv.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"io/lumine/mythic/lib/player/skill/PassiveSkill"}, 0, null);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/player/skill/PassiveSkill", "getTriggeredSkill", "()Lio/lumine/mythic/lib/skill/Skill;", false);
            mv.visitTypeInsn(CHECKCAST, "net/Indyuce/mmocore/skill/CastableSkill");
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/CastableSkill", "getSkill", "()Lnet/Indyuce/mmocore/skill/ClassSkill;", false);
            mv.visitVarInsn(ASTORE, 5);
            Label l14 = new Label();
            mv.visitLabel(l14);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/ClassSkill", "toEteryunSkill", "(Lnet/Indyuce/mmocore/api/player/PlayerData;I)Lcom/eteryun/skills/network/client/ClientboundPacketPlayerSkills$Skill;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
            mv.visitInsn(POP);
            mv.visitLabel(l13);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitIincInsn(3, 1);
            mv.visitJumpInsn(GOTO, l8);
            mv.visitLabel(l9);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "getPlayer", "()Lorg/bukkit/entity/Player;", false);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitMethodInsn(INVOKESTATIC, "com/eteryun/skills/SkillsUtils", "sendSkills", "(Lorg/bukkit/entity/Player;Ljava/util/ArrayList;Ljava/util/ArrayList;)V", false);

            mv.visitInsn(RETURN);
            mv.visitMaxs(4, 6);
            mv.visitEnd();
            super.visitEnd();
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
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/api/player/PlayerData", "updateEteryunSkills", "()V", false);
                }
                super.visitInsn(opcode);
            }
        }
    }
}

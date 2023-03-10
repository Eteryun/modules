package com.eteryun.skills.transforms;

import com.eteryun.api.asm.ITransform;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class ClassSkillTransform implements ITransform {
    @Override
    public String getTarget() {
        return "net/Indyuce/mmocore/skill/ClassSkill";
    }

    @Override
    public byte[] transform(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        reader.accept(new ClassSkillClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public static final class ClassSkillClassVisitor extends ClassVisitor {
        public ClassSkillClassVisitor(final ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public void visitEnd() {
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "toEteryunSkill", "(Lnet/Indyuce/mmocore/api/player/PlayerData;I)Lcom/eteryun/skills/network/client/ClientboundPacketPlayerSkills$Skill;", null, null);
            mv.visitCode();
            // classSkill
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/ClassSkill", "toCastable", "(Lnet/Indyuce/mmocore/api/player/PlayerData;)Lnet/Indyuce/mmocore/skill/CastableSkill;", false);
            mv.visitVarInsn(ASTORE, 3);

            // id
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/ClassSkill", "getSkill", "()Lnet/Indyuce/mmocore/skill/RegisteredSkill;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/RegisteredSkill", "getHandler", "()Lio/lumine/mythic/lib/skill/handler/SkillHandler;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/skill/handler/SkillHandler", "getLowerCaseId", "()Ljava/lang/String;", false);
            mv.visitVarInsn(ASTORE, 4);

            // image
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/ClassSkill", "getSkill", "()Lnet/Indyuce/mmocore/skill/RegisteredSkill;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/RegisteredSkill", "getImage", "()Ljava/lang/String;", false);
            mv.visitVarInsn(ASTORE, 5);

            // mana
            mv.visitVarInsn(ALOAD, 3);
            mv.visitLdcInsn("mana");
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/CastableSkill", "getModifier", "(Ljava/lang/String;)D", false);
            mv.visitVarInsn(DSTORE, 6);

            // cooldown
            mv.visitVarInsn(ALOAD, 3);
            mv.visitLdcInsn("cooldown");
            mv.visitMethodInsn(INVOKEVIRTUAL, "net/Indyuce/mmocore/skill/CastableSkill", "getModifier", "(Ljava/lang/String;)D", false);
            mv.visitLdcInsn(Double.valueOf(1000));
            mv.visitInsn(DMUL);
            mv.visitVarInsn(DSTORE, 8);

            // instance Skill
            mv.visitTypeInsn(NEW, "com/eteryun/skills/network/client/ClientboundPacketPlayerSkills$Skill");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitVarInsn(DLOAD, 6);
            mv.visitVarInsn(DLOAD, 8);
            mv.visitVarInsn(ILOAD, 2);
            mv.visitMethodInsn(INVOKESPECIAL, "com/eteryun/skills/network/client/ClientboundPacketPlayerSkills$Skill", "<init>", "(Ljava/lang/String;Ljava/lang/String;DDI)V", false);
            mv.visitInsn(ARETURN);

            mv.visitInsn(ARETURN);
            mv.visitMaxs(9, 10);
            mv.visitEnd();
            super.visitEnd();
        }
    }
}

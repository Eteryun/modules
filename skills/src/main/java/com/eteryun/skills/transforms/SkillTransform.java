package com.eteryun.skills.transforms;

import com.eteryun.api.asm.ITransform;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class SkillTransform  implements ITransform {
    @Override
    public String getTarget() {
        return "io/lumine/mythic/lib/skill/Skill";
    }

    @Override
    public byte[] transform(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        reader.accept(new SkillClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public static final class SkillClassVisitor extends ClassVisitor {
        public SkillClassVisitor(final ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            final MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
            return name.equals("castInstantly") ? new SkillMethodVisitor(mv) : mv;
        }

        public static final class SkillMethodVisitor extends MethodVisitor {

            public SkillMethodVisitor(MethodVisitor visitor) {
                super(Opcodes.ASM9, visitor);
            }

            public void visitInsn(int opcode) {
                if (opcode == RETURN) {
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/skill/SkillMetadata", "getCaster", "()Lio/lumine/mythic/lib/player/PlayerMetadata;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/player/PlayerMetadata", "getData", "()Lio/lumine/mythic/lib/api/player/MMOPlayerData;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/api/player/MMOPlayerData", "getCooldownMap", "()Lio/lumine/mythic/lib/player/cooldown/CooldownMap;", false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/skill/SkillMetadata", "getCast", "()Lio/lumine/mythic/lib/skill/Skill;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/player/cooldown/CooldownMap", "isOnCooldown", "(Lio/lumine/mythic/lib/player/cooldown/CooldownObject;)Z", false);

                    Label label1 = new Label();
                    mv.visitJumpInsn(IFEQ, label1);

                    Label label2 = new Label();
                    mv.visitLabel(label2);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/skill/SkillMetadata", "getCaster", "()Lio/lumine/mythic/lib/player/PlayerMetadata;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/player/PlayerMetadata", "getPlayer", "()Lorg/bukkit/entity/Player;", false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/skill/SkillMetadata", "getCast", "()Lio/lumine/mythic/lib/skill/Skill;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/skill/Skill", "getHandler", "()Lio/lumine/mythic/lib/skill/handler/SkillHandler;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/lib/skill/handler/SkillHandler", "getLowerCaseId", "()Ljava/lang/String;", false);
                    mv.visitMethodInsn(INVOKESTATIC, "com/eteryun/skills/SkillsUtils", "sendCastSkill", "(Lorg/bukkit/entity/Player;Ljava/lang/String;)V", false);
                    mv.visitLabel(label1);
                    mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                }
                super.visitInsn(opcode);
            }
        }
    }
}

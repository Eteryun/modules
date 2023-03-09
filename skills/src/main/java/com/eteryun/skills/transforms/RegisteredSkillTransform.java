package com.eteryun.skills.transforms;

import com.eteryun.api.asm.ITransform;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class RegisteredSkillTransform implements ITransform {
    @Override
    public String getTarget() {
        return "net/Indyuce/mmocore/skill/RegisteredSkill";
    }

    @Override
    public byte[] transform(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        reader.accept(new RegisteredSkillClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public static final class RegisteredSkillClassVisitor extends ClassVisitor {
        public RegisteredSkillClassVisitor(final ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            final MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
            return name.contains("<init>") ? new RegisteredSkillMethodVisitor(descriptor, mv) : mv;
        }

        @Override
        public void visitEnd() {
            FieldVisitor fv = super.visitField(ACC_PRIVATE, "image", "Ljava/lang/String;", null, null);
            fv.visitEnd();

            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "getImage", "()Ljava/lang/String;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "net/Indyuce/mmocore/skill/RegisteredSkill", "image", "Ljava/lang/String;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
            super.visitEnd();
        }

        public static final class RegisteredSkillMethodVisitor extends MethodVisitor {
            private final String defaultImage = "https://assets.eteryun.com.br/images/skills/default.png";
            private final String descriptor;

            public RegisteredSkillMethodVisitor(String descriptor, MethodVisitor visitor) {
                super(Opcodes.ASM9, visitor);
                this.descriptor = descriptor;
            }

            @Override
            public void visitInsn(int opcode) {
                if (opcode == RETURN) {
                    if (descriptor.equals("(Lio/lumine/mythic/lib/skill/handler/SkillHandler;Lorg/bukkit/configuration/ConfigurationSection;)V")){
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitLdcInsn("image");
                        mv.visitLdcInsn(defaultImage);
                        mv.visitMethodInsn(INVOKEINTERFACE, "org/bukkit/configuration/ConfigurationSection", "getString", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", true);
                        mv.visitFieldInsn(PUTFIELD, "net/Indyuce/mmocore/skill/RegisteredSkill", "image", "Ljava/lang/String;");
                    } else if (descriptor.equals("(Lio/lumine/mythic/lib/skill/handler/SkillHandler;Ljava/lang/String;Lorg/bukkit/inventory/ItemStack;Ljava/util/List;Lio/lumine/mythic/lib/skill/trigger/TriggerType;)V"))  {
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitLdcInsn(defaultImage);
                        mv.visitFieldInsn(PUTFIELD, "net/Indyuce/mmocore/skill/RegisteredSkill", "image", "Ljava/lang/String;");
                    }
                }
                super.visitInsn(opcode);
            }
        }
    }
}

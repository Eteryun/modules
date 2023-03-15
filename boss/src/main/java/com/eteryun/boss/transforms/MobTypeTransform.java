package com.eteryun.boss.transforms;

import com.eteryun.api.asm.ITransform;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class MobTypeTransform implements ITransform {
    @Override
    public String getTarget() {
        return "io/lumine/mythic/core/mobs/MobType";
    }

    @Override
    public byte[] transform(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        reader.accept(new MobTypeClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public static final class MobTypeClassVisitor extends ClassVisitor {
        public MobTypeClassVisitor(final ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            final MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
            return name.equals("<init>") ? new MobTypeMethodVisitor(mv) : mv;
        }

        @Override
        public void visitEnd() {
            // BossInfo Fields
            FieldVisitor fv = super.visitField(ACC_PROTECTED, "useBossInfo", "Z", null, null);
            fv.visitEnd();
            fv = super.visitField(ACC_PROTECTED, "bossInfoTitle", "Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;", null, null);
            fv.visitEnd();
            fv = super.visitField(ACC_PROTECTED, "bossInfoRange", "I", null, null);
            fv.visitEnd();
            fv = super.visitField(ACC_PROTECTED, "bossInfoRangSq", "I", null, null);
            fv.visitEnd();
            fv = super.visitField(ACC_PROTECTED, "bossInfoColor", "Ljava/lang/String;", null, null);
            fv.visitEnd();
            fv = super.visitField(ACC_PROTECTED, "bossInfoImage", "Ljava/lang/String;", null, null);
            fv.visitEnd();

            // useBossInfo Method
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "usesBossInfo", "()Z", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "useBossInfo", "Z");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();

            // getBossBarRangeSquared Method
            mv = super.visitMethod(ACC_PUBLIC, "getBossInfoRangeSquared", "()I", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoRangSq", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();

            // getBossInfoTitle Method
            mv = super.visitMethod(ACC_PUBLIC, "getBossInfoTitle", "()Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoTitle", "Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;");
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();

            // getBossInfo Method
            mv = super.visitMethod(ACC_PUBLIC, "getBossInfo", "()Ljava/util/Optional;", "()Ljava/util/Optional<Lcom/eteryun/boss/BossInfo;>;", null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "useBossInfo", "Z");
            Label label1 = new Label();
            mv.visitJumpInsn(IFNE, label1);
            Label label2 = new Label();
            mv.visitLabel(label2);
            mv.visitMethodInsn(INVOKESTATIC, "java/util/Optional", "empty", "()Ljava/util/Optional;", false);
            mv.visitInsn(ARETURN);

            mv.visitLabel(label1);
            mv.visitTypeInsn(NEW, "com/eteryun/boss/BossInfo");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoRange", "I");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoColor", "Ljava/lang/String;");
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoImage", "Ljava/lang/String;");
            mv.visitMethodInsn(INVOKESPECIAL, "com/eteryun/boss/BossInfo", "<init>", "(ILjava/lang/String;Ljava/lang/String;)V", false);
            mv.visitVarInsn(ASTORE, 1);

            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKESTATIC, "java/util/Optional", "of", "(Ljava/lang/Object;)Ljava/util/Optional;", false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(3, 2);
            mv.visitEnd();

            super.visitEnd();
        }

        public static final class MobTypeMethodVisitor extends MethodVisitor {

            public MobTypeMethodVisitor(MethodVisitor visitor) {
                super(Opcodes.ASM9, visitor);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                super.visitFieldInsn(opcode, owner, name, descriptor);

                if (name.equals("bossBarTitle") && opcode == PUTFIELD) {
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 5);
                    mv.visitLdcInsn("BossInfo.Enabled");
                    mv.visitInsn(ICONST_0);
                    mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/config/MythicConfig", "getBoolean", "(Ljava/lang/String;Z)Z", true);
                    mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/MobType", "useBossInfo", "Z");

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 5);
                    mv.visitLdcInsn("BossInfo.Title");
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "displayName", "Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;");
                    Label l1 = new Label();
                    mv.visitJumpInsn(IFNONNULL, l1);
                    mv.visitLdcInsn("");
                    Label l2 = new Label();
                    mv.visitJumpInsn(GOTO, l2);
                    mv.visitLabel(l1);
                    mv.visitFrame(Opcodes.F_FULL, 12, new Object[] {"io/lumine/mythic/core/mobs/MobType", "io/lumine/mythic/core/mobs/MobExecutor", "io/lumine/mythic/api/packs/Pack", "java/io/File", "java/lang/String", "io/lumine/mythic/api/config/MythicConfig", "java/lang/String", "java/lang/String", "java/lang/String", Opcodes.INTEGER, "java/lang/String", Opcodes.INTEGER}, 3, new Object[] {"io/lumine/mythic/core/mobs/MobType", "io/lumine/mythic/api/config/MythicConfig", "java/lang/String"});
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "displayName", "Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;");
                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;", false);
                    mv.visitLabel(l2);
                    mv.visitFrame(Opcodes.F_FULL, 12, new Object[] {"io/lumine/mythic/core/mobs/MobType", "io/lumine/mythic/core/mobs/MobExecutor", "io/lumine/mythic/api/packs/Pack", "java/io/File", "java/lang/String", "io/lumine/mythic/api/config/MythicConfig", "java/lang/String", "java/lang/String", "java/lang/String", Opcodes.INTEGER, "java/lang/String", Opcodes.INTEGER}, 4, new Object[] {"io/lumine/mythic/core/mobs/MobType", "io/lumine/mythic/api/config/MythicConfig", "java/lang/String", "java/lang/String"});
                    mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/config/MythicConfig", "getPlaceholderString", "(Ljava/lang/String;Ljava/lang/String;)Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;", true);
                    mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoTitle", "Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;");

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 5);
                    mv.visitLdcInsn("BossInfo.Color");
                    mv.visitLdcInsn("#8b221a");
                    mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/config/MythicConfig", "getString", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", true);
                    mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoColor", "Ljava/lang/String;");

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 5);
                    mv.visitLdcInsn("BossInfo.Image");
                    mv.visitLdcInsn("https://minotar.net/cube/steve");
                    mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/config/MythicConfig", "getString", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", true);
                    mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoImage", "Ljava/lang/String;");

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 5);
                    mv.visitLdcInsn("BossInfo.Range");
                    mv.visitIntInsn(BIPUSH, 64);
                    mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/config/MythicConfig", "getInteger", "(Ljava/lang/String;I)I", true);
                    mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoRange", "I");

                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoRange", "I");
                    mv.visitInsn(I2D);
                    mv.visitLdcInsn(Double.valueOf(2));
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
                    mv.visitInsn(D2I);
                    mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/MobType", "bossInfoRangSq", "I");
                }
            }
        }
    }
}

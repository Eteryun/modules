package com.eteryun.boss.transforms;

import com.eteryun.api.asm.ITransform;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class MythicMobTransform implements ITransform {
    @Override
    public String getTarget() {
        return "io/lumine/mythic/api/mobs/MythicMob";
    }

    @Override
    public byte[] transform(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        reader.accept(new MythicMobClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public static final class MythicMobClassVisitor extends ClassVisitor {
        public MythicMobClassVisitor(final ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public void visitEnd() {
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC | ACC_ABSTRACT, "usesBossInfo", "()Z", null, null);
            mv.visitEnd();

            mv = super.visitMethod(ACC_PUBLIC | ACC_ABSTRACT, "getBossInfoRangeSquared", "()I", null, null);
            mv.visitEnd();

            mv = super.visitMethod(ACC_PUBLIC | ACC_ABSTRACT, "getBossInfo", "()Ljava/util/Optional;", "()Ljava/util/Optional<Lcom/eteryun/boss/BossInfo;>;", null);
            mv.visitEnd();

            mv = super.visitMethod(ACC_PUBLIC | ACC_ABSTRACT, "getBossInfoTitle", "()Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;", null, null);
            mv.visitEnd();

            super.visitEnd();
        }
    }
}

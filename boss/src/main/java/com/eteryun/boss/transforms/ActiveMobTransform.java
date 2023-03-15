package com.eteryun.boss.transforms;

import com.eteryun.api.asm.ITransform;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.*;

public class ActiveMobTransform implements ITransform {
    @Override
    public String getTarget() {
        return "io/lumine/mythic/core/mobs/ActiveMob";
    }

    @Override
    public byte[] transform(byte[] bytes) {
        final ClassReader reader = new ClassReader(bytes);
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        reader.accept(new ActiveMobClassVisitor(writer), ClassReader.EXPAND_FRAMES);
        return writer.toByteArray();
    }

    public static final class ActiveMobClassVisitor extends ClassVisitor {
        public ActiveMobClassVisitor(final ClassVisitor visitor) {
            super(Opcodes.ASM9, visitor);
        }

        @Override
        public void visitEnd() {
            FieldVisitor fv = super.visitField(ACC_PROTECTED | ACC_TRANSIENT, "bossInfo", "Ljava/util/Optional;", "Ljava/util/Optional<Lcom/eteryun/boss/BossInfo;>;", null);
            fv.visitEnd();

            // unloadBossInfo Method
            MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "unloadBossInfo", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "bossInfo", "Ljava/util/Optional;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Optional", "isPresent", "()Z", false);
            Label label1 = new Label();
            mv.visitJumpInsn(IFEQ, label1);
            Label label2 = new Label();
            mv.visitLabel(label2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "bossInfo", "Ljava/util/Optional;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Optional", "get", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, "com/eteryun/boss/BossInfo");
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "removeAll", "()V", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESTATIC, "java/util/Optional", "empty", "()Ljava/util/Optional;", false);
            mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "bossInfo", "Ljava/util/Optional;");
            mv.visitLabel(label1);
            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 2);
            mv.visitEnd();

            // updateBossInfo Method
            mv = super.visitMethod(ACC_PUBLIC, "updateBossInfo", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "bossInfo", "Ljava/util/Optional;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Optional", "isPresent", "()Z", false);
            Label label3 = new Label();
            mv.visitJumpInsn(IFNE, label3);
            Label label4 = new Label();
            mv.visitLabel(label4);
            mv.visitInsn(RETURN);
            mv.visitLabel(label3);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "getLocation", "()Lio/lumine/mythic/api/adapters/AbstractLocation;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/api/adapters/AbstractLocation", "getWorld", "()Lio/lumine/mythic/api/adapters/AbstractWorld;", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "getLocation", "()Lio/lumine/mythic/api/adapters/AbstractLocation;", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "getType", "()Lio/lumine/mythic/api/mobs/MythicMob;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/mobs/MythicMob", "getBossInfoRangeSquared", "()I", true);
            mv.visitInsn(I2D);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/adapters/AbstractWorld", "getPlayersInRadius", "(Lio/lumine/mythic/api/adapters/AbstractLocation;D)Ljava/util/Collection;", true);
            mv.visitVarInsn(ASTORE, 1);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Collection", "stream", "()Ljava/util/stream/Stream;", true);
            mv.visitInvokeDynamicInsn("apply", "()Ljava/util/function/Function;", new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false), new Object[]{Type.getType("(Ljava/lang/Object;)Ljava/lang/Object;"), new Handle(Opcodes.H_INVOKESTATIC, "io/lumine/mythic/core/mobs/ActiveMob", "lambda$updateBossInfo$9", "(Lio/lumine/mythic/api/adapters/AbstractPlayer;)Ljava/util/UUID;", false), Type.getType("(Lio/lumine/mythic/api/adapters/AbstractPlayer;)Ljava/util/UUID;")});
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/stream/Stream", "map", "(Ljava/util/function/Function;)Ljava/util/stream/Stream;", true);
            mv.visitMethodInsn(INVOKESTATIC, "java/util/stream/Collectors", "toSet", "()Ljava/util/stream/Collector;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/stream/Stream", "collect", "(Ljava/util/stream/Collector;)Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, "java/util/Collection");
            mv.visitVarInsn(ASTORE, 2);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "bossInfo", "Ljava/util/Optional;");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/Optional", "get", "()Ljava/lang/Object;", false);
            mv.visitTypeInsn(CHECKCAST, "com/eteryun/boss/BossInfo");
            mv.visitVarInsn(ASTORE, 3);

            mv.visitVarInsn(ALOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "getPlayers", "()Ljava/util/Collection;", false);
            mv.visitVarInsn(ASTORE, 4);

            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "getEntity", "()Lio/lumine/mythic/api/adapters/AbstractEntity;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/adapters/AbstractEntity", "getHealth", "()D", true);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "setHealth", "(D)V", false);

            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "getEntity", "()Lio/lumine/mythic/api/adapters/AbstractEntity;", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/adapters/AbstractEntity", "getMaxHealth", "()D", true);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "setMaxHealth", "(D)V", false);

            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "type", "Lio/lumine/mythic/api/mobs/MythicMob;");
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/mobs/MythicMob", "getBossInfoTitle", "()Lio/lumine/mythic/api/skills/placeholders/PlaceholderString;", true);
            mv.visitTypeInsn(NEW, "io/lumine/mythic/core/skills/placeholders/GenericPlaceholderMeta");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "getEntity", "()Lio/lumine/mythic/api/adapters/AbstractEntity;", false);
            mv.visitMethodInsn(INVOKESPECIAL, "io/lumine/mythic/core/skills/placeholders/GenericPlaceholderMeta", "<init>", "(Lio/lumine/mythic/api/skills/SkillCaster;Lio/lumine/mythic/api/adapters/AbstractEntity;)V", false);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/skills/placeholders/PlaceholderString", "get", "(Lio/lumine/mythic/core/skills/placeholders/PlaceholderMeta;)Ljava/lang/String;", true);
            mv.visitVarInsn(ASTORE, 5);

            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, 5);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "setTitle", "(Ljava/lang/String;)V", false);

            mv.visitVarInsn(ALOAD, 4);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Collection", "stream", "()Ljava/util/stream/Stream;", true);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitInvokeDynamicInsn("accept", "(Ljava/util/Collection;Lcom/eteryun/boss/BossInfo;)Ljava/util/function/Consumer;", new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false), new Object[]{Type.getType("(Ljava/lang/Object;)V"), new Handle(Opcodes.H_INVOKESTATIC, "io/lumine/mythic/core/mobs/ActiveMob", "lambda$updateBossInfo$10", "(Ljava/util/Collection;Lcom/eteryun/boss/BossInfo;Ljava/util/UUID;)V", false), Type.getType("(Ljava/util/UUID;)V")});
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/stream/Stream", "forEach", "(Ljava/util/function/Consumer;)V", true);

            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Collection", "stream", "()Ljava/util/stream/Stream;", true);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitInvokeDynamicInsn("accept", "(Lcom/eteryun/boss/BossInfo;)Ljava/util/function/Consumer;", new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", false), new Object[]{Type.getType("(Ljava/lang/Object;)V"), new Handle(Opcodes.H_INVOKESTATIC, "io/lumine/mythic/core/mobs/ActiveMob", "lambda$updateBossInfo$11", "(Lcom/eteryun/boss/BossInfo;Lio/lumine/mythic/api/adapters/AbstractPlayer;)V", false), Type.getType("(Lio/lumine/mythic/api/adapters/AbstractPlayer;)V")});
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/stream/Stream", "forEach", "(Ljava/util/function/Consumer;)V", true);

            mv.visitInsn(RETURN);
            mv.visitMaxs(5, 8);
            mv.visitEnd();

            mv = super.visitMethod(ACC_PRIVATE | ACC_STATIC | ACC_SYNTHETIC, "lambda$updateBossInfo$9", "(Lio/lumine/mythic/api/adapters/AbstractPlayer;)Ljava/util/UUID;", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/adapters/AbstractPlayer", "getUniqueId", "()Ljava/util/UUID;", true);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();

            mv = super.visitMethod(ACC_PRIVATE | ACC_STATIC | ACC_SYNTHETIC, "lambda$updateBossInfo$10", "(Ljava/util/Collection;Lcom/eteryun/boss/BossInfo;Ljava/util/UUID;)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Collection", "contains", "(Ljava/lang/Object;)Z", true);
            Label label5 = new Label();
            mv.visitJumpInsn(IFNE, label5);
            Label label6 = new Label();
            mv.visitLabel(label6);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "removePlayer", "(Ljava/util/UUID;)V", false);
            mv.visitLabel(label5);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 3);
            mv.visitEnd();

            mv = super.visitMethod(ACC_PRIVATE | ACC_STATIC | ACC_SYNTHETIC, "lambda$updateBossInfo$11", "(Lcom/eteryun/boss/BossInfo;Lio/lumine/mythic/api/adapters/AbstractPlayer;)V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/adapters/AbstractPlayer", "getUniqueId", "()Ljava/util/UUID;", true);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "isViewing", "(Ljava/util/UUID;)Z", false);
            Label label7 = new Label();
            mv.visitJumpInsn(IFNE, label7);
            Label label8 = new Label();
            mv.visitLabel(label8);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/adapters/AbstractPlayer", "getUniqueId", "()Ljava/util/UUID;", true);
            mv.visitMethodInsn(INVOKEVIRTUAL, "com/eteryun/boss/BossInfo", "addPlayer", "(Ljava/util/UUID;)V", false);
            mv.visitLabel(label7);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 2);
            mv.visitEnd();

            super.visitEnd();
        }

        @Override
        public MethodVisitor visitMethod(final int access, final String name, final String descriptor, final String signature, final String[] exceptions) {
            final MethodVisitor mv = this.cv.visitMethod(access, name, descriptor, signature, exceptions);
            if ((name.equals("tick") && descriptor.equals("(JI)V")) || name.equals("<init>") || name.equals("loadSaved") || name.equals("setupNew") || name.equals("unloadBossBars"))
                return new ActiveMobMethodVisitor(name, mv);
            return mv;
        }

        public static final class ActiveMobMethodVisitor extends MethodVisitor {
            private String methodName;
            public ActiveMobMethodVisitor(String name, MethodVisitor visitor) {
                super(ASM9, visitor);
                this.methodName = name;
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                if (opcode == GETSTATIC && name.equals("EnableThreatTables") && methodName.equals("setupNew"))
                    initBossInfo();

                super.visitFieldInsn(opcode, owner, name, descriptor);

                if (opcode == PUTFIELD && name.equals("bossBar") && methodName.equals("<init>")) {
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKESTATIC, "java/util/Optional", "empty", "()Ljava/util/Optional;", false);
                    mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "bossInfo", "Ljava/util/Optional;");
                }
            }

            @Override
            public void visitCode() {
                super.visitCode();
                if (methodName.equals("loadSaved"))
                    initBossInfo();
                if (methodName.equals("unloadBossBars")) {
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "unloadBossInfo", "()V", false);
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                if (name.equals("updateBossBar") && methodName.equals("tick")) {
                    mv.visitVarInsn(ALOAD, 0);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "io/lumine/mythic/core/mobs/ActiveMob", "updateBossInfo", "()V", false);
                }
            }

            private void initBossInfo() {
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "type", "Lio/lumine/mythic/api/mobs/MythicMob;");
                mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/mobs/MythicMob", "usesBossInfo", "()Z", true);
                Label label9 = new Label();
                mv.visitJumpInsn(IFEQ, label9);
                Label label10 = new Label();
                mv.visitLabel(label10);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "type", "Lio/lumine/mythic/api/mobs/MythicMob;");
                mv.visitMethodInsn(INVOKEINTERFACE, "io/lumine/mythic/api/mobs/MythicMob", "getBossInfo", "()Ljava/util/Optional;", true);
                mv.visitFieldInsn(PUTFIELD, "io/lumine/mythic/core/mobs/ActiveMob", "bossInfo", "Ljava/util/Optional;");
                mv.visitLabel(label9);
                mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            }
        }
    }
}

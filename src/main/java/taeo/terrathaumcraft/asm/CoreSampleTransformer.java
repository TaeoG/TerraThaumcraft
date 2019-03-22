/*
 *     Copyright 2019 TaeoGDev
 *
 *     This file is part of TerraThaumcraft.
 *
 *     TerraThaumcraft is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     TerraThaumcraft is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with TerraThaumcraft.  If not, see <https://www.gnu.org/licenses/>.
 */

package taeo.terrathaumcraft.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import taeo.ttfcapi.asm.TAPITransformer;

import java.util.Iterator;

public class CoreSampleTransformer extends TAPITransformer {
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if(name.equals("com.bioxx.tfc.WorldGen.Generators.WorldGenLooseRocks"))
        {
            // LogHelper.info("FOUND TFC GRASS CLASS");
            return patchClassASM(name, basicClass);
        }

        return basicClass;
    }
    public byte[] patchClassASM(String name, byte[] basicClass)
    {
        String targetMethod = "getCoreSample";

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while (methods.hasNext())
        {
            MethodNode meth = methods.next();
            if (meth.name.equals(targetMethod))
            {
                meth.instructions.clear();
                meth.visitCode();
                Label l0 = new Label();
                meth.visitLabel(l0);
                meth.visitTypeInsn(NEW, "java/util/ArrayList");
                meth.visitInsn(DUP);
                meth.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
                meth.visitVarInsn(ASTORE, 5);
                Label l1 = new Label();
                meth.visitLabel(l1);
                meth.visitTypeInsn(NEW, "java/util/ArrayList");
                meth.visitInsn(DUP);
                meth.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
                meth.visitVarInsn(ASTORE, 6);
                Label l2 = new Label();
                meth.visitLabel(l2);
                meth.visitVarInsn(ILOAD, 2);
                meth.visitInsn(ICONST_4);
                meth.visitInsn(ISHR);
                meth.visitInsn(ICONST_4);
                meth.visitInsn(ISHL);
                meth.visitVarInsn(ISTORE, 7);
                Label l3 = new Label();
                meth.visitLabel(l3);
                meth.visitVarInsn(ILOAD, 4);
                meth.visitInsn(ICONST_4);
                meth.visitInsn(ISHR);
                meth.visitInsn(ICONST_4);
                meth.visitInsn(ISHL);
                meth.visitVarInsn(ISTORE, 8);
                Label l4 = new Label();
                meth.visitLabel(l4);
                meth.visitInsn(ICONST_0);
                meth.visitVarInsn(ISTORE, 9);
                Label l5 = new Label();
                meth.visitLabel(l5);
                meth.visitFrame(Opcodes.F_FULL, 10, new Object[]{"com/bioxx/tfc/WorldGen/Generators/WorldGenLooseRocks", "net/minecraft/world/World", Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER, "java/util/ArrayList", "java/util/ArrayList", Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER}, 0, new Object[]{});
                meth.visitVarInsn(ILOAD, 9);
                meth.visitIntInsn(BIPUSH, 15);
                Label l6 = new Label();
                meth.visitJumpInsn(IF_ICMPGT, l6);
                Label l7 = new Label();
                meth.visitLabel(l7);
                meth.visitInsn(ICONST_0);
                meth.visitVarInsn(ISTORE, 10);
                Label l8 = new Label();
                meth.visitLabel(l8);
                meth.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
                meth.visitVarInsn(ILOAD, 10);
                meth.visitIntInsn(BIPUSH, 15);
                Label l9 = new Label();
                meth.visitJumpInsn(IF_ICMPGT, l9);
                Label l10 = new Label();
                meth.visitLabel(l10);
                meth.visitVarInsn(ILOAD, 3);
                meth.visitVarInsn(ISTORE, 11);
                Label l11 = new Label();
                meth.visitLabel(l11);
                meth.visitFrame(Opcodes.F_APPEND, 1, new Object[]{Opcodes.INTEGER}, 0, null);
                meth.visitVarInsn(ILOAD, 11);
                meth.visitVarInsn(ILOAD, 3);
                meth.visitIntInsn(BIPUSH, 35);
                meth.visitInsn(ISUB);
                Label l12 = new Label();
                meth.visitJumpInsn(IF_ICMPLE, l12);
                Label l13 = new Label();
                meth.visitLabel(l13);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitVarInsn(ILOAD, 7);
                meth.visitVarInsn(ILOAD, 9);
                meth.visitInsn(IADD);
                meth.visitVarInsn(ILOAD, 11);
                meth.visitVarInsn(ILOAD, 8);
                meth.visitVarInsn(ILOAD, 10);
                meth.visitInsn(IADD);
                meth.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", world_GetBlock_method, "(III)Lnet/minecraft/block/Block;", false);
                meth.visitFieldInsn(GETSTATIC, "com/bioxx/tfc/api/TFCBlocks", "ore", "Lnet/minecraft/block/Block;");
                Label l14 = new Label();
                meth.visitJumpInsn(IF_ACMPNE, l14);
                Label l15 = new Label();
                meth.visitLabel(l15);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitVarInsn(ILOAD, 7);
                meth.visitVarInsn(ILOAD, 9);
                meth.visitInsn(IADD);
                meth.visitVarInsn(ILOAD, 11);
                meth.visitVarInsn(ILOAD, 8);
                meth.visitVarInsn(ILOAD, 10);
                meth.visitInsn(IADD);
                meth.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", world_GetBlockMetadata_method, "(III)I", false);
                meth.visitVarInsn(ISTORE, 12);
                Label l16 = new Label();
                meth.visitLabel(l16);
                meth.visitVarInsn(ALOAD, 5);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESTATIC, "com/bioxx/tfc/Blocks/Terrain/BlockOre", "getDroppedItem", "(I)Lnet/minecraft/item/Item;", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "contains", "(Ljava/lang/Object;)Z", false);
                meth.visitJumpInsn(IFNE, l14);
                Label l17 = new Label();
                meth.visitLabel(l17);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitIntInsn(BIPUSH, 14);
                meth.visitJumpInsn(IF_ICMPEQ, l14);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitIntInsn(BIPUSH, 15);
                meth.visitJumpInsn(IF_ICMPEQ, l14);
                Label l18 = new Label();
                meth.visitLabel(l18);
                meth.visitVarInsn(ALOAD, 5);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESTATIC, "com/bioxx/tfc/Blocks/Terrain/BlockOre", "getDroppedItem", "(I)Lnet/minecraft/item/Item;", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
                meth.visitInsn(POP);
                Label l19 = new Label();
                meth.visitLabel(l19);
                meth.visitVarInsn(ALOAD, 6);
                meth.visitTypeInsn(NEW, "net/minecraft/item/ItemStack");
                meth.visitInsn(DUP);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESTATIC, "com/bioxx/tfc/Blocks/Terrain/BlockOre", "getDroppedItem", "(I)Lnet/minecraft/item/Item;", false);
                meth.visitInsn(ICONST_1);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESPECIAL, "net/minecraft/item/ItemStack", "<init>", "(Lnet/minecraft/item/Item;II)V", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
                meth.visitInsn(POP);
                meth.visitLabel(l14);
                meth.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitVarInsn(ILOAD, 7);
                meth.visitVarInsn(ILOAD, 9);
                meth.visitInsn(IADD);
                meth.visitVarInsn(ILOAD, 11);
                meth.visitVarInsn(ILOAD, 8);
                meth.visitVarInsn(ILOAD, 10);
                meth.visitInsn(IADD);
                meth.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", world_GetBlock_method, "(III)Lnet/minecraft/block/Block;", false);
                meth.visitFieldInsn(GETSTATIC, "taeo/terrathaumcraft/init/TTCBlocks", "blockMagicOre", "Lnet/minecraft/block/Block;");
                Label l20 = new Label();
                meth.visitJumpInsn(IF_ACMPNE, l20);
                Label l21 = new Label();
                meth.visitLabel(l21);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitVarInsn(ILOAD, 7);
                meth.visitVarInsn(ILOAD, 9);
                meth.visitInsn(IADD);
                meth.visitVarInsn(ILOAD, 11);
                meth.visitVarInsn(ILOAD, 8);
                meth.visitVarInsn(ILOAD, 10);
                meth.visitInsn(IADD);
                meth.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/world/World", world_GetBlockMetadata_method, "(III)I", false);
                meth.visitVarInsn(ISTORE, 12);
                Label l22 = new Label();
                meth.visitLabel(l22);
                meth.visitVarInsn(ALOAD, 5);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESTATIC, "com/bioxx/tfc/Blocks/Terrain/BlockOre", "getDroppedItem", "(I)Lnet/minecraft/item/Item;", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "contains", "(Ljava/lang/Object;)Z", false);
                meth.visitJumpInsn(IFNE, l20);
                Label l23 = new Label();
                meth.visitLabel(l23);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitIntInsn(BIPUSH, 6);
                meth.visitJumpInsn(IF_ICMPGE, l20);
                Label l24 = new Label();
                meth.visitLabel(l24);
                meth.visitVarInsn(ALOAD, 5);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESTATIC, "taeo/terrathaumcraft/block/BlockMagicOre", "getDroppedItem", "(I)Lnet/minecraft/item/Item;", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
                meth.visitInsn(POP);
                Label l25 = new Label();
                meth.visitLabel(l25);
                meth.visitVarInsn(ALOAD, 6);
                meth.visitTypeInsn(NEW, "net/minecraft/item/ItemStack");
                meth.visitInsn(DUP);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESTATIC, "taeo/terrathaumcraft/block/BlockMagicOre", "getDroppedItem", "(I)Lnet/minecraft/item/Item;", false);
                meth.visitInsn(ICONST_1);
                meth.visitVarInsn(ILOAD, 12);
                meth.visitMethodInsn(INVOKESPECIAL, "net/minecraft/item/ItemStack", "<init>", "(Lnet/minecraft/item/Item;II)V", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
                meth.visitInsn(POP);
                meth.visitLabel(l20);
                meth.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                meth.visitIincInsn(11, -1);
                meth.visitJumpInsn(GOTO, l11);
                meth.visitLabel(l12);
                meth.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
                meth.visitIincInsn(10, 1);
                meth.visitJumpInsn(GOTO, l8);
                meth.visitLabel(l9);
                meth.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
                meth.visitIincInsn(9, 1);
                meth.visitJumpInsn(GOTO, l5);
                meth.visitLabel(l6);
                meth.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
                meth.visitVarInsn(ALOAD, 6);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "isEmpty", "()Z", false);
                Label l26 = new Label();
                meth.visitJumpInsn(IFNE, l26);
                Label l27 = new Label();
                meth.visitLabel(l27);
                meth.visitVarInsn(ALOAD, 6);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitFieldInsn(GETFIELD, "net/minecraft/world/World", world_Rand_field, "Ljava/util/Random;");
                meth.visitVarInsn(ALOAD, 6);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "size", "()I", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/Random", "nextInt", "(I)I", false);
                meth.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "get", "(I)Ljava/lang/Object;", false);
                meth.visitTypeInsn(CHECKCAST, "net/minecraft/item/ItemStack");
                meth.visitInsn(ARETURN);
                meth.visitLabel(l26);
                meth.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                meth.visitInsn(ACONST_NULL);
                meth.visitInsn(ARETURN);
                Label l28 = new Label();
                meth.visitLabel(l28);
                meth.visitLocalVariable("m", "I", null, l16, l14, 12);
                meth.visitLocalVariable("m", "I", null, l22, l20, 12);
                meth.visitLocalVariable("y", "I", null, l11, l12, 11);
                meth.visitLocalVariable("z", "I", null, l8, l9, 10);
                meth.visitLocalVariable("x", "I", null, l5, l6, 9);
                meth.visitLocalVariable("this", "Lcom/bioxx/tfc/WorldGen/Generators/WorldGenLooseRocks;", null, l0, l28, 0);
                meth.visitLocalVariable("world", "Lnet/minecraft/world/World;", null, l0, l28, 1);
                meth.visitLocalVariable("xCoord", "I", null, l0, l28, 2);
                meth.visitLocalVariable("yCoord", "I", null, l0, l28, 3);
                meth.visitLocalVariable("zCoord", "I", null, l0, l28, 4);
                meth.visitLocalVariable("coreSample", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Lnet/minecraft/item/Item;>;", l1, l28, 5);
                meth.visitLocalVariable("coreSampleStacks", "Ljava/util/ArrayList;", "Ljava/util/ArrayList<Lnet/minecraft/item/ItemStack;>;", l2, l28, 6);
                meth.visitLocalVariable("x1", "I", null, l3, l28, 7);
                meth.visitLocalVariable("z1", "I", null, l4, l28, 8);
                meth.visitMaxs(6, 13);
                meth.visitEnd();
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}

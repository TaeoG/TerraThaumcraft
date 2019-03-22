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

import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.asm.TAPITransformer;
import taeo.ttfcapi.utility.LogHelper;


import java.util.Iterator;

public class WaterRenderTransformer extends TAPITransformer {


    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {

        if(name.equals("com.bioxx.tfc.Blocks.Liquids.BlockCustomLiquid") || name.equals("com.bioxx.tfc.Blocks.Liquids.BlockLiquidStatic"))
        {
            LogHelper.info(ReferenceTTC.MOD_NAME,"FOUND TFC LIQUID CLASS");
            return patchClassASM(name, basicClass);
        }
        else if(name.equals("com.bioxx.tfc.Blocks.Liquids.BlockHotWater") || name.equals("com.bioxx.tfc.Blocks.Liquids.BlockHotWaterStatic"))
        {
            return patchClassASM2(name, basicClass);
        }

        return basicClass;
    }

    public byte[] patchClassASM(String name, byte[] basicClass)
    {
        LogHelper.info(ReferenceTTC.MOD_NAME,"Patching Water render (4)");
        String targetMethod = block_colorMultiplier_method;

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while(methods.hasNext())
        {
            MethodNode meth = methods.next();
            if(meth.name.equals(targetMethod))
            {
                meth.instructions.clear();

                meth.visitCode();
                Label l0 = new Label();
                meth.visitLabel(l0);
                meth.visitLineNumber(61, l0);
                meth.visitVarInsn(ALOAD, 0);
                meth.visitFieldInsn(GETFIELD, "net/minecraft/block/BlockLiquid", block_blockMaterial_field, "Lnet/minecraft/block/material/Material;");
                meth.visitFieldInsn(GETSTATIC, "net/minecraft/block/material/Material", material_water_field, "Lnet/minecraft/block/material/Material;");
                Label l1 = new Label();
                meth.visitJumpInsn(IF_ACMPEQ, l1);
                Label l2 = new Label();
                meth.visitLabel(l2);
                meth.visitLineNumber(63, l2);
                meth.visitLdcInsn(new Integer(16777215));
                meth.visitInsn(IRETURN);
                meth.visitLabel(l1);
                meth.visitLineNumber(65, l1);
                meth.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitVarInsn(ILOAD, 2);
                meth.visitVarInsn(ILOAD, 3);
                meth.visitVarInsn(ILOAD, 4);
                meth.visitLdcInsn(new Integer(3493173));
                meth.visitMethodInsn(INVOKESTATIC, "taeo/terrathaumcraft/utility/ColorTools", "mixWaterColors", "(Lnet/minecraft/world/IBlockAccess;IIII)[I", false);
                meth.visitMethodInsn(INVOKESTATIC, "taeo/terrathaumcraft/utility/ColorTools", "convertRGBtoInt", "([I)I", false);
                meth.visitInsn(IRETURN);
                Label l3 = new Label();
                meth.visitLabel(l3);
                meth.visitLocalVariable("this", "Lcom/bioxx/tfc/Blocks/Liquids/BlockLiquidStatic;", null, l0, l3, 0);
                meth.visitLocalVariable("par1IBlockAccess", "Lnet/minecraft/world/IBlockAccess;", null, l0, l3, 1);
                meth.visitLocalVariable("par2", "I", null, l0, l3, 2);
                meth.visitLocalVariable("par3", "I", null, l0, l3, 3);
                meth.visitLocalVariable("par4", "I", null, l0, l3, 4);
                meth.visitMaxs(5, 5);
                meth.visitEnd();
            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);

        return writer.toByteArray();
    }

    public byte[] patchClassASM2(String name, byte[] basicClass)
    {
        String targetMethod = block_colorMultiplier_method;

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
                meth.visitLineNumber(42, l0);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitVarInsn(ILOAD, 2);
                meth.visitVarInsn(ILOAD, 3);
                meth.visitVarInsn(ILOAD, 4);
                meth.visitVarInsn(ALOAD, 0);
                if(name.equals("com.bioxx.tfc.Blocks.Liquids.BlockHotWater"))
                {
                    meth.visitFieldInsn(GETFIELD, "com/bioxx/tfc/Blocks/Liquids/BlockHotWater", "fluidType", "Lnet/minecraftforge/fluids/Fluid;");
                }
                if(name.equals("com.bioxx.tfc.Blocks.Liquids.BlockHotWaterStatic"))
                {
                    meth.visitFieldInsn(GETFIELD, "com/bioxx/tfc/Blocks/Liquids/BlockHotWaterStatic", "fluidType", "Lnet/minecraftforge/fluids/Fluid;");
                }

                meth.visitMethodInsn(INVOKEVIRTUAL, "net/minecraftforge/fluids/Fluid", "getColor", "()I", false);
                meth.visitMethodInsn(INVOKESTATIC, "taeo/terrathaumcraft/utility/ColorTools", "mixWaterColors", "(Lnet/minecraft/world/IBlockAccess;IIII)[I", false);
                meth.visitMethodInsn(INVOKESTATIC, "taeo/terrathaumcraft/utility/ColorTools", "convertRGBtoInt", "([I)I", false);
                meth.visitInsn(IRETURN);
                Label l1 = new Label();
                meth.visitLabel(l1);
                if(name.equals("com.bioxx.tfc.Blocks.Liquids.BlockHotWaterStatic"))
                {
                    meth.visitLocalVariable("this", "Lcom/bioxx/tfc/Blocks/Liquids/BlockHotWaterStatic;", null, l0, l1, 0);
                }
                else if(name.equals("com.bioxx.tfc.Blocks.Liquids.BlockHotWater"))
                {
                    meth.visitLocalVariable("this", "Lcom/bioxx/tfc/Blocks/Liquids/BlockHotWater;", null, l0, l1, 0);
                }
                meth.visitLocalVariable("par1IBlockAccess", "Lnet/minecraft/world/IBlockAccess;", null, l0, l1, 1);
                meth.visitLocalVariable("par2", "I", null, l0, l1, 2);
                meth.visitLocalVariable("par3", "I", null, l0, l1, 3);
                meth.visitLocalVariable("par4", "I", null, l0, l1, 4);
                meth.visitMaxs(5, 5);
                meth.visitEnd();

            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);

        return writer.toByteArray();
    }
}

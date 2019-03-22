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
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.asm.TAPITransformer;
import taeo.ttfcapi.utility.LogHelper;

import java.util.Iterator;

public class GrassRenderTransformer extends TAPITransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if(name.equals("com.bioxx.tfc.Blocks.Terrain.BlockGrass") || name.equals("com.bioxx.tfc.Blocks.Vanilla.BlockCustomTallGrass"))
        {
           // LogHelper.info("FOUND TFC GRASS CLASS");
            return patchClassASM(name, basicClass);
        }

        return basicClass;
    }
    public byte[] patchClassASM(String name, byte[] basicClass)
    {
        LogHelper.info(ReferenceTTC.MOD_NAME,"Patching Grass Render (2)");

        String targetMethod = block_colorMultiplier_method;

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while(methods.hasNext())
        {
            MethodNode meth = methods.next();
            if (meth.name.equals(targetMethod))
            {
                meth.instructions.clear();

                meth.visitCode();
                Label l0 = new Label();
                meth.visitLabel(l0);
                meth.visitLineNumber(41, l0);
                meth.visitVarInsn(ALOAD, 1);
                meth.visitVarInsn(ILOAD, 2);
                meth.visitVarInsn(ILOAD, 3);
                meth.visitVarInsn(ILOAD, 4);
                meth.visitMethodInsn(INVOKESTATIC, "taeo/terrathaumcraft/utility/ColorTools", "grassColorMultiplier", "(Lnet/minecraft/world/IBlockAccess;III)I", false);
                meth.visitInsn(IRETURN);
                Label l1 = new Label();
                meth.visitLabel(l1);
                meth.visitLocalVariable("this", "Lterrathaumcraft/block/Foliage/BlockCustomTallGrassTTC;", null, l0, l1, 0);
                meth.visitLocalVariable("bAccess", "Lnet/minecraft/world/IBlockAccess;", null, l0, l1, 1);
                meth.visitLocalVariable("x", "I", null, l0, l1, 2);
                meth.visitLocalVariable("y", "I", null, l0, l1, 3);
                meth.visitLocalVariable("z", "I", null, l0, l1, 4);
                meth.visitMaxs(4, 5);
                meth.visitEnd();

            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
}

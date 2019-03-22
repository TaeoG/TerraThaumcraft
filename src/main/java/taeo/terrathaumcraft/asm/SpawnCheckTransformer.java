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

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;
import taeo.ttfcapi.asm.TAPITransformer;

import java.util.Iterator;

public class SpawnCheckTransformer extends TAPITransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        switch(name){
            case "thaumcraft.common.tiles.TileNode":
                return patchTileNode(name, basicClass);
            case "thaumcraft.common.tiles.TileEldritchCrabSpawner":
                return patchCrabSpawner(name, basicClass);
            case "thaumcraft.common.tiles.TileEldritchObelisk":
                return patchObelisk(name, basicClass);
            case "thaumcraft.common.tiles.TileEldritchPortal":
                return patchPortal(name,basicClass);
            case "thaumcraft.common.tiles.TileEldritchAltar":
                return patchAltar(name,basicClass);
            default:
                return basicClass;
        }

    }
    public byte[] patchTileNode(String name, byte[] basicClass)
    {

        String targetMethod = "handleDarkNode";

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while(methods.hasNext())
        {
            MethodNode methodNode = methods.next();
            if (methodNode.name.equals(targetMethod))
            {

                AbstractInsnNode currentNode = null;
                LocalVariableNode localNode = null;

                Iterator<AbstractInsnNode> iter = methodNode.instructions.iterator();
                Iterator<LocalVariableNode> varIter = methodNode.localVariables.iterator();

                boolean foundVar = false;
                while(varIter.hasNext() && !foundVar)
                {
                    localNode = varIter.next();

                    if(localNode.desc.equals("Lthaumcraft/common/entities/monster/EntityGiantBrainyZombie;"))
                    {
                        LocalVariableNode fixedLocal = new LocalVariableNode("entity", "Lterrathaumcraft/entity/monster/EntityGiantBrainyZombie;", null, localNode.start, localNode.end, localNode.index);
                        methodNode.localVariables.add(fixedLocal);
                        methodNode.localVariables.remove(localNode);
                        foundVar = true;
                    }
                }

                while(iter.hasNext())
                {
                    currentNode = iter.next();
                    if(currentNode.getType() ==  AbstractInsnNode.TYPE_INSN && currentNode.getOpcode() == Opcodes.NEW)
                    {
                        TypeInsnNode typeNode = (TypeInsnNode) currentNode;
                        if(typeNode.desc.equals("thaumcraft/common/entities/monster/EntityGiantBrainyZombie"))
                        {
                            InsnList toInjectType = new InsnList();
                            toInjectType.add(new TypeInsnNode(org.objectweb.asm.Opcodes.NEW, "taeo/terrathaumcraft/entity/monster/EntityGiantBrainyZombieTTC"));

                            methodNode.instructions.insert(currentNode, toInjectType);
                            methodNode.instructions.remove(currentNode);
                        }
                    }
                    if(currentNode.getType() == AbstractInsnNode.METHOD_INSN && currentNode.getOpcode() == Opcodes.INVOKESPECIAL)
                    {
                        MethodInsnNode invokeNode = (MethodInsnNode) currentNode;
                        if(invokeNode.owner.equals("thaumcraft/common/entities/monster/EntityGiantBrainyZombie"))
                        {
                            InsnList toInjectInvoke = new InsnList();
                            toInjectInvoke.add(new MethodInsnNode(org.objectweb.asm.Opcodes.INVOKESPECIAL, "taeo/terrathaumcraft/entity/monster/EntityGiantBrainyZombieTTC", "<init>", "(Lnet/minecraft/world/World;)V", false));

                            methodNode.instructions.insert(currentNode, toInjectInvoke);
                            methodNode.instructions.remove(currentNode);

                        }
                    }
                    if(currentNode.getType() == AbstractInsnNode.METHOD_INSN && currentNode.getOpcode() == Opcodes.INVOKEVIRTUAL)
                    {
                        MethodInsnNode invokeVNode = (MethodInsnNode) currentNode;
                        if(invokeVNode.owner.equals("thaumcraft/common/entities/monster/EntityGiantBrainyZombie"))
                        {
                            InsnList toInjectVInvoke = new InsnList();
                            toInjectVInvoke.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "taeo/terrathaumcraft/entity/monster/EntityGiantBrainyZombieTTC", entity_setLocationAndAngles_method, "(DDDFF)V", false));

                            methodNode.instructions.insert(currentNode, toInjectVInvoke);
                            methodNode.instructions.remove(currentNode);
                        }
                    }


                }

            }
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classNode.accept(writer);
        return writer.toByteArray();
    }
    public byte[] patchCrabSpawner(String name, byte[] basicClass)
    {
        return basicClass;
    }
    public byte[] patchAltar(String name, byte[] basicClass)
    {
        return basicClass;
    }
    public byte[] patchObelisk(String name, byte[] basicClass)
    {
        return basicClass;
    }
    public byte[] patchPortal(String name, byte[] basicClass)
    {
        return basicClass;
    }


}

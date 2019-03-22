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

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;
import net.minecraft.launchwrapper.IClassTransformer;

public class EntityGolemBaseTransformer implements IClassTransformer{

	@Override
	public byte[] transform(String name, String transformedName,
			byte[] basicClass) {
		if(name.equals("thaumcraft.common.entities.golems.EntityGolemBase"))
		{
			LogHelper.info(ReferenceTTC.MOD_NAME,"***************Found Golem Entity Base");
			return patchClassASM(name, basicClass);
		}
		
		return basicClass;
	}

	public byte[] patchClassASM(String name, byte[] basicClass)
	{
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while(methods.hasNext())
		{
			MethodNode methodNode = methods.next();
			int fdiv_index = -1;
			if(methodNode.name.equals("setupGolem"))
			{
				AbstractInsnNode currentNode = null;
				
				
				Iterator<AbstractInsnNode> iter = methodNode.instructions.iterator();
				int index = -1;
				while(iter.hasNext())
				{
					index++;
					currentNode = iter.next();
					
					if(currentNode.getType() == AbstractInsnNode.TYPE_INSN)
					{
						TypeInsnNode typeNode = (TypeInsnNode) currentNode;
						if(typeNode.desc.equals("thaumcraft/common/entities/ai/interact/AIHarvestCrops"))
						{
							InsnList toInjectType = new InsnList();
							toInjectType.add(new TypeInsnNode(Opcodes.NEW, "taeo/terrathaumcraft/entity/ai/AIHarvestCropsTTC"));
							
							methodNode.instructions.insert(currentNode, toInjectType);
							methodNode.instructions.remove(currentNode);
						}
						if(typeNode.desc.equals("thaumcraft/common/entities/ai/interact/AIHarvestLogs"))
						{
							InsnList toInjectType = new InsnList();
							toInjectType.add(new TypeInsnNode(Opcodes.NEW, "taeo/terrathaumcraft/entity/ai/AIHarvestLogsTTC"));
							
							methodNode.instructions.insert(currentNode, toInjectType);
							methodNode.instructions.remove(currentNode);
						}
						if(typeNode.desc.equals("thaumcraft/common/entities/ai/interact/AIFish"))
						{
							InsnList toInjectType = new InsnList();
							toInjectType.add(new TypeInsnNode(Opcodes.NEW, "taeo/terrathaumcraft/entity/ai/AIFishTTC"));
							
							methodNode.instructions.insert(currentNode, toInjectType);
							methodNode.instructions.remove(currentNode);
						}
					}
					if(currentNode.getType() == AbstractInsnNode.METHOD_INSN && currentNode.getOpcode() == Opcodes.INVOKESPECIAL)
					{
						MethodInsnNode invokeNode = (MethodInsnNode) currentNode;
						if(invokeNode.owner.equals("thaumcraft/common/entities/ai/interact/AIHarvestCrops"))					
						{
							InsnList toInjectInvoke = new InsnList();
							toInjectInvoke.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "taeo/terrathaumcraft/entity/ai/AIHarvestCropsTTC", "<init>", "(Lthaumcraft/common/entities/golems/EntityGolemBase;)V", false));
							
							methodNode.instructions.insert(currentNode, toInjectInvoke);
							methodNode.instructions.remove(currentNode);
							
						}
						if(invokeNode.owner.equals("thaumcraft/common/entities/ai/interact/AIHarvestLogs"))					
						{
							InsnList toInjectInvoke = new InsnList();
							toInjectInvoke.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "taeo/terrathaumcraft/entity/ai/AIHarvestLogsTTC", "<init>", "(Lthaumcraft/common/entities/golems/EntityGolemBase;)V", false));
							
							methodNode.instructions.insert(currentNode, toInjectInvoke);
							methodNode.instructions.remove(currentNode);
							
						}
						if(invokeNode.owner.equals("thaumcraft/common/entities/ai/interact/AIFish"))					
						{
							InsnList toInjectInvoke = new InsnList();
							toInjectInvoke.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "taeo/terrathaumcraft/entity/ai/AIFishTTC", "<init>", "(Lthaumcraft/common/entities/golems/EntityGolemBase;)V", false));
							
							methodNode.instructions.insert(currentNode, toInjectInvoke);
							methodNode.instructions.remove(currentNode);
							
						}
						
					}
				}
				
			}
		}
		//mv.visitMethodInsn(INVOKESPECIAL, "thaumcraft/common/entities/ai/interact/AIHarvestCrops", "<init>", "(Lthaumcraft/common/entities/golems/EntityGolemBase;)V", false);
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
}

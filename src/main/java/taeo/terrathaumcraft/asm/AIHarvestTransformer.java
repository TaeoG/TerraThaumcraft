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
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;


import org.objectweb.asm.tree.VarInsnNode;

import taeo.terrathaumcraft.reference.ReferenceTTC;
import taeo.ttfcapi.utility.LogHelper;
import net.minecraft.launchwrapper.IClassTransformer;



public class AIHarvestTransformer implements IClassTransformer{

	@Override
	public byte[] transform(String name, String transformedName,
			byte[] basicClass) {
		//System.out.println("Looking at class " + name);
				if(name.equals("thaumcraft.common.entities.ai.interact.AIHarvestCrops"))
				{
					LogHelper.info(ReferenceTTC.MOD_NAME,"***************Potentially found Golem Harvest AI");
					return patchClassASM(name, basicClass);
				}
				
				return basicClass;
	}
	
	public byte[] patchClassASM(String name, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		String targetMethodName = "findGrownCrop";
		
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while(methods.hasNext())
		{
			MethodNode methodNode = methods.next();
			int fdiv_index = -1;
			if(methodNode.name.equals("checkAdjacent") || methodNode.name.equals("findGrownCrop"))
			{
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				InsnList toInject = new InsnList();
				
				toInject.add(new MethodInsnNode(184, "taeo/terrathaumcraft/utility/CropUtilsTTC", "isGrownCrop", "(Lnet/minecraft/world/World;III)Z", false));
				Iterator<AbstractInsnNode> iter = methodNode.instructions.iterator();
				int index = -1;
				while(iter.hasNext())
				{
					index++;
					currentNode = iter.next();
					//System.out.println(currentNode.getOpcode() + " " + currentNode.getType() + " " + currentNode.getClass());
					if(currentNode.getType() == AbstractInsnNode.METHOD_INSN)
					{
						MethodInsnNode invokeCall = (MethodInsnNode) currentNode;
						LogHelper.info(ReferenceTTC.MOD_NAME,"Found a static Method call: " + invokeCall.owner +" " + invokeCall.name);
						if(invokeCall.name.equals("isGrownCrop"))
						{
							LogHelper.info(ReferenceTTC.MOD_NAME,"Overwriting call to custom CropUtil");
							invokeCall.owner = "taeo/terrathaumcraft/utility/CropUtilsTTC";
							LogHelper.info(ReferenceTTC.MOD_NAME,"isGrownCrop owner switched to TTC class");
							
							methodNode.instructions.insert(currentNode, toInject);
							LogHelper.info(ReferenceTTC.MOD_NAME,"added new line to class");

							methodNode.instructions.remove(currentNode);

							LogHelper.info(ReferenceTTC.MOD_NAME,"removed old line");
						}
					}
				}
			}
//			if(methodNode.name.equals("findGrownCrop"))
//			{
//				//System.out.println("***************Inside Node Jar Method!");
//
//				AbstractInsnNode currentNode = null;
//				AbstractInsnNode targetNode = null;
//
//				InsnList toInject = new InsnList();
//				
//				toInject.add(new MethodInsnNode(184, "terrathaumcraft/utility/CropUtilsTTC", "isGrownCrop", "(Lnet/minecraft/world/World;III)Z", false));
//				Iterator<AbstractInsnNode> iter = methodNode.instructions.iterator();
//				int index = -1;
//				while(iter.hasNext())
//				{
//					index++;
//					currentNode = iter.next();
//					//System.out.println(currentNode.getOpcode() + " " + currentNode.getType() + " " + currentNode.getClass());
//					if(currentNode.getType() == AbstractInsnNode.METHOD_INSN)
//					{
//						MethodInsnNode invokeCall = (MethodInsnNode) currentNode;
//						LogHelper.info("Found a static Method call: " + invokeCall.owner +" " + invokeCall.name);
//						if(invokeCall.name.equals("isGrownCrop"))
//						{
//							LogHelper.info("Overwriting call to custom CropUtil");
//							invokeCall.owner = "terrathaumcraft/utility/CropUtilsTTC";
//							LogHelper.info("isGrownCrop owner switched to TTC class");
//							
//							methodNode.instructions.insert(currentNode, toInject);
//							LogHelper.info("added new line to class");
//
//							methodNode.instructions.remove(currentNode);
//
//							LogHelper.info("removed old line");
//						}
//					}
//				}
//			}
			if(methodNode.name.equals("harvest"))
			{
				InsnList callGolemHarvest = new InsnList();
				callGolemHarvest.add(new VarInsnNode(Opcodes.ALOAD, 0));
				callGolemHarvest.add(new FieldInsnNode(Opcodes.GETFIELD, "thaumcraft/common/entities/ai/interact/AIHarvestCrops", "theWorld", "Lnet/minecraft/world/World;"));
				callGolemHarvest.add(new VarInsnNode(Opcodes.ALOAD, 2));
				callGolemHarvest.add(new VarInsnNode(Opcodes.ALOAD, 0));
				callGolemHarvest.add(new FieldInsnNode(Opcodes.GETFIELD,"thaumcraft/common/entities/ai/interact/AIHarvestCrops", "xx", "I"));
				callGolemHarvest.add(new VarInsnNode(Opcodes.ALOAD, 0));
				callGolemHarvest.add(new FieldInsnNode(Opcodes.GETFIELD,"thaumcraft/common/entities/ai/interact/AIHarvestCrops", "yy", "I"));
				callGolemHarvest.add(new VarInsnNode(Opcodes.ALOAD, 0));
				callGolemHarvest.add(new FieldInsnNode(Opcodes.GETFIELD,"thaumcraft/common/entities/ai/interact/AIHarvestCrops", "zz", "I"));
				callGolemHarvest.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "taeo/terrathaumcraft/utility/CropUtilsTTC", "golemHarvestTTC", "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;III)V", false));
				
			
				AbstractInsnNode currentNode = null;
				Iterator<AbstractInsnNode> iter = methodNode.instructions.iterator();
				int index = -1;
				while(iter.hasNext())
				{
					index++;
					currentNode = iter.next();
					if(currentNode.getType() == AbstractInsnNode.LINE)
					{
						LineNumberNode line = (LineNumberNode)	currentNode;
						if(line.line == 230)
						{
							methodNode.instructions.insertBefore(currentNode, callGolemHarvest);
						}
						if(line.line == 282)
						{
							JumpInsnNode jump = new JumpInsnNode(index, null);
						}
					}
				}
			
			
			}
		}
		LogHelper.info(ReferenceTTC.MOD_NAME,"Writing AI class");
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();	
	}
	

	
}

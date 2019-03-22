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

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import net.minecraft.launchwrapper.IClassTransformer;

public class NodeJarTransformer implements IClassTransformer{

	private String targetMethodName;
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) 
	{
		//System.out.println("Looking at class " + name);
		if(name.equals("thaumcraft.common.items.wands.WandManager"))
		{
			//System.out.println("***************Potentially found Wand Manager");
			return patchClassASM(name, basicClass);
		}
		
		return basicClass;
		
		
	}
	//This transformer allows nodejars to be made with TFC wood planks
	public byte[] patchClassASM(String name, byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		targetMethodName = "fitNodeJar";
		
		InsnList toInject = new InsnList();
		toInject.add(new LdcInsnNode("plankWood"));
		
		
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while(methods.hasNext())
		{
			MethodNode m = methods.next();
			int fdiv_index = -1;
			
			if(m.name.equals(targetMethodName))
			{
				//System.out.println("***************Inside Node Jar Method!");
				
				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;
				
				Iterator<AbstractInsnNode> iter = m.instructions.iterator();
				int index = -1;
						while(iter.hasNext())
						{
							index++;
							currentNode = iter.next();
							//System.out.println(currentNode.getOpcode() + " " + currentNode.getType() + " " + currentNode.getClass());
							if(currentNode.getType() == AbstractInsnNode.LDC_INSN)
							{
								//System.out.println("Maybe Found SlabWood line");
								AbstractInsnNode rem = m.instructions.get(index);
								m.instructions.insert(currentNode, toInject);
								m.instructions.remove(currentNode);
								
							}
						}
			}
		}
		
		
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
		
	}

}

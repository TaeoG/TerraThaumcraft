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

import com.bioxx.tfc.Core.TFC_MobData;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import taeo.ttfcapi.asm.TAPITransformer;

import java.util.Iterator;

public class ThaumcraftMobInterfaceTransformer extends TAPITransformer {

	private final static String SLASHING = "SLASHING";
	private final static String PIERCING = "PIERCING";
	private final static String CRUSHING = "CRUSHING";

	//TODO fixed setSummoned and setisdevil in firebat, onlivingupdate in giantbrainy zombie, taintswarm damage bonus, slimes
	//            														AttackDamage 						DamageType 	Maximum Health 						C 	S 	P
	public final MobAttributes BrainyZombie = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 5/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 25/20, 	0,	0, 	0);
	public final MobAttributes Cultist = new MobAttributes(				TFC_MobData.ZOMBIE_DAMAGE * 4/3,	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH, 			0, 	0, 	0);
	public final MobAttributes CultistCleric = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE, 			SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 30/20, 	0, 	0, 	0);
	public final MobAttributes CultistKnight = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE, 			SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 36/20, 	0, 	0, 	0);
	public final MobAttributes EldritchCrab = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 4/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH, 			0, 	0, 	0);
	public final MobAttributes EldritchGuardian = new MobAttributes(	TFC_MobData.ZOMBIE_DAMAGE * 7/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 50/20, 	0, 	0, 	0);
	public final MobAttributes FireBat = new MobAttributes(				TFC_MobData.ZOMBIE_DAMAGE * 2/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 5/20, 	0, 	0, 	0);
	public final MobAttributes GiantBrainyZombie = new MobAttributes(	TFC_MobData.ZOMBIE_DAMAGE * 7/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 60/20, 	0, 	0, 	0);
	public final MobAttributes InhabitedZombie = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 5/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 30/20, 	0, 	0, 	0);
	public final MobAttributes MindSpider = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 1/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 1/20, 	0, 	0, 	0);
	public final MobAttributes Pech = new MobAttributes(				TFC_MobData.ZOMBIE_DAMAGE * 6/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 30/20, 	0, 	0, 	0);
	public final MobAttributes Taintacle = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 7/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 50/20, 	0, 	0, 	0);
	public final MobAttributes TaintacleSmall = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 2/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 8/20, 	0, 	0, 	0);
	public final MobAttributes TaintChicken = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 3/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 8/20, 	0, 	0, 	0);
	public final MobAttributes TaintCow = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 6/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 40/20, 	0, 	0, 	0);
	public final MobAttributes TaintCreeper = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 2/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 30/20, 	0, 	0, 	0);
	public final MobAttributes TaintPig = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 4/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 20/20, 	0, 	0, 	0);
	public final MobAttributes TaintSheep = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 3/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 20/20, 	0, 	0, 	0);
	public final MobAttributes TaintSpider = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 2/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 5/20, 	0, 	0, 	0);
	public final MobAttributes TaintSpore = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 1/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 1/20, 	0, 	0, 	0);
	public final MobAttributes TaintSporeSwarmer = new MobAttributes(	TFC_MobData.ZOMBIE_DAMAGE * 1/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 75/20, 	0, 	0, 	0);
	public final MobAttributes TaintSwarm = new MobAttributes(			TFC_MobData.ZOMBIE_DAMAGE * 2/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 30/20, 	0, 	0, 	0);
	public final MobAttributes TaintVillager = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 4/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 30/20, 	0, 	0, 	0);
	public final MobAttributes ThaumicSlime = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE, 			SLASHING, 	TFC_MobData.ZOMBIE_HEALTH, 			0, 	0, 	0);
	public final MobAttributes Watcher = new MobAttributes(				TFC_MobData.ZOMBIE_DAMAGE * 6/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 30/20, 	0, 	0, 	0);
	public final MobAttributes Wisp = new MobAttributes(				TFC_MobData.ZOMBIE_DAMAGE * 3/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 22/20, 	0, 	0, 	0);

	public final MobAttributes CultistLeader = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 5/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 125/20, 0, 	0, 	0);
	public final MobAttributes CultistPortal = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 0/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 500/20, 0, 	0, 	0);
	public final MobAttributes EldritchGolem = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 10/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 250/20, 0, 	0, 	0);
	public final MobAttributes EldritchWarden = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 10/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 200/20, 0, 	0, 	0);
	public final MobAttributes TaintacleGiant = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE * 9/3, 	SLASHING, 	TFC_MobData.ZOMBIE_HEALTH * 125/20, 0, 	0, 	0);
	public final MobAttributes ThaumcraftBoss = new MobAttributes(		TFC_MobData.ZOMBIE_DAMAGE, 			SLASHING, 	TFC_MobData.ZOMBIE_HEALTH, 			0, 	0, 	0);

	private String name;
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		this.name = name;
		switch (name)
		{
			case "thaumcraft.common.entities.monster.EntityBrainyZombie":
				return patchClass(basicClass, BrainyZombie, true);
			case "thaumcraft.common.entities.monster.EntityCultist":
				return patchClass(basicClass, Cultist, true);
			case "thaumcraft.common.entities.monster.EntityCultistCleric":
				return patchClass(basicClass, CultistCleric, false);
			case "thaumcraft.common.entities.monster.EntityCultistKnight":
				return patchClass(basicClass, CultistKnight, false);
			case "thaumcraft.common.entities.monster.EntityEldritchCrab":
				return patchClass(basicClass, EldritchCrab, true);
			case "thaumcraft.common.entities.monster.EntityEldritchGuardian":
				return patchClass(basicClass, EldritchGuardian, true);
			case "thaumcraft.common.entities.monster.EntityFireBat":
				return patchClass(basicClass, FireBat, true);
			case "thaumcraft.common.entities.monster.EntityGiantBrainyZombie":
				return patchClass(basicClass, GiantBrainyZombie, false);
			case "thaumcraft.common.entities.monster.EntityInhabitedZombie":
				return patchClass(basicClass, InhabitedZombie, true);
			case "thaumcraft.common.entities.monster.EntityMindSpider":
				return patchClass(basicClass, MindSpider, true);
			case "thaumcraft.common.entities.monster.EntityPech":
				return patchClass(basicClass, Pech, true);
			case "thaumcraft.common.entities.monster.EntityTaintacle":
				return patchClass(basicClass, Taintacle, true);
			case "thaumcraft.common.entities.monster.EntityTaintacleSmall":
				return patchClass(basicClass, TaintacleSmall, false);
			case "thaumcraft.common.entities.monster.EntityTaintChicken":
				return patchClass(basicClass, TaintChicken, true);
			case "thaumcraft.common.entities.monster.EntityTaintCow":
				return patchClass(basicClass, TaintCow, true);
			case "thaumcraft.common.entities.monster.EntityTaintCreeper":
				return patchClass(basicClass, TaintCreeper, true);
			case "thaumcraft.common.entities.monster.EntityTaintPig":
				return patchClass(basicClass, TaintPig, true);
			case "thaumcraft.common.entities.monster.EntityTaintSheep":
				return patchClass(basicClass, TaintSheep, true);
			case "thaumcraft.common.entities.monster.EntityTaintSpider":
				return patchClass(basicClass, TaintSpider, true);
			case "thaumcraft.common.entities.monster.EntityTaintSpore":
				return patchClass(basicClass, TaintSpore, true);
			case "thaumcraft.common.entities.monster.EntityTaintSporeSwarmer":
				return patchClass(basicClass, TaintSporeSwarmer, false);
			case "thaumcraft.common.entities.monster.EntityTaintSwarm":
				return patchClass(basicClass, TaintSwarm, true);
			case "thaumcraft.common.entities.monster.EntityTaintVillager":
				return patchClass(basicClass, TaintVillager, true);
			case "thaumcraft.common.entities.monster.EntityThaumicSlime":
				return patchClass(basicClass, ThaumicSlime, true);
			case "thaumcraft.common.entities.monster.EntityWatcher":
				return patchClass(basicClass, Watcher, true);
			case "thaumcraft.common.entities.monster.EntityWisp":
				return patchClass(basicClass, Wisp, true);
			case "thaumcraft.common.entities.monster.boss.EntityCultistLeader":
				return patchClass(basicClass, CultistLeader, false);
			case "thaumcraft.common.entities.monster.boss.EntityCultistPortal":
				return patchClass(basicClass, CultistPortal, true);
			case "thaumcraft.common.entities.monster.boss.EntityEldritchGolem":
				return patchClass(basicClass, EldritchGolem, false);
			case "thaumcraft.common.entities.monster.boss.EntityEldritchWarden":
				return patchClass(basicClass, EldritchWarden, false);
			case "thaumcraft.common.entities.monster.boss.EntityTaintacleGiant":
				return patchClass(basicClass, TaintacleGiant, false);
			case "thaumcraft.common.entities.monster.boss.EntityThaumcraftBoss":
				return patchClass(basicClass, ThaumcraftBoss, true);
			default:
				return basicClass;
		}

	}

	public byte[] patchClass(byte[] basicClass, MobAttributes mobAttr, boolean addInterface)
	{

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

		if (addInterface)
		{
			classNode.interfaces.add("com/bioxx/tfc/api/Interfaces/ICausesDamage");
			classNode.interfaces.add("com/bioxx/tfc/api/Interfaces/IInnateArmor");
		}
		MethodVisitor methodVisitor;
		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext())
		{
			MethodNode methodNode = methods.next();
			if (methodNode.name.equals(entity_applyattributes_method))
			{
				if(name.equals("thaumcraft.common.entities.monster.EntityFireBate"))
				{
					methodNode.instructions.clear();

					methodNode.visitCode();
					Label l0 = new Label();
					methodNode.visitLabel(l0);
					methodNode.visitLineNumber(79, l0);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitMethodInsn(INVOKESPECIAL, "net/minecraft/entity/monster/EntityMob", entity_applyattributes_method, "()V", false);
					Label l1 = new Label();
					methodNode.visitLabel(l1);
					methodNode.visitLineNumber(80, l1);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitFieldInsn(GETSTATIC, "net/minecraft/entity/SharedMonsterAttributes", monster_maxHealth_field, "Lnet/minecraft/entity/ai/attributes/IAttribute;");
					methodNode.visitMethodInsn(INVOKEVIRTUAL, "thaumcraft/common/entities/monster/EntityFireBat", entity_getEntityAttribute_method, "(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;", false);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitMethodInsn(INVOKEVIRTUAL, "thaumcraft/common/entities/monster/EntityFireBat", "getIsDevil", "()Z", false);
					Label l2 = new Label();
					methodNode.visitJumpInsn(IFEQ, l2);
					methodNode.visitLdcInsn(new Double(mobAttr.maxHealth*3));
					Label l3 = new Label();
					methodNode.visitJumpInsn(GOTO, l3);
					methodNode.visitLabel(l2);
					methodNode.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"net/minecraft/entity/ai/attributes/IAttributeInstance"});
					methodNode.visitLdcInsn(new Double(mobAttr.maxHealth));
					methodNode.visitLabel(l3);
					methodNode.visitFrame(Opcodes.F_FULL, 1, new Object[]{"thaumcraft/common/entities/monster/EntityFireBat"}, 2, new Object[]{"net/minecraft/entity/ai/attributes/IAttributeInstance", Opcodes.DOUBLE});
					methodNode.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/entity/ai/attributes/IAttributeInstance", attribute_setBaseValue_method, "(D)V", true);
					Label l4 = new Label();
					methodNode.visitLabel(l4);
					methodNode.visitLineNumber(81, l4);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitFieldInsn(GETSTATIC, "net/minecraft/entity/SharedMonsterAttributes", monster_attackDamage_field, "Lnet/minecraft/entity/ai/attributes/IAttribute;");
					methodNode.visitMethodInsn(INVOKEVIRTUAL, "thaumcraft/common/entities/monster/EntityFireBat", entity_getEntityAttribute_method, "(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;", false);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitMethodInsn(INVOKEVIRTUAL, "thaumcraft/common/entities/monster/EntityFireBat", "getIsSummoned", "()Z", false);
					Label l5 = new Label();
					methodNode.visitJumpInsn(IFEQ, l5);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitMethodInsn(INVOKEVIRTUAL, "thaumcraft/common/entities/monster/EntityFireBat", "getIsDevil", "()Z", false);
					Label l6 = new Label();
					methodNode.visitJumpInsn(IFEQ, l6);
					methodNode.visitIntInsn(SIPUSH, (int)( mobAttr.attackDamage*1.5));
					Label l7 = new Label();
					methodNode.visitJumpInsn(GOTO, l7);
					methodNode.visitLabel(l6);
					methodNode.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"net/minecraft/entity/ai/attributes/IAttributeInstance"});
					methodNode.visitIntInsn(SIPUSH, (int)mobAttr.attackDamage);
					methodNode.visitLabel(l7);
					methodNode.visitFrame(Opcodes.F_FULL, 1, new Object[]{"thaumcraft/common/entities/monster/EntityFireBat"}, 2, new Object[]{"net/minecraft/entity/ai/attributes/IAttributeInstance", Opcodes.INTEGER});
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitFieldInsn(GETFIELD, "thaumcraft/common/entities/monster/EntityFireBat", "damBonus", "I");
					methodNode.visitInsn(IADD);
					methodNode.visitInsn(I2D);
					Label l8 = new Label();
					methodNode.visitJumpInsn(GOTO, l8);
					methodNode.visitLabel(l5);
					methodNode.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"net/minecraft/entity/ai/attributes/IAttributeInstance"});
					methodNode.visitInsn(DCONST_1);
					methodNode.visitLabel(l8);
					methodNode.visitFrame(Opcodes.F_FULL, 1, new Object[]{"thaumcraft/common/entities/monster/EntityFireBat"}, 2, new Object[]{"net/minecraft/entity/ai/attributes/IAttributeInstance", Opcodes.DOUBLE});
					methodNode.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/entity/ai/attributes/IAttributeInstance", attribute_setBaseValue_method, "(D)V", true);
					Label l9 = new Label();
					methodNode.visitLabel(l9);
					methodNode.visitLineNumber(82, l9);
					methodNode.visitInsn(RETURN);
					Label l10 = new Label();
					methodNode.visitLabel(l10);
					methodNode.visitLocalVariable("this", "Lthaumcraft/common/entities/monster/EntityFireBat;", null, l0, l10, 0);
					methodNode.visitMaxs(3, 1);
					methodNode.visitEnd();
				}
				else if (name.equals("thaumcraft.common.entities.monster.EntityTaintSwarm"))
				{
					methodNode.instructions.clear();

					methodNode.visitCode();
					Label l0 = new Label();
					methodNode.visitLabel(l0);
					methodNode.visitLineNumber(121, l0);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitMethodInsn(INVOKESPECIAL, "net/minecraft/entity/monster/EntityMob", entity_applyattributes_method, "()V", false);
					Label l1 = new Label();
					methodNode.visitLabel(l1);
					methodNode.visitLineNumber(122, l1);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitFieldInsn(GETSTATIC, "net/minecraft/entity/SharedMonsterAttributes", monster_maxHealth_field, "Lnet/minecraft/entity/ai/attributes/IAttribute;");
					methodNode.visitMethodInsn(INVOKEVIRTUAL, "thaumcraft/common/entities/monster/EntityTaintSwarm", entity_getEntityAttribute_method, "(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;", false);
					methodNode.visitLdcInsn(new Double(mobAttr.maxHealth));
					methodNode.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/entity/ai/attributes/IAttributeInstance", attribute_setBaseValue_method, "(D)V", true);
					Label l2 = new Label();
					methodNode.visitLabel(l2);
					methodNode.visitLineNumber(123, l2);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitFieldInsn(GETSTATIC, "net/minecraft/entity/SharedMonsterAttributes", monster_attackDamage_field, "Lnet/minecraft/entity/ai/attributes/IAttribute;");
					methodNode.visitMethodInsn(INVOKEVIRTUAL, "thaumcraft/common/entities/monster/EntityTaintSwarm", entity_getEntityAttribute_method, "(Lnet/minecraft/entity/ai/attributes/IAttribute;)Lnet/minecraft/entity/ai/attributes/IAttributeInstance;", false);
					methodNode.visitIntInsn(SIPUSH, (int)mobAttr.attackDamage);
					methodNode.visitVarInsn(ALOAD, 0);
					methodNode.visitFieldInsn(GETFIELD, "thaumcraft/common/entities/monster/EntityTaintSwarm", "damBonus", "I");
					methodNode.visitInsn(IADD);
					methodNode.visitInsn(I2D);
					methodNode.visitMethodInsn(INVOKEINTERFACE, "net/minecraft/entity/ai/attributes/IAttributeInstance", attribute_setBaseValue_method, "(D)V", true);
					Label l3 = new Label();
					methodNode.visitLabel(l3);
					methodNode.visitLineNumber(124, l3);
					methodNode.visitInsn(RETURN);
					Label l4 = new Label();
					methodNode.visitLabel(l4);
					methodNode.visitLocalVariable("this", "Lthaumcraft/common/entities/monster/EntityTaintSwarm;", null, l0, l4, 0);
					methodNode.visitMaxs(3, 1);
					methodNode.visitEnd();
				}
				else
				{
					Iterator<AbstractInsnNode> insnListIterator = methodNode.instructions.iterator();
					while (insnListIterator.hasNext())
					{
						AbstractInsnNode currentNode = insnListIterator.next();
						if (currentNode.getType() == AbstractInsnNode.FIELD_INSN && currentNode.getOpcode() == Opcodes.GETSTATIC)
						{
							FieldInsnNode fieldInsnNode = (FieldInsnNode) currentNode;
							String name = fieldInsnNode.name;
							insnListIterator.next();
							currentNode = insnListIterator.next();
							if (currentNode.getType() == AbstractInsnNode.LDC_INSN)
							{
								LdcInsnNode ldcNode = (LdcInsnNode) currentNode;
								if (name.equals(monster_maxHealth_field))
								{
									ldcNode.cst = mobAttr.maxHealth;
								} else if (name.equals(monster_attackDamage_field))
								{
									ldcNode.cst = mobAttr.attackDamage;
								}

							}
							else if (currentNode.getType() == AbstractInsnNode.INSN)
							{
								LdcInsnNode ldcInsnNode;
								ldcInsnNode = new LdcInsnNode(mobAttr.maxHealth);
								if(name.equals(monster_attackDamage_field))
								{
									ldcInsnNode = new LdcInsnNode(mobAttr.attackDamage);
								}
								InsnList insertList = new InsnList();
								insertList.add(ldcInsnNode);
								methodNode.instructions.insert(currentNode, insertList);
								methodNode.instructions.remove(currentNode);
							}
						}
					}
				}
				

			}
		}


		methodVisitor = writer.visitMethod(ACC_PUBLIC, "getCrushArmor", "()I", null, null);
		methodVisitor.visitCode();
		Label l0 = new Label();
		methodVisitor.visitLabel(l0);
		methodVisitor.visitIntInsn(SIPUSH, mobAttr.crushArmor);
		methodVisitor.visitInsn(IRETURN);
		methodVisitor.visitEnd();

		methodVisitor = writer.visitMethod(ACC_PUBLIC, "getSlashArmor", "()I", null, null);
		methodVisitor.visitCode();
		l0 = new Label();
		methodVisitor.visitLabel(l0);
		methodVisitor.visitIntInsn(SIPUSH, mobAttr.slashArmor);
		methodVisitor.visitInsn(IRETURN);
		methodVisitor.visitEnd();

		methodVisitor = writer.visitMethod(ACC_PUBLIC, "getPierceArmor", "()I", null, null);
		methodVisitor.visitCode();
		l0 = new Label();
		methodVisitor.visitLabel(l0);
		methodVisitor.visitIntInsn(SIPUSH, mobAttr.pierceArmor);
		methodVisitor.visitInsn(IRETURN);
		methodVisitor.visitEnd();

		methodVisitor = writer.visitMethod(ACC_PUBLIC, "getDamageType", "()Lcom/bioxx/tfc/api/Enums/EnumDamageType;", null, null);
		methodVisitor.visitCode();
		methodVisitor.visitFieldInsn(GETSTATIC, "com/bioxx/tfc/api/Enums/EnumDamageType", mobAttr.damageType, "Lcom/bioxx/tfc/api/Enums/EnumDamageType;");
		methodVisitor.visitInsn(ARETURN);
		methodVisitor.visitEnd();


		classNode.accept(writer);
		return writer.toByteArray();
	}

	public class MobAttributes {
		public String damageType;
		public int crushArmor;
		public int slashArmor;
		public int pierceArmor;
		public double maxHealth;
		public double attackDamage;

		public MobAttributes(double attackDamage, String damageType, double maxHealth, int crushArmor, int slashArmor, int pierceArmor)
		{
			this.damageType = damageType;
			this.crushArmor = crushArmor;
			this.slashArmor = slashArmor;
			this.pierceArmor = pierceArmor;
			this.maxHealth = maxHealth;
			this.attackDamage = attackDamage;
		}
	}


}

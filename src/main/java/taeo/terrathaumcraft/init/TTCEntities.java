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

package taeo.terrathaumcraft.init;

import cpw.mods.fml.common.registry.EntityRegistry;
import taeo.terrathaumcraft.TerraThaumcraftMod;
import taeo.terrathaumcraft.entity.item.EntityItemTTC;
import taeo.terrathaumcraft.entity.monster.*;

public class TTCEntities {

	public static void init()
	{
		byte id = 0;
		int var1 = id + 1;

		/*EntityRegistry.registerModEntity(EntityBrainyZombieTTC.class, "BrainyZombie", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityGiantBrainyZombieTTC.class, "GiantBrainyZombie", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityWispTTC.class, "Wisp", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityFireBatTTC.class, "Firebat", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityPechTTC.class, "Pech", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityMindSpiderTTC.class, "MindSpider", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityEldritchGuardianTTC.class, "EldritchGuardian", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityEldritchWardenTTC.class, "EldritchWarden", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityCultistKnightTTC.class, "CultistKnight", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityCultistClericTTC.class, "CultistCleric", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityCultistLeaderTTC.class, "CultistLeader", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityCultistPortalTTC.class, "CultistPortal", var1++, TerraThaumcraftMod.instance, 64, 20, false);
		EntityRegistry.registerModEntity(EntityEldritchGolemTTC.class, "EldritchGolem", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityEldritchCrabTTC.class, "EldritchCrab", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityInhabitedZombieTTC.class, "InhabitedZombie", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityThaumicSlimeTTC.class, "ThaumSlime", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintSpiderTTC.class, "TaintSpider", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintacleTTC.class, "Taintacle", var1++, TerraThaumcraftMod.instance, 64, 3, false);
		EntityRegistry.registerModEntity(EntityTaintacleSmallTTC.class, "TaintacleTiny", var1++, TerraThaumcraftMod.instance, 64, 3, false);
		EntityRegistry.registerModEntity(EntityTaintSporeTTC.class, "TaintSpore", var1++, TerraThaumcraftMod.instance, 64, 20, false);
		EntityRegistry.registerModEntity(EntityTaintSporeSwarmerTTC.class, "TaintSwarmer", var1++, TerraThaumcraftMod.instance, 64, 20, false);
		EntityRegistry.registerModEntity(EntityTaintSwarmTTC.class, "TaintSwarm", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintChickenTTC.class, "TaintedChicken", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintCowTTC.class, "TaintedCow", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintCreeperTTC.class, "TaintedCreeper", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintPigTTC.class, "TaintedPig", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintSheepTTC.class, "TaintedSheep", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintVillagerTTC.class, "TaintedVillager", var1++, TerraThaumcraftMod.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntityTaintacleGiantTTC.class, "TaintacleGiant", var1++, TerraThaumcraftMod.instance, 64, 3, false);*/
        EntityRegistry.registerModEntity(EntityItemTTC.class, "ItemTTC", var1++, TerraThaumcraftMod.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityMagmaCubeTTC.class, "MagmaCubeTTC", var1++, TerraThaumcraftMod.instance, 64, 3, true);
        EntityRegistry.registerModEntity(EntityWitherSkeletonTTC.class, "WitherSkellyTTC", var1++, TerraThaumcraftMod.instance, 64,8,true);

    }
}

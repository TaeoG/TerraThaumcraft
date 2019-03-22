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

package taeo.terrathaumcraft.item.equipment;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import taeo.terrathaumcraft.entity.monster.EntityWitherSkeletonTTC;

public class ItemDebugStick extends Item {

    public ItemDebugStick()
    {
        super();
        setMaxDamage(0);
        setHasSubtypes(false);
        setUnlocalizedName("debugstick");
    }
    @Override
    public void registerIcons(IIconRegister registerer)
    {
       itemIcon = registerer.registerIcon("stick");
    }
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        /*int spacing = 2;
        int size = 2;
        for(int x1 = -size; x1 <= size; x1++)
        {
            for(int z1 = -size; z1 <= size; z1 ++)
            {
                Utils.setBiomeAt(world, x + x1 +spacing, z + z1, ThaumcraftWorldGenerator.biomeMagicalForest);
                Utils.setBiomeAt(world, x + x1, z + z1 + spacing, ThaumcraftWorldGenerator.biomeTaint);
                Utils.setBiomeAt(world, x + x1, z + z1 - spacing, ThaumcraftWorldGenerator.biomeEerie);
            }
        }
        return true;*/


       //WorldGenerator worldGen = new WorldGenSilverwoodTrees(true, 7, 5);
        //return worldGen.generate(new WorldWrapper(world),world.rand, x, y+1,z);
        if(!world.isRemote)
        {

            EntityWitherSkeletonTTC wither = new EntityWitherSkeletonTTC(world);
            wither.setLocationAndAngles(x,y,z,0,0);
            world.spawnEntityInWorld(wither);

            /*
            EntitySkeleton skeleton = new EntitySkeleton(world);
            skeleton.setSkeletonType(1);
            skeleton.setCombatTask();
            skeleton.setLocationAndAngles(x,y+1,z,0,0);
            world.spawnEntityInWorld(skeleton);
            */
            /*player.addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            LogHelper.info("Potion array size is currently " + Potion.potionTypes.length);
            int registeredPotions = 0;
            for(int i = 0; i < Potion.potionTypes.length; i++)
            {
                if(Potion.potionTypes[i] != null)
                {
                    registeredPotions++;
                }
            }
            LogHelper.info("Number of registered Potions: " + registeredPotions);
            for(int i = 0; i < Potion.potionTypes.length; i++)
            {
                if(Potion.potionTypes[i] != null)
                LogHelper.info("Potion Effect at index " + i + " = " + Potion.potionTypes[i].getName());
                else
                    LogHelper.info("Potion Effect at index " + i + " = null");

            }*/
        }

        return true;
    }


}

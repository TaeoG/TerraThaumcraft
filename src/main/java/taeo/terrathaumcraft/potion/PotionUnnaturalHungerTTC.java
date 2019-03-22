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

package taeo.terrathaumcraft.potion;

import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import thaumcraft.common.config.Config;
import thaumcraft.common.lib.potions.PotionUnnaturalHunger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PotionUnnaturalHungerTTC extends PotionUnnaturalHunger {
    public PotionUnnaturalHungerTTC()
    {
        super(Config.potionUnHungerID, true, 4482611);
    }
    @Override
    public void performEffect(EntityLivingBase target, int level)
    {

        if(target instanceof EntityPlayer && !((EntityPlayer) target).worldObj.isRemote)
        {
            EntityPlayer player = (EntityPlayer)target;
            FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
            foodstats.onUpdate(player);
            foodstats.stomachLevel = foodstats.stomachLevel * 0.9f;
            foodstats.waterLevel *= 0.9f;
            /*foodstats.nutrDairy *= 0.9;
            foodstats.nutrFruit *= 0.9;
            foodstats.nutrGrain *= 0.9;
            foodstats.nutrProtein *= 0.9;
            foodstats.nutrVeg*=0.9;*/

            EnumFoodGroup[] fgs = EnumFoodGroup.values();

            ArrayList<Integer> fgindexs = new ArrayList(Arrays.asList(0,1,2,3,4));
            boolean foundNonZeroFG = false;
            Random rand = ((EntityPlayer) target).worldObj.rand;


            while(!foundNonZeroFG)
            {
                if(fgindexs.isEmpty())
                {
                    break;
                }
                int fgindex = rand.nextInt(fgindexs.size());
                EnumFoodGroup foodGroup = fgs[fgindexs.get(fgindex)];
                float fgValue = getNutritionLevel(foodstats, foodGroup);
                if(fgValue<=0)
                {
                    setNutritionLevel(foodstats, foodGroup, 0);
                    fgindexs.remove(fgindex);
                }
                else
                {
                    setNutritionLevel(foodstats, foodGroup, Math.max( fgValue - 0.05f ,0));
                    foundNonZeroFG = true;
                }
            }

            //foodstats.addNutrition(fgs[((EntityPlayer) target).worldObj.rand.nextInt(fgs.length)], -.05f, false);
            //foodstats.addFoodExhaustion(100);

            TFC_Core.setPlayerFoodStats(player, foodstats);
            //Send update packet from Server to Client
            if(foodstats.shouldSendUpdate())
            {
                AbstractPacket pkt = new PlayerUpdatePacket(player, 0);
                TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
            }

        }
    }
    public float getNutritionLevel(FoodStatsTFC foodstats, EnumFoodGroup fg)
    {
        switch(fg)
        {
            case Dairy: return foodstats.nutrDairy;
            case Vegetable: return  foodstats.nutrVeg;
            case Protein: return foodstats.nutrProtein;
            case Fruit: return  foodstats.nutrFruit;
            case Grain: return foodstats.nutrGrain;
            default: return 0;
        }
    }
    public void setNutritionLevel(FoodStatsTFC foodstats, EnumFoodGroup fg, float value)
    {
        switch(fg)
        {
            case Dairy: foodstats.nutrDairy = value; break;
            case Vegetable: foodstats.nutrVeg = value; break;
            case Protein:  foodstats.nutrProtein = value; break;
            case Fruit:  foodstats.nutrFruit = value; break;
            case Grain: foodstats.nutrGrain = value; break;
        }
    }

    public boolean isReady(int p_76397_1_, int p_76397_2_)
    {
        int k;
        k = 50 >> p_76397_2_;
        return k > 0 ? p_76397_1_ % k == 0 : true;
    }
}



package thaumcraft.common.tiles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;

public class TileArcaneLampFertility
        extends TileThaumcraft
        implements IEssentiaTransport
{
    public ForgeDirection facing = ForgeDirection.getOrientation(0);
    public int charges = 0;

    public boolean canUpdate()
    {
        return true;
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        super.onDataPacket(net, pkt);
        if ((this.worldObj != null) &&
                (this.worldObj.isRemote)) {
            this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
        }
    }


    int count = 0;

    public void updateEntity()
    {
        if (!this.worldObj.isRemote) {
            if ((this.charges < 4) &&
                    (drawEssentia())) {
                this.charges += 1;
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            }


            if ((this.charges > 1) && (this.count++ % 300 == 0)) {
                updateAnimals();
            }
        }
    }



    private void updateAnimals()
    {
        int distance = 7;

        List<EntityAnimal> entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(distance, distance, distance));




        Iterator entityAnimalIterator = entitiesWithinAABB.iterator();

        while (entityAnimalIterator.hasNext())
        {
            EntityAnimal currentAnimal = (EntityAnimal)entityAnimalIterator.next();


            EntityLivingBase currentAnimalBase = currentAnimal;

            if ((currentAnimal.getGrowingAge() == 0) && (!currentAnimal.isInLove()))
            {
                ArrayList<EntityAnimal> sa = new ArrayList();
                Iterator varq = entitiesWithinAABB.iterator();
                while (varq.hasNext())
                {
                    EntityLivingBase var7 = (EntityLivingBase)varq.next();
                    if (var7.getClass().equals(currentAnimalBase.getClass())) {
                        sa.add((EntityAnimal)var7);
                    }
                }

                if ((sa == null) || (sa.size() <= 7)) {
                    Iterator var22 = sa.iterator();
                    EntityAnimal partner = null;
                    while (var22.hasNext())
                    {

                        EntityAnimal var33 = (EntityAnimal)var22.next();
                        if ((var33.getGrowingAge() == 0) && (!var33.isInLove())) {
                            if (partner == null) {
                                partner = var33;
                            } else {
                                this.charges -= 2;
                                var33.func_146082_f(null);
                                partner.func_146082_f(null);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void readCustomNBT(NBTTagCompound nbttagcompound) {
        this.facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("orientation"));
        this.charges = nbttagcompound.getInteger("charges");
    }


    public void writeCustomNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setInteger("orientation", this.facing.ordinal());
        nbttagcompound.setInteger("charges", this.charges);
    }



    int drawDelay = 0;

    boolean drawEssentia() {
        if (++this.drawDelay % 5 != 0) return false;
        TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.facing);
        if (te != null) {
            IEssentiaTransport ic = (IEssentiaTransport)te;
            if (!ic.canOutputTo(this.facing.getOpposite())) return false;
            if ((ic.getSuctionAmount(this.facing.getOpposite()) < getSuctionAmount(this.facing)) && (ic.takeEssentia(Aspect.LIFE, 1, this.facing.getOpposite()) == 1))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isConnectable(ForgeDirection face)
    {
        return face == this.facing;
    }

    public boolean canInputFrom(ForgeDirection face)
    {
        return face == this.facing;
    }

    public boolean canOutputTo(ForgeDirection face)
    {
        return false;
    }


    public void setSuction(Aspect aspect, int amount) {}


    public boolean renderExtendedTube()
    {
        return false;
    }

    public int getMinimumSuction()
    {
        return 0;
    }

    public Aspect getSuctionType(ForgeDirection face)
    {
        return Aspect.LIFE;
    }

    public int getSuctionAmount(ForgeDirection face)
    {
        return face == this.facing ? 128 - this.charges * 10 : 0;
    }

    public Aspect getEssentiaType(ForgeDirection loc)
    {
        return null;
    }

    public int getEssentiaAmount(ForgeDirection loc)
    {
        return 0;
    }

    public int takeEssentia(Aspect aspect, int amount, ForgeDirection facing)
    {
        return 0;
    }

    public int addEssentia(Aspect aspect, int amount, ForgeDirection facing)
    {
        return 0;
    }
}



/* Location:           D:\Taeo\Desktop\ThaumaFirmaCraft2\Thaumcraft-1.7.10-4.2.3.5.deobfnew.jar

 * Qualified Name:     thaumcraft.common.tiles.TileArcaneLampFertility

 * JD-Core Version:    0.7.0.1

 */
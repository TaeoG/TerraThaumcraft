
package thaumcraft.common.tiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import taeo.terrathaumcraft.entity.monster.EntityCultistClericTTC;
import taeo.terrathaumcraft.entity.monster.EntityCultistKnightTTC;
import taeo.terrathaumcraft.entity.monster.EntityEldritchGuardianTTC;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.common.entities.monster.EntityCultist;
import thaumcraft.common.lib.world.dim.MazeHandler;
import thaumcraft.common.lib.world.dim.MazeThread;

public class TileEldritchAltar extends TileThaumcraft
{
    private boolean spawner = false;
    private boolean open = false;
    private boolean spawnedClerics = false;
    private byte spawnType = 0;

    private byte eyes = 0;

    public void readCustomNBT(NBTTagCompound nbttagcompound)
    {
        setEyes(nbttagcompound.getByte("eyes"));
        setOpen(nbttagcompound.getBoolean("open"));
    }

    public void writeCustomNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("eyes", getEyes());
        nbttagcompound.setBoolean("open", isOpen());
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        this.spawnedClerics = nbttagcompound.getBoolean("spawnedClerics");
        this.spawner = nbttagcompound.getBoolean("spawner");
        this.spawnType = nbttagcompound.getByte("spawntype");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("spawnedClerics", this.spawnedClerics);
        nbttagcompound.setBoolean("spawner", this.spawner);
        nbttagcompound.setByte("spawntype", this.spawnType);
    }

    public double getMaxRenderDistanceSquared()
    {
        return 9216.0D;
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
    }


    public boolean isSpawner()
    {
        return this.spawner;
    }

    public void setSpawner(boolean spawner) {
        this.spawner = spawner;
    }



    public boolean canUpdate()
    {
        return true;
    }

    private int counter = 0;


    public void updateEntity()
    {
        if ((!this.worldObj.isRemote) && (isSpawner()) && (this.counter++ >= 80) && (this.counter % 40 == 0))
        {
            switch (this.spawnType) {
                case 0:
                    if (!this.spawnedClerics) {
                        spawnClerics();
                    } else {
                        spawnGuards();
                    }
                    break;
                case 1:
                    spawnGuardian();
            }

        }
    }





    private void spawnGuards()
    {
        List ents = this.worldObj.getEntitiesWithinAABB(EntityCultistClericTTC.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(24.0D, 16.0D, 24.0D));





        if (ents.size() < 1) {
            setSpawner(false);
            return;
        }

        ents = this.worldObj.getEntitiesWithinAABB(EntityCultist.class, AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1).expand(24.0D, 16.0D, 24.0D));




        if (ents.size() < 8) {
            EntityCultistKnightTTC eg = new EntityCultistKnightTTC(this.worldObj);
            int i1 = this.xCoord + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
            int j1 = this.yCoord + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 0, 3) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
            int k1 = this.zCoord + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);

            if (World.doesBlockHaveSolidTopSurface(this.worldObj, i1, j1 - 1, k1))
            {
                eg.setPosition(i1, j1, k1);

                if ((this.worldObj.checkNoEntityCollision(eg.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(eg, eg.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(eg.boundingBox)))
                {


                    eg.onSpawnWithEgg((IEntityLivingData)null);
                    eg.spawnExplosionParticle();
                    eg.setHomeArea(this.xCoord, this.yCoord, this.zCoord, 16);
                    this.worldObj.spawnEntityInWorld(eg);
                }
            }
        }
    }

    private void spawnGuardian()
    {
        EntityEldritchGuardianTTC eg = new EntityEldritchGuardianTTC(this.worldObj);
        int i1 = this.xCoord + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
        int j1 = this.yCoord + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 0, 3) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);
        int k1 = this.zCoord + MathHelper.getRandomIntegerInRange(this.worldObj.rand, 4, 10) * MathHelper.getRandomIntegerInRange(this.worldObj.rand, -1, 1);

        if (World.doesBlockHaveSolidTopSurface(this.worldObj, i1, j1 - 1, k1))
        {
            eg.setPosition(i1, j1, k1);

            if (eg.getCanSpawnHere())
            {
                eg.onSpawnWithEgg((IEntityLivingData)null);
                eg.spawnExplosionParticle();
                eg.setHomeArea(this.xCoord, this.yCoord, this.zCoord, 16);
                this.worldObj.spawnEntityInWorld(eg);
            }
        }
    }

    private void spawnClerics()
    {
        int success = 0;
        for (int a = 0; a < 4; a++) {
            int xx = 0;
            int zz = 0;
            switch (a) {
                case 0:  xx = -2;zz = -2; break;
                case 1:  xx = -2;zz = 2; break;
                case 2:  xx = 2;zz = -2; break;
                case 3:  xx = 2;zz = 2;
            }
            EntityCultistClericTTC cleric = new EntityCultistClericTTC(this.worldObj);
            if (World.doesBlockHaveSolidTopSurface(this.worldObj, this.xCoord + xx, this.yCoord - 1, this.zCoord + zz))
            {
                cleric.setPosition(this.xCoord + 0.5D + xx, this.yCoord, this.zCoord + 0.5D + zz);

                if ((this.worldObj.checkNoEntityCollision(cleric.boundingBox)) && (this.worldObj.getCollidingBoundingBoxes(cleric, cleric.boundingBox).isEmpty()) && (!this.worldObj.isAnyLiquid(cleric.boundingBox)))
                {



                    cleric.setHomeArea(this.xCoord, this.yCoord, this.zCoord, 8);
                    cleric.onSpawnWithEgg((IEntityLivingData)null);
                    cleric.spawnExplosionParticle();
                    if (this.worldObj.spawnEntityInWorld(cleric)) {
                        success++;
                        cleric.setIsRitualist(true);
                    }
                }
            }
        }
        if (success > 2) {
            this.spawnedClerics = true;
            markDirty();
        }
    }

    public byte getSpawnType()
    {
        return this.spawnType;
    }

    public void setSpawnType(byte spawnType) {
        this.spawnType = spawnType;
    }

    public byte getEyes() {
        return this.eyes;
    }

    public void setEyes(byte eyes) {
        this.eyes = eyes;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean checkForMaze() {
        int w = 15 + this.worldObj.rand.nextInt(8) * 2;
        int h = 15 + this.worldObj.rand.nextInt(8) * 2;
        if (!MazeHandler.mazesInRange(this.xCoord >> 4, this.zCoord >> 4, w, h)) {
            Thread t = new Thread(new MazeThread(this.xCoord >> 4, this.zCoord >> 4, w, h, this.worldObj.rand.nextLong()));
            t.start();
            return false;
        }
        return true;
    }
}



/* Location:           D:\Taeo\Desktop\ThaumaFirmaCraft2\Thaumcraft-1.7.10-4.2.3.5.deobfnew.jar

 * Qualified Name:     thaumcraft.common.tiles.TileEldritchAltar

 * JD-Core Version:    0.7.0.1

 */
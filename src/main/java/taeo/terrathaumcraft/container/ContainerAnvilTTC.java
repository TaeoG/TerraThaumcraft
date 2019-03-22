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

package taeo.terrathaumcraft.container;

import taeo.terrathaumcraft.tile.TEAnvilTTC;
import thaumcraft.api.ItemApi;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotAnvilFlux;
import com.bioxx.tfc.Containers.Slots.SlotAnvilHammer;
import com.bioxx.tfc.Containers.Slots.SlotAnvilIn;
import com.bioxx.tfc.Containers.Slots.SlotAnvilWeldOut;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.TFCItems;

public class ContainerAnvilTTC extends ContainerTFC{

	private TEAnvilTTC anvil;
	private int greenIndicator;
	private int redIndicator;
	private int tier = -1;
	public ContainerAnvilTTC(InventoryPlayer inventoryplayer, TEAnvilTTC anvil, World world, int x, int y, int z)
	{
		this.anvil = anvil;
		greenIndicator = -1000;
		redIndicator = -1000;
		//Hammer slot
		addSlotToContainer(new SlotAnvilHammer(inventoryplayer.player, anvil, 0, 7, 95));
		//input item slot
		addSlotToContainer(new SlotAnvilIn(anvil, 1, 87, 46));

		//Weld slots
		addSlotToContainer(new SlotAnvilIn(anvil,  2, 14, 12));
		addSlotToContainer(new SlotAnvilIn(anvil,  3, 32, 12));
		addSlotToContainer(new SlotAnvilWeldOut(anvil, 4, 23, 34));
		//blueprint slot
		addSlotToContainer(new SlotAnvilIn(anvil, 5, 105, 46));
		//flux slot
		addSlotToContainer(new SlotAnvilFlux(anvil, 6, 185, 95));
		//wand slot
		addSlotToContainer(new SlotAnvilWand(anvil,7, 149, 83 ));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 24, 122, false, true);

		
	}
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer entityplayer, int i)
	{
		ItemStack origStack = null;
		ItemStack wand = ItemApi.getItem("itemWandCasting", 0);
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slothammer = (Slot)inventorySlots.get(0);
		Slot[] slotinput = {(Slot)inventorySlots.get(1), (Slot)inventorySlots.get(2), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(5)};
		Slot slotwand = (Slot)inventorySlots.get(TEAnvilTTC.WAND_SLOT);

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();
			if(i <= 6)
			{
				if(!this.mergeItemStack(slotStack, 7, inventorySlots.size(), false))
					return null;
			}
			else if(slotStack.getItem() == TFCItems.powder && slotStack.getItemDamage() == 0)
			{
				if (!this.mergeItemStack(slotStack, 6, 7, false))
					return null;
			}
			else if(slotStack.getItem() instanceof ItemHammer)
			{
				if(slothammer.getHasStack())
					return null;
				ItemStack stack = slotStack.copy();
				stack.stackSize = 1;
				slothammer.putStack(stack);
				slotStack.stackSize--;
			}
			else if (slotStack.getItem() == wand.getItem())
			{
				if (slotwand.getHasStack()) {
					return null;
				}
				ItemStack stack = slotStack.copy();
				stack.stackSize = 1;
				slotwand.putStack(stack);
				slotStack.stackSize -= 1;
			}
			else
			{
				int j = 0;
				while(j < slotinput.length)
				{
					if(slotinput[j].getHasStack())
						j++;
					else
					{
						ItemStack stack = slotStack.copy();
						stack.stackSize = 1;
						slotinput[j].putStack(stack);
						slotStack.stackSize--;
						break;
					}
				}
			}
			if(slotStack.stackSize <= 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}
		return origStack;
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			int cv = anvil.getCraftingValue();
			int icv = anvil.getItemCraftingValueNoSet(1);
			int t = this.anvil.AnvilTier;

			if (this.redIndicator != cv)
				var2.sendProgressBarUpdate(this, 0, cv);
			if (this.greenIndicator != icv)
				var2.sendProgressBarUpdate(this, 1, icv);
			if (this.tier != t)
				var2.sendProgressBarUpdate(this, 2, t);
		}

		redIndicator = anvil.craftingValue;
		greenIndicator = anvil.itemCraftingValue;
		this.tier = this.anvil.AnvilTier;
	}

	/**
	 * This is needed to make sure that something is done when 
	 * the client receives the updated progress bar
	 * */
	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if(anvil != null)
		{
			if (par1 == 0)
				this.anvil.craftingValue = par2;
			else if (par1 == 1)
				this.anvil.itemCraftingValue = par2;
			else if (par1 == 2)
				this.anvil.AnvilTier = par2;
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		anvil.closeInventory();
	}
}

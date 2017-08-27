package com.cjm721.spawnhere.item;

import com.cjm721.spawnhere.block.BlockSpawn;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

import static com.cjm721.spawnhere.block.BlockSpawn.convertFromNumber;

public class ItemBlockSpawn extends ItemBlock {

    public ItemBlockSpawn(BlockSpawn block) {
        super(block);
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        super.getSubItems(tab, items);

        for(int i = 0; i < 4; i++) {
            items.add(new ItemStack(this,1,i));
        }
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    @Nonnull
    public String getUnlocalizedName(ItemStack stack) {
        switch(convertFromNumber(stack.getItemDamage())) {
            case MONSTER:
                return super.getUnlocalizedName() + ".monster";
            case AMBIENT:
                return super.getUnlocalizedName() + ".ambient";
            case CREATURE:
                return super.getUnlocalizedName() + ".passive";
            case WATER_CREATURE:
                return super.getUnlocalizedName() + ".water";
            default:
                return super.getUnlocalizedName(stack);

        }
    }
}

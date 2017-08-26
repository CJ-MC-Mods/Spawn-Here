package com.cjm721.spawnhere.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.cjm721.spawnhere.SpawnHere.MODID;

public class BlockSpawn extends Block implements ITileEntityProvider {

    public static final PropertyInteger TYPE = PropertyInteger.create("metadata", 0, 3);

    public BlockSpawn() {
        super(Material.GROUND);

        setRegistryName("spawn_block");
        setUnlocalizedName("spawn_block");

        setHardness(0.6F);
        setSoundType(SoundType.PLANT);

        GameRegistry.registerTileEntity(SpawnTileEntity.class, MODID + ":spawn_block");
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TYPE);
    }

    public static EnumCreatureType convertFromNumber(int meta) {
        switch (meta) {
            case 0:
                return EnumCreatureType.MONSTER;
            case 1:
                return EnumCreatureType.AMBIENT;
            case 2:
                return EnumCreatureType.CREATURE;
            case 3:
                return EnumCreatureType.WATER_CREATURE;
            default:
                return null;
        }
    }

    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE});
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);

        for (int meta = 0; meta < 4; meta++) {
            items.add(new ItemStack(this, 1, meta));
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new SpawnTileEntity(convertFromNumber(meta));
    }

    @SideOnly(Side.CLIENT)
    public void registerModels() {
        for(int meta = 0; meta < 4; meta++) {
            ModelResourceLocation location = new ModelResourceLocation(getRegistryName(), "metadata=" + meta);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), meta, location);
        }
    }
}

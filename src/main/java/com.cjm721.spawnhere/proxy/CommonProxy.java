package com.cjm721.spawnhere.proxy;

import com.cjm721.spawnhere.block.BlockSpawn;
import com.cjm721.spawnhere.item.ItemBlockSpawn;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static BlockSpawn spawnBlock;

    public void preInit(FMLPreInitializationEvent event) {
        spawnBlock = new BlockSpawn();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(spawnBlock);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlockSpawn(spawnBlock).setRegistryName(spawnBlock.getRegistryName()));
    }
}

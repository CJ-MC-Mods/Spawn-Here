package com.cjm721.spawnhere;

import com.cjm721.spawnhere.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SpawnHere.MODID, version = SpawnHere.VERSION,
        acceptedMinecraftVersions = "[1.12,1.13)",
        useMetadata = true
)
public class SpawnHere {

    @Mod.Instance(SpawnHere.MODID)
    public static SpawnHere instance;

    public static final String MODID = "spawnhere";
    public static final String VERSION = "${mod_version}";

    public static final String PROXY_CLIENT = "com.cjm721.spawnhere.proxy.ClientProxy";
    public static final String PROXY_SERVER = "com.cjm721.spawnhere.proxy.CommonProxy";

    @SidedProxy(clientSide = SpawnHere.PROXY_CLIENT, serverSide = SpawnHere.PROXY_SERVER)
    public static CommonProxy proxy;

    public static final CreativeTabs creativeTab = new CreativeTabs("spawn_here_tab") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(Item.getItemFromBlock(CommonProxy.spawnBlock), 1, 0);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}

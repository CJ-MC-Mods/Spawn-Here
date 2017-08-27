package com.cjm721.spawnhere.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.cjm721.spawnhere.SpawnHere.MODID;

@Config(modid = MODID)
public class SpawnHereConfig {

    @Config.Comment("Min Spawn Distance. [Default: 24]")
    public static int minSpawn = 24;

    @Config.Comment("Max Spawn Distance. [Default: 128]")
    public static int maxSpawn = 128;

    @Config.Comment("Override Hostile Can Spawn. [Default: false]")
    public static boolean hostileCanSpawnOverride = false;
    @Config.Comment("Spawn Chance Per Tick For Hostile. [Default: 0.001]")
    public static double hostileSpawnChance = 0.001;

    @Config.Comment("Override Passive Can Spawn. [Default: true]")
    public static boolean passiveCanSpawnOverride = true;
    @Config.Comment("Spawn Chance Per Tick For Hostile. [Default: 0.001]")
    public static double passiveSpawnChance = 0.001;

    @Config.Comment("Override Ambient Can Spawn. [Default: false]")
    public static boolean ambientCanSpawnOverride = false;
    @Config.Comment("Spawn Chance Per Tick For Hostile. [Default: 0.001]")
    public static double ambientSpawnChance = 0.001;

    @Config.Comment("Override Water Can Spawn. [Default: false]")
    public static boolean waterCanSpawnOverride = false;
    @Config.Comment("Spawn Chance Per Tick For Hostile. [Default: 0.001]")
    public static double waterSpawnChance = 0.001;

    @Mod.EventBusSubscriber(modid = MODID)
    private static class EventHandler {
        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MODID)) {
                ConfigManager.sync(MODID, Config.Type.INSTANCE);
            }
        }
    }
}

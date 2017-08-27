package com.cjm721.spawnhere.block;

import com.cjm721.spawnhere.config.SpawnHereConfig;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;

import static com.cjm721.spawnhere.config.SpawnHereConfig.*;

public class SpawnTileEntity extends TileEntity implements ITickable {

    private final EnumCreatureType type;

    public SpawnTileEntity() {
        this(EnumCreatureType.MONSTER);
    }

    public SpawnTileEntity(EnumCreatureType type) {
        this.type = type;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if (world.isRemote || world.rand.nextDouble() > lookupSpawnChance(type)) {
            return;
        }

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        WorldServer world = (WorldServer) getWorld();

        if (!world.isAnyPlayerWithinRangeAt(x, y, z, minSpawn) && pos.distanceSq(x,y,z) <= maxSpawn*maxSpawn) {
            Biome.SpawnListEntry spawnList = world.getSpawnListEntryForTypeAt(type, pos);

            if (world.canCreatureTypeSpawnHere(type, spawnList, pos)) {
                try {
                    EntityLiving entityLiving = spawnList.newInstance(getWorld());

                    if(EntitySpawnPlacementRegistry.getPlacementForEntity(spawnList.entityClass) == EntityLiving.SpawnPlacementType.IN_WATER && world.getBlockState(pos.up()).getMaterial() != Material.WATER) {
                        return;
                    }


                    entityLiving.setLocationAndAngles(x, y + 1, z, world.rand.nextFloat() * 360.0F, 0.0F);

                    net.minecraftforge.fml.common.eventhandler.Event.Result canSpawn = net.minecraftforge.event.ForgeEventFactory.canEntitySpawn(entityLiving, world, x, y + 1, z, false);
                    boolean canSpawnHere = lookupCreatureOverride(type) || entityLiving.getCanSpawnHere();
                    boolean isNotColliding = entityLiving.isNotColliding();
                    if (canSpawn == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW || (canSpawn == net.minecraftforge.fml.common.eventhandler.Event.Result.DEFAULT && canSpawnHere && isNotColliding)) {
                        if (!net.minecraftforge.event.ForgeEventFactory.doSpecialSpawn(entityLiving, world, x, y, z))
                            entityLiving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityLiving)), null);

                        if (entityLiving.isNotColliding()) {
                            world.spawnEntity(entityLiving);
                        } else {
                            entityLiving.setDead();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean lookupCreatureOverride(EnumCreatureType type) {
        switch (type) {
            case MONSTER:
                return hostileCanSpawnOverride;
            case AMBIENT:
                return SpawnHereConfig.ambientCanSpawnOverride;
            case CREATURE:
                return SpawnHereConfig.passiveCanSpawnOverride;
            case WATER_CREATURE:
                return SpawnHereConfig.waterCanSpawnOverride;
            default:
                return false;
        }
    }

    public double lookupSpawnChance(EnumCreatureType type) {
        switch (type) {
            case MONSTER:
                return SpawnHereConfig.hostileSpawnChance;
            case AMBIENT:
                return SpawnHereConfig.ambientSpawnChance;
            case CREATURE:
                return SpawnHereConfig.passiveSpawnChance;
            case WATER_CREATURE:
                return SpawnHereConfig.waterSpawnChance;
            default:
                return 0;
        }
    }
}

package com.aranaira.magichem.registry;

import com.aranaira.magichem.MagiChemMod;
import com.aranaira.magichem.block.entity.*;
import com.aranaira.magichem.block.entity.routers.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class BlockEntitiesRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MagiChemMod.MODID);

    public static final RegistryObject<BlockEntityType<AlembicBlockEntity>> ALEMBIC_BE = BLOCK_ENTITIES.register("alembic", () ->
            BlockEntityType.Builder.of(AlembicBlockEntity::new, BlockRegistry.ALEMBIC.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<DistilleryBlockEntity>> DISTILLERY_BE = BLOCK_ENTITIES.register("distillery", () ->
            BlockEntityType.Builder.of(DistilleryBlockEntity::new, BlockRegistry.DISTILLERY.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<DistilleryRouterBlockEntity>> DISTILLERY_ROUTER_BE = BLOCK_ENTITIES.register("distillery_router", () ->
            BlockEntityType.Builder.of(DistilleryRouterBlockEntity::new, BlockRegistry.DISTILLERY_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<CentrifugeBlockEntity>> CENTRIFUGE_BE = BLOCK_ENTITIES.register("centrifuge", () ->
            BlockEntityType.Builder.of(CentrifugeBlockEntity::new, BlockRegistry.CENTRIFUGE.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<CentrifugeRouterBlockEntity>> CENTRIFUGE_ROUTER_BE = BLOCK_ENTITIES.register("centrifuge_router", () ->
            BlockEntityType.Builder.of(CentrifugeRouterBlockEntity::new, BlockRegistry.CENTRIFUGE_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<FuseryBlockEntity>> FUSERY_BE = BLOCK_ENTITIES.register("fusery", () ->
            BlockEntityType.Builder.of(FuseryBlockEntity::new, BlockRegistry.FUSERY.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<FuseryRouterBlockEntity>> FUSERY_ROUTER_BE = BLOCK_ENTITIES.register("fusery_router", () ->
            BlockEntityType.Builder.of(FuseryRouterBlockEntity::new, BlockRegistry.FUSERY_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<CircleFabricationBlockEntity>> CIRCLE_FABRICATION_BE = BLOCK_ENTITIES.register("circle_fabrication", () ->
            BlockEntityType.Builder.of(CircleFabricationBlockEntity::new, BlockRegistry.CIRCLE_FABRICATION.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<CirclePowerBlockEntity>> CIRCLE_POWER_BE = BLOCK_ENTITIES.register("circle_power", () ->
            BlockEntityType.Builder.of(CirclePowerBlockEntity::new, BlockRegistry.CIRCLE_POWER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<PowerSpikeBlockEntity>> POWER_SPIKE_BE = BLOCK_ENTITIES.register("power_spike", () ->
            BlockEntityType.Builder.of(PowerSpikeBlockEntity::new, BlockRegistry.POWER_SPIKE.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<MateriaVesselBlockEntity>> MATERIA_VESSEL_BE = BLOCK_ENTITIES.register("materia_vessel", () ->
            BlockEntityType.Builder.of(MateriaVesselBlockEntity::new, BlockRegistry.MATERIA_VESSEL.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorFireBlockEntity>> ACTUATOR_FIRE_BE = BLOCK_ENTITIES.register("actuator_fire", () ->
            BlockEntityType.Builder.of(ActuatorFireBlockEntity::new, BlockRegistry.ACTUATOR_FIRE.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorWaterBlockEntity>> ACTUATOR_WATER_BE = BLOCK_ENTITIES.register("actuator_water", () ->
            BlockEntityType.Builder.of(ActuatorWaterBlockEntity::new, BlockRegistry.ACTUATOR_WATER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorEarthBlockEntity>> ACTUATOR_EARTH_BE = BLOCK_ENTITIES.register("actuator_earth", () ->
            BlockEntityType.Builder.of(ActuatorEarthBlockEntity::new, BlockRegistry.ACTUATOR_EARTH.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorAirBlockEntity>> ACTUATOR_AIR_BE = BLOCK_ENTITIES.register("actuator_air", () ->
            BlockEntityType.Builder.of(ActuatorAirBlockEntity::new, BlockRegistry.ACTUATOR_AIR.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<BaseActuatorRouterBlockEntity>> BASE_ACTUATOR_ROUTER_BE = BLOCK_ENTITIES.register("actuator_router", () ->
            BlockEntityType.Builder.of(BaseActuatorRouterBlockEntity::new, BlockRegistry.BASE_ACTUATOR_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorFireRouterBlockEntity>> ACTUATOR_FIRE_ROUTER_BE = BLOCK_ENTITIES.register("actuator_fire_router", () ->
            BlockEntityType.Builder.of(ActuatorFireRouterBlockEntity::new, BlockRegistry.ACTUATOR_FIRE_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorWaterRouterBlockEntity>> ACTUATOR_WATER_ROUTER_BE = BLOCK_ENTITIES.register("actuator_water_router", () ->
            BlockEntityType.Builder.of(ActuatorWaterRouterBlockEntity::new, BlockRegistry.ACTUATOR_WATER_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorEarthRouterBlockEntity>> ACTUATOR_EARTH_ROUTER_BE = BLOCK_ENTITIES.register("actuator_earth_router", () ->
            BlockEntityType.Builder.of(ActuatorEarthRouterBlockEntity::new, BlockRegistry.ACTUATOR_EARTH_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ActuatorAirRouterBlockEntity>> ACTUATOR_AIR_ROUTER_BE = BLOCK_ENTITIES.register("actuator_air_router", () ->
            BlockEntityType.Builder.of(ActuatorAirRouterBlockEntity::new, BlockRegistry.ACTUATOR_AIR_ROUTER.get()).build(null)
    );

    public static final RegistryObject<BlockEntityType<ExperienceExchangerBlockEntity>> EXPERIENCE_EXCHANGER_BE = BLOCK_ENTITIES.register("experience_exchanger", () ->
            BlockEntityType.Builder.of(ExperienceExchangerBlockEntity::new, BlockRegistry.EXPERIENCE_EXCHANGER.get()).build(null)
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register((eventBus));
    }
}

package net.phantompig.worldsfinestchocolate.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.phantompig.worldsfinestchocolate.WorldsFinestChocolate;

import java.util.function.Supplier;

public class WFCEntityTypes {
    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(WorldsFinestChocolate.MOD_ID);

    public static final Supplier<EntityType<RecentlyEnlargedStructureEntity>> RECENTLY_ENLARGED_STRUCTURE = ENTITY_TYPES.registerEntityType(
            "recently_enlarged_structure",
            RecentlyEnlargedStructureEntity::new,
            MobCategory.MISC,
            entityBuilder -> entityBuilder.sized(1, 1).eyeHeight(0).fireImmune().noLootTable()
    );

    public static void init(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}

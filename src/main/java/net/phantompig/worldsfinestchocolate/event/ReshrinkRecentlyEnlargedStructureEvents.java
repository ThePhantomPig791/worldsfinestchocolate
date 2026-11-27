package net.phantompig.worldsfinestchocolate.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.phantompig.worldsfinestchocolate.WorldsFinestChocolate;
import net.phantompig.worldsfinestchocolate.entity.RecentlyEnlargedStructureEntity;
import net.threetag.worldsfinest.entity.ThrownPymDisc;
import net.threetag.worldsfinest.item.WFPymDiscItem;

@EventBusSubscriber(modid = WorldsFinestChocolate.MOD_ID)
public class ReshrinkRecentlyEnlargedStructureEvents {
    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (event.getRayTraceResult() instanceof BlockHitResult hit && event.getProjectile() instanceof ThrownPymDisc disc && disc.getItem().getItem() instanceof WFPymDiscItem discItem && discItem.shrink) {
            for (Entity e : disc.level().getEntities(disc, new AABB(hit.getBlockPos()).inflate(0.25f), e -> e instanceof RecentlyEnlargedStructureEntity)) {
                if (e instanceof RecentlyEnlargedStructureEntity rese && rese.getCaptureBoundingBox().isInside(hit.getBlockPos())) rese.reshrink();
                return;
            }
        }
    }
}

package net.phantompig.worldsfinestchocolate.event;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.phantompig.worldsfinestchocolate.WorldsFinestChocolate;
import net.threetag.worldsfinest.entity.ThrownPymDisc;
import net.threetag.worldsfinest.item.ShrunkenSuitItem;
import net.threetag.worldsfinest.item.WFPymDiscItem;
import net.threetag.worldsfinest.sound.WFSoundEvents;

@EventBusSubscriber(modid = WorldsFinestChocolate.MOD_ID)
public class EnlargeSuitSetEvents {
    @SubscribeEvent
    public static void onProjectileImpactEvent(ProjectileImpactEvent event) {
        WorldsFinestChocolate.LOGGER.info("hit by {}", event.getProjectile());
        if (event.getRayTraceResult() instanceof EntityHitResult eHit
                && event.getProjectile() instanceof ThrownPymDisc thrownDisc
                && thrownDisc.getItem().getItem() instanceof WFPymDiscItem disc
                && !disc.shrink
                && eHit.getEntity() instanceof ItemEntity itemEntity
                && itemEntity.getItem().getItem() instanceof ShrunkenSuitItem suit
                && itemEntity.getItem().get(DataComponents.CONTAINER) instanceof ItemContainerContents container
        ) {
            var pos = event.getEntity().position();
            var level = event.getEntity().level();
            ShrunkenSuitItem.getItems(container).forEach(piece -> {
                ItemEntity pieceEntity = new ItemEntity(level, pos.x, pos.y, pos.z, piece, Math.random() - 0.5, 0.5 * Math.random(), Math.random() - 0.5);
                level.addFreshEntity(pieceEntity);
            });
            itemEntity.playSound(itemEntity.isInWater() ? WFSoundEvents.PYM_PARTICLE_ENLARGING_UNDERWATER.get() : WFSoundEvents.PYM_PARTICLE_ENLARGING.get());
            itemEntity.discard();
        }
    }
}

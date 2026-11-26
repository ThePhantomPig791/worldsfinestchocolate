package net.phantompig.worldsfinestchocolate.event;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.phantompig.worldsfinestchocolate.WFCConfig;
import net.phantompig.worldsfinestchocolate.WorldsFinestChocolate;
import net.threetag.worldsfinest.component.WFDataComponents;
import net.threetag.worldsfinest.item.WFItems;
import net.threetag.worldsfinest.structure.ShrunkenStructureRef;

@EventBusSubscriber(modid = WorldsFinestChocolate.MOD_ID)
public class ThrowShrunkenStructureEvents {
    @SubscribeEvent
    public static void onItemTossEvent(ItemTossEvent event) {
        ThrowTypeConfig type = WFCConfig.THROW_SHRUNKEN_STRUCTURE_TYPE.get();
        if (type != ThrowTypeConfig.NONE && event.getPlayer().isCrouching()) {
            Player player = event.getPlayer();
            ItemEntity thrownItem = event.getEntity();
            if (thrownItem.getItem().is(WFItems.SHRUNKEN_STRUCTURE)) {
                if (thrownItem.getItem().get(WFDataComponents.SHRUNKEN_STRUCTURE) instanceof ShrunkenStructureRef struct) {
                    Vec3 vel;
                    if (type == ThrowTypeConfig.STRENGTH_SCALED) {
                        double attackDamageScale = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                        attackDamageScale = 2.5 * attackDamageScale / (attackDamageScale + 5);
                        double strengthFactor = (1.5 + attackDamageScale) / Math.pow(struct.size().toMutable().length(), 0.2);
                        vel = player.getLookAngle().scale(strengthFactor);
                    } else {
                        vel = player.getLookAngle().scale(1.5);
                    }
                    thrownItem.addDeltaMovement(vel.add(0.15 * (Math.random() - 1), 0.15 * (Math.random() - 1), 0.15 * (Math.random() - 1)));
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.8f, 0.45f + (player.level().getRandom().nextFloat() * 0.2f));

                    // when the ShrunkenStructureEntity is created, the ItemEntity's velocity is copied
                }
            }
        }
    }

    public enum ThrowTypeConfig {
        CONSTANT,
        STRENGTH_SCALED,
        NONE
    }
}

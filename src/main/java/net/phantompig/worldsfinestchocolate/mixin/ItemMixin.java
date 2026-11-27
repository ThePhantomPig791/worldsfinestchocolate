package net.phantompig.worldsfinestchocolate.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.phantompig.worldsfinestchocolate.WFCConfig;
import net.threetag.worldsfinest.component.WFDataComponents;
import net.threetag.worldsfinest.entity.ShrunkenStructureEntity;
import net.threetag.worldsfinest.item.ShrunkenStructureItem;
import net.threetag.worldsfinest.structure.ShrunkenStructureRef;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void wfc$use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ShrunkenStructureItem) {
            WFCConfig.ThrowTypeConfig type = WFCConfig.THROW_SHRUNKEN_STRUCTURE_TYPE.get();
            if (type != WFCConfig.ThrowTypeConfig.NONE && !player.isCrouching() && stack.get(WFDataComponents.SHRUNKEN_STRUCTURE) instanceof ShrunkenStructureRef struct) {
                Vec3 vel;
                if (type == WFCConfig.ThrowTypeConfig.STRENGTH_SCALED) {
                    double attackDamageScale = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    attackDamageScale = 2.5 * attackDamageScale / (attackDamageScale + 5);
                    double strengthFactor = (1.5 + attackDamageScale) / Math.pow(struct.size().toMutable().length(), 0.2);
                    vel = player.getLookAngle().scale(strengthFactor);
                } else {
                    vel = player.getLookAngle().scale(1.5);
                }
                ShrunkenStructureEntity thrown = new ShrunkenStructureEntity(
                        player.level(), player.getX(), player.getY() + player.getEyeHeight() * 0.7f, player.getZ(),
                        stack
                );
                vel = vel.add(player.getDeltaMovement());
                thrown.addDeltaMovement(vel.add(0.1 * (Math.random() - 1), 0.1 * (Math.random() - 1), 0.1 * (Math.random() - 1)));
                player.level().addFreshEntity(thrown);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.8f, 0.15f + (player.level().getRandom().nextFloat() * 0.2f));
                stack.shrink(1);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}

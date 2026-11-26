package net.phantompig.worldsfinestchocolate.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.phantompig.worldsfinestchocolate.WFCConfig;
import net.threetag.worldsfinest.entity.sizechange.PymParticleSizeChanger;
import net.threetag.worldsfinest.item.WFPymDiscItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WFPymDiscItem.class)
public abstract class WFPymDiscItemMixin extends Item {
    private WFPymDiscItemMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void wfc$use(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (WFCConfig.ALLOW_SELF_DISC.get() && player.isCrouching() && player.getHealth() > 2) {
            var stack = player.getItemInHand(hand);
            if (!(stack.getItem() instanceof WFPymDiscItem disc)) return;
            PymParticleSizeChanger.changeSize(player, disc.shrink);

            player.awardStat(Stats.ITEM_USED.get(this));
            stack.consume(1, player);
            cir.setReturnValue(InteractionResult.SUCCESS);

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.6f, 1.5f + level.getRandom().nextFloat() * 0.1f);
            if (level instanceof ServerLevel sl) {
                player.hurtServer(sl, player.damageSources().generic(), 2);
            }
        }
    }
}

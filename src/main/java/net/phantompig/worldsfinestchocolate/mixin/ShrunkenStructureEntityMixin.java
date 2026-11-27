package net.phantompig.worldsfinestchocolate.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.phantompig.worldsfinestchocolate.WFCConfig;
import net.phantompig.worldsfinestchocolate.entity.RecentlyEnlargedStructureEntity;
import net.phantompig.worldsfinestchocolate.entity.WFCEntityTypes;
import net.threetag.worldsfinest.entity.ShrunkenStructureEntity;
import net.threetag.worldsfinest.structure.ShrunkenStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShrunkenStructureEntity.class)
public class ShrunkenStructureEntityMixin {
    @WrapOperation(method = "restore", at = @At(value = "INVOKE", target = "Lnet/threetag/worldsfinest/structure/ShrunkenStructure;paste(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V"))
    private void wfc$restore(ShrunkenStructure instance, ServerLevel level, BlockPos origin, Operation<Void> original) {
        original.call(instance, level, origin);
        if (WFCConfig.RESHRINK_STRUCTURE_TIME.get() > 0) {
            var rese = new RecentlyEnlargedStructureEntity(WFCEntityTypes.RECENTLY_ENLARGED_STRUCTURE.get(), level, instance.size());
            rese.setPos(origin.getX() + instance.size().getX() / 2f, origin.getY(), origin.getZ() + instance.size().getZ() / 2f);
            level.addFreshEntity(rese);
        }
    }
}

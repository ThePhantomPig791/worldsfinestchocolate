package net.phantompig.worldsfinestchocolate.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.phantompig.worldsfinestchocolate.WorldsFinestChocolate;
import net.threetag.worldsfinest.item.ShrunkenSuitItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = Entity.class)
public abstract class EntityMixin {
    @WrapMethod(method = "canBeHitByProjectile")
    private boolean wfc$canBeHitByProjectile(Operation<Boolean> original) {
        WorldsFinestChocolate.LOGGER.info("proj hit: {} is a {}", (Entity) ((Object) this), ((Entity) ((Object) this)).getClass().toString());
        return this.wfc$isShrunkenSuitItem() || original.call();
    }

    @WrapMethod(method = "isAttackable")
    private boolean wfc$isAttackable(Operation<Boolean> original) {
        WorldsFinestChocolate.LOGGER.info("attacked: {} is a {}", (Entity) ((Object) this), ((Entity) ((Object) this)).getClass().toString());
        return this.wfc$isShrunkenSuitItem() || original.call();
    }

    @Unique
    private boolean wfc$isShrunkenSuitItem() {
        return ((Entity) ((Object) this) instanceof ItemEntity itemEntity && itemEntity.getItem().getItem() instanceof ShrunkenSuitItem);
    }
}

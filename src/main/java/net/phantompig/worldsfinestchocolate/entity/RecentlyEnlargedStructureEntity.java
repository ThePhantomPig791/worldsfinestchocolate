package net.phantompig.worldsfinestchocolate.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.phantompig.worldsfinestchocolate.WFCConfig;
import net.threetag.worldsfinest.entity.ShrunkenStructureEntity;
import net.threetag.worldsfinest.entity.sizechange.PymParticleSizeChanger;
import net.threetag.worldsfinest.item.ShrunkenStructureItem;
import net.threetag.worldsfinest.structure.ShrunkenStructure;
import net.threetag.worldsfinest.structure.ShrunkenStructureRef;
import net.threetag.worldsfinest.structure.ShrunkenStructureStorage;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class RecentlyEnlargedStructureEntity extends Entity {
    public static final EntityDataAccessor<Vector3fc> SIZE = SynchedEntityData.defineId(RecentlyEnlargedStructureEntity.class, EntityDataSerializers.VECTOR3);

    public RecentlyEnlargedStructureEntity(EntityType<? extends RecentlyEnlargedStructureEntity> type, Level level) {
        this(type, level, Vec3i.ZERO);
    }

    public RecentlyEnlargedStructureEntity(EntityType<? extends RecentlyEnlargedStructureEntity> type, Level level, Vec3i size) {
        super(type, level);
        this.setSize(size);
    }

    @Override
    public void tick() {
        if (this.tickCount++ >= WFCConfig.RESHRINK_STRUCTURE_TIME.get()) {
            this.discard();
        }
    }

    public void reshrink() {
        // basically ripped from StructureShrinkerBlockEntity
        if (!(this.level() instanceof ServerLevel level)) return;
        var size = this.getSize();
        var halfSize = Vec3.atLowerCornerOf(size).scale(0.5);
        BlockPos corner1 = BlockPos.containing(this.position().subtract((int) halfSize.x, 0, (int) halfSize.z));
        BlockPos corner2 = BlockPos.containing(this.position().add((int) halfSize.x, size.getY() - 1, (int) halfSize.z));
        BoundingBox box = new BoundingBox(corner1.getX(), corner1.getY(), corner1.getZ(), corner2.getX(), corner2.getY(), corner2.getZ());
        ShrunkenStructure structure = ShrunkenStructure.capture(level, box);
        if (structure.isEmpty()) return;

        ShrunkenStructureStorage storage = ShrunkenStructureStorage.get(this.level().getServer());
        ShrunkenStructureRef ref = new ShrunkenStructureRef(storage.store(structure), structure.size());
        ShrunkenStructure.clear(level, box);

        for (LivingEntity livingEntity : level.getEntitiesOfClass(LivingEntity.class, AABB.of(box))) {
            PymParticleSizeChanger.changeSize(livingEntity, true);
        }

        ShrunkenStructureEntity entity = new ShrunkenStructureEntity(level, (double)box.minX() + (double)box.getXSpan() / 2.0, box.minY(), (double)box.minZ() + (double)box.getZSpan() / 2.0, ShrunkenStructureItem.create(ref));
        entity.shrinkFromFullSize();
        level.addFreshEntity(entity);

        this.discard();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        var size = this.getSize();
        return EntityDimensions.scalable(Math.max(size.getX(), size.getZ()), size.getY());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(SIZE, new Vector3f(0, 0, 0));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (accessor.equals(SIZE)) {
            this.refreshDimensions();
        }
    }

    public Vec3i getSize() {
        var size = this.getEntityData().get(SIZE);
        return new Vec3i((int) size.x(), (int) size.y(), (int) size.z());
    }

    public void setSize(Vec3i size) {
        this.getEntityData().set(SIZE, new Vector3f(size.getX(), size.getY(), size.getZ()));
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource damageSource, float v) {
        return false;
    }

    @Override
    public boolean mayInteract(ServerLevel level, BlockPos pos) {
        return false;
    }

    @Override
    public boolean skipAttackInteraction(Entity source) {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput valueInput) {
        this.setSize(valueInput.read("Size", Vec3i.CODEC).orElse(Vec3i.ZERO));

    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        valueOutput.store("Size", Vec3i.CODEC, this.getSize());
    }
}

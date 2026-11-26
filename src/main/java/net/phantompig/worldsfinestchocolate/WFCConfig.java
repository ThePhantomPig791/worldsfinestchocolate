package net.phantompig.worldsfinestchocolate;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.phantompig.worldsfinestchocolate.event.ThrowShrunkenStructureEvents;

public class WFCConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.EnumValue<ThrowShrunkenStructureEvents.ThrowTypeConfig> THROW_SHRUNKEN_STRUCTURE_TYPE = BUILDER
            .comment("How throwing shrunken structures is handled. CONSTANT will make every structure throw at the same speed, following almost the exact same path as Pym Discs. STRENGTH_SCALED will make stronger players throw farther and larger structures throw shorter. NONE will disable throwing structures, meaning they will drop normally like in base WF.")
            .defineEnum("throwShrunkenStructureType", ThrowShrunkenStructureEvents.ThrowTypeConfig.STRENGTH_SCALED, ThrowShrunkenStructureEvents.ThrowTypeConfig.values());

    public static final ModConfigSpec.BooleanValue ALLOW_SELF_DISC = BUILDER
            .comment("If enabled, sneak + right-clicking a Pym Disc will apply the shrink/enlarge effect to yourself.")
            .define("allowSelfDisc", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}

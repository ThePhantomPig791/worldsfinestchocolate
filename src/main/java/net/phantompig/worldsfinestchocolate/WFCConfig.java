package net.phantompig.worldsfinestchocolate;

import net.neoforged.neoforge.common.ModConfigSpec;

public class WFCConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.EnumValue<ThrowTypeConfig> THROW_SHRUNKEN_STRUCTURE_TYPE = BUILDER
            .comment("How throwing shrunken structures is handled. CONSTANT will make every structure throw at the same speed, following almost the exact same path as Pym Discs. STRENGTH_SCALED will make stronger players throw farther and larger structures throw shorter. NONE will disable throwing structures.")
            .defineEnum("throwShrunkenStructureType", ThrowTypeConfig.STRENGTH_SCALED, ThrowTypeConfig.values());

    public static final ModConfigSpec.BooleanValue ALLOW_SELF_DISC = BUILDER
            .comment("If enabled, sneak + right-clicking a Pym Disc will apply the shrink/enlarge effect to yourself.")
            .define("allowSelfDisc", true);

    public static final ModConfigSpec.IntValue RESHRINK_STRUCTURE_TIME = BUILDER
            .comment("Up to this many ticks after enlarging a Shrunken Structure, you can throw a Shrink Disc at it to re-shrink it.")
            .defineInRange("reshrinkStructureTime", 1800, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public enum ThrowTypeConfig {
        CONSTANT,
        STRENGTH_SCALED,
        NONE
    }
}

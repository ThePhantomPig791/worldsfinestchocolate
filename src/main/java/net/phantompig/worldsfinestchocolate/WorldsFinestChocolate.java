package net.phantompig.worldsfinestchocolate;

import net.neoforged.fml.config.ModConfig;
import net.phantompig.worldsfinestchocolate.entity.WFCEntityTypes;
import net.phantompig.worldsfinestchocolate.item.WFCItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(WorldsFinestChocolate.MOD_ID)
public class WorldsFinestChocolate {
    public static final String MOD_ID = "worldsfinestchocolate";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WorldsFinestChocolate(IEventBus modEventBus, ModContainer modContainer) {
        WFCItems.init(modEventBus);
        WFCEntityTypes.init(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, WFCConfig.SPEC);

        LOGGER.info("World's Finest Chocolate initialized 😋");
    }
}

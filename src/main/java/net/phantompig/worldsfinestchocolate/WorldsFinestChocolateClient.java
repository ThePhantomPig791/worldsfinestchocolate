package net.phantompig.worldsfinestchocolate;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.phantompig.worldsfinestchocolate.client.renderer.entity.RecentlyEnlargedStructureEntityRenderer;
import net.phantompig.worldsfinestchocolate.entity.WFCEntityTypes;

@Mod(value = WorldsFinestChocolate.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = WorldsFinestChocolate.MOD_ID, value = Dist.CLIENT)
public class WorldsFinestChocolateClient {
    public WorldsFinestChocolateClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(WFCEntityTypes.RECENTLY_ENLARGED_STRUCTURE.get(), RecentlyEnlargedStructureEntityRenderer::new);
    }
}

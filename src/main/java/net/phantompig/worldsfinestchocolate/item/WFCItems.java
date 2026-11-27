package net.phantompig.worldsfinestchocolate.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.phantompig.worldsfinestchocolate.WorldsFinestChocolate;
import net.threetag.worldsfinest.item.HeartOfTheSwampItem;
import net.threetag.worldsfinest.item.WFItems;

import java.util.List;

@EventBusSubscriber(modid = WorldsFinestChocolate.MOD_ID)
public class WFCItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(WorldsFinestChocolate.MOD_ID);

    public static final DeferredItem<Item> CHOCOLATE_BAR = ITEMS.registerItem("chocolate_bar", props -> new Item(props.food(WFCFood.CHOCOLATE_PROPERTIES, WFCFood.CHOCOLATE_CONSUMABLE)));
    public static final DeferredItem<Item> CHOCOLATE_COVERED_ANT = ITEMS.registerItem("chocolate_covered_ant", props -> new Item(props.food(WFCFood.CHOCOLATE_ANT_PROPERTIES, WFCFood.CHOCOLATE_CONSUMABLE)));
    public static final DeferredItem<Item> CHOCOLATE_COVERED_BULLET_ANT = ITEMS.registerItem("chocolate_covered_bullet_ant", props -> new Item(props.food(WFCFood.CHOCOLATE_ANT_PROPERTIES, WFCFood.CHOCOLATE_COVERED_BULLET_ANT_CONSUMABLE)));
    public static final DeferredItem<Item> CHOCOLATE_COVERED_CARPENTER_ANT = ITEMS.registerItem("chocolate_covered_carpenter_ant", props -> new Item(props.food(WFCFood.CHOCOLATE_ANT_PROPERTIES, WFCFood.CHOCOLATE_COVERED_CARPENTER_ANT_CONSUMABLE)));

    public static final DeferredItem<Item> CHOCOLATE_COVERED_ACORN = ITEMS.registerItem("chocolate_covered_acorn", props -> new Item(props.food(WFCFood.CHOCOLATE_COVERED_ACORN_PROPERTIES, WFCFood.CHOCOLATE_CONSUMABLE)));
    public static final DeferredItem<HeartOfTheSwampItem> CHOCOLATE_COVERED_HEART_OF_THE_SWAMP = ITEMS.registerItem("chocolate_covered_heart_of_the_swamp", props -> new HeartOfTheSwampItem(props.rarity(Rarity.UNCOMMON).food((new FoodProperties.Builder()).alwaysEdible().build(), WFCFood.CHOCOLATE_CONSUMABLE)));

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void addToCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.insertAfter(Items.COOKIE.getDefaultInstance(), CHOCOLATE_BAR.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CHOCOLATE_BAR.toStack(), CHOCOLATE_COVERED_ACORN.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CHOCOLATE_COVERED_ACORN.toStack(), CHOCOLATE_COVERED_ANT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CHOCOLATE_COVERED_ANT.toStack(), CHOCOLATE_COVERED_BULLET_ANT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(CHOCOLATE_COVERED_BULLET_ANT.toStack(), CHOCOLATE_COVERED_CARPENTER_ANT.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.insertAfter(WFItems.HEART_OF_THE_SWAMP.toStack(), CHOCOLATE_COVERED_HEART_OF_THE_SWAMP.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static class WFCFood {
        public static final FoodProperties CHOCOLATE_PROPERTIES = (new FoodProperties.Builder()).nutrition(1).saturationModifier(1).alwaysEdible().build();
        public static final FoodProperties CHOCOLATE_ANT_PROPERTIES = (new FoodProperties.Builder()).nutrition(3).saturationModifier(1).alwaysEdible().build();
        public static final FoodProperties CHOCOLATE_COVERED_ACORN_PROPERTIES = (new FoodProperties.Builder()).nutrition(1).saturationModifier(4).alwaysEdible().build();

        public static final Consumable CHOCOLATE_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8f).onConsume(new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(MobEffects.SPEED, 140, 2), new MobEffectInstance(MobEffects.SLOWNESS, 300, 0)))).build();
        public static final Consumable CHOCOLATE_COVERED_BULLET_ANT_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8f).onConsume(new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(MobEffects.SPEED, 120, 3), new MobEffectInstance(MobEffects.SLOWNESS, 180, 0), new MobEffectInstance(MobEffects.POISON, 60, 2)))).build();
        public static final Consumable CHOCOLATE_COVERED_CARPENTER_ANT_CONSUMABLE = Consumables.defaultFood().consumeSeconds(0.8f).onConsume(new ApplyStatusEffectsConsumeEffect(List.of(new MobEffectInstance(MobEffects.SPEED, 180, 6), new MobEffectInstance(MobEffects.SLOWNESS, 1000, 1)))).build();
    }
}

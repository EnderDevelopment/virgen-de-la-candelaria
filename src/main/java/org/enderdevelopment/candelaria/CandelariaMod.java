package org.enderdevelopment.candelaria;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class CandelariaMod implements ModInitializer {
    public static final String MOD_ID = "candelaria_addon";
    public static final Item MEDALLION = Registry.register(
            Registries.ITEM,
            Identifier.of(MOD_ID, "medalla_candelaria"),
            new Item(new Item.Settings().maxCount(1))
    );

    @Override
    public void onInitialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(MEDALLION));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(CommandManager.literal("candelaria")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> {
                            context.getSource().getPlayerOrThrow().giveItemStack(MEDALLION.getDefaultStack());
                            context.getSource().sendFeedback(() -> Text.literal("Has recibido la Medalla de la Candelaria."), false);
                            return 1;
                        })));
    }
}

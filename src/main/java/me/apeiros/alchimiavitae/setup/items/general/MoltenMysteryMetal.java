package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.AlchimiaVitaeRecipeTypes;
import me.apeiros.alchimiavitae.utils.Categories;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MoltenMysteryMetal extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public MoltenMysteryMetal() {

        super(Categories.ALCHEMY, AlchimiaVitaeItems.MOLTEN_MYSTERY_METAL, AlchimiaVitaeRecipeTypes.SOUL_COLLECTOR_TYPE, new ItemStack[] {
                AlchimiaVitaeItems.EXP_CRYSTAL, AlchimiaVitaeItems.ILLUMIUM, AlchimiaVitaeItems.EXP_CRYSTAL,
                AlchimiaVitaeItems.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), AlchimiaVitaeItems.DARKSTEEL,
                AlchimiaVitaeItems.EXP_CRYSTAL, AlchimiaVitaeItems.ILLUMIUM, AlchimiaVitaeItems.EXP_CRYSTAL
        });

    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return PlayerRightClickEvent::cancel;
    }
}

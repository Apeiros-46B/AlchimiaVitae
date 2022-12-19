package me.apeiros.alchimiavitae.setup.items.general;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.setup.items.crafters.DivineAltar;

public class MoltenMysteryMetal extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public MoltenMysteryMetal(ItemGroup ig, DivineAltar divineAltar) {
        super(ig, AlchimiaItems.MOLTEN_MYSTERY_METAL, AlchimiaUtils.RecipeTypes.DIVINE_ALTAR, new ItemStack[] {
                AlchimiaItems.EXP_CRYSTAL, AlchimiaItems.ILLUMIUM, AlchimiaItems.EXP_CRYSTAL,
                AlchimiaItems.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), AlchimiaItems.DARKSTEEL,
                AlchimiaItems.EXP_CRYSTAL, AlchimiaItems.ILLUMIUM, AlchimiaItems.EXP_CRYSTAL
        });

        // Add recipe to Divine Altar
        divineAltar.newRecipe(AlchimiaItems.MOLTEN_MYSTERY_METAL, this.getRecipe());
    }

    // {{{ Prevent placement
    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return PlayerRightClickEvent::cancel;
    }
    // }}}

}

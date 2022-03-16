package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class MoltenMysteryMetal extends SimpleSlimefunItem<ItemUseHandler> implements NotPlaceable {

    public MoltenMysteryMetal(ItemGroup c) {

        super(c, Items.MOLTEN_MYSTERY_METAL, Utils.RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL
        });

    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return PlayerRightClickEvent::cancel;
    }
}

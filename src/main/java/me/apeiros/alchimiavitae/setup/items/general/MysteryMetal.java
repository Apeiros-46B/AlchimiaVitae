package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.apeiros.alchimiavitae.setup.Items;
import org.bukkit.inventory.ItemStack;

public class MysteryMetal extends SlimefunItem {

    public MysteryMetal(ItemGroup c) {

        super(c, Items.MYSTERY_METAL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                Items.MOLTEN_MYSTERY_METAL, null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.MYSTERY_METAL, 16));

    }

}

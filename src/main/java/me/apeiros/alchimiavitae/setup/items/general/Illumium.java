package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import org.bukkit.inventory.ItemStack;

public class Illumium extends SlimefunItem {

    public Illumium(ItemGroup c) {

        super(c, Items.ILLUMIUM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGIC_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1,
                Items.GOOD_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, Items.GOOD_MAGIC_PLANT,
                SlimefunItems.MAGIC_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1
        }, new SlimefunItemStack(Items.ILLUMIUM, 2));

    }

}


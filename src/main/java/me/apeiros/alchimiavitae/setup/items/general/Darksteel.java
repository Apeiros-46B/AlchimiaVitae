package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import org.bukkit.inventory.ItemStack;

public class Darksteel extends SlimefunItem {

    public Darksteel(ItemGroup c) {

        super(c, Items.DARKSTEEL, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.ENDER_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1,
                Items.EVIL_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, Items.EVIL_MAGIC_PLANT,
                SlimefunItems.ENDER_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.ENDER_LUMP_1
        }, new SlimefunItemStack(Items.DARKSTEEL, 2));

    }

}

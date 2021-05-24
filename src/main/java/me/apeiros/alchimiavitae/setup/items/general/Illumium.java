package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class Illumium extends SlimefunItem {

    public Illumium(Category c) {

        super(c, Items.ILLUMIUM, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGIC_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1,
                Items.GOOD_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, Items.GOOD_MAGIC_PLANT,
                SlimefunItems.MAGIC_LUMP_1, Items.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1
        }, new SlimefunItemStack(Items.ILLUMIUM, 2));

    }

}


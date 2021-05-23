package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class Illumium extends SlimefunItem {

    public Illumium() {

        super(Categories.ALCHEMY, new SlimefunItemStack(AlchimiaVitaeItems.ILLUMIUM, 2), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.MAGIC_LUMP_1, AlchimiaVitaeItems.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1,
                AlchimiaVitaeItems.GOOD_MAGIC_PLANT, SlimefunItems.STEEL_INGOT, AlchimiaVitaeItems.GOOD_MAGIC_PLANT,
                SlimefunItems.MAGIC_LUMP_1, AlchimiaVitaeItems.EXP_CRYSTAL, SlimefunItems.MAGIC_LUMP_1
        });

    }

}


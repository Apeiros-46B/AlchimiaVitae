package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class AlchemicReinforced extends SlimefunItem {

    public AlchemicReinforced() {

        super(Categories.ALCHEMY, new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 2), RecipeType.SMELTERY, new ItemStack[] {
                AlchimiaVitaeItems.MYSTERY_METAL, AlchimiaVitaeItems.DARKSTEEL, AlchimiaVitaeItems.ILLUMIUM,
                AlchimiaVitaeItems.EXP_CRYSTAL, null, null,
                null, null, null
        });

    }

}

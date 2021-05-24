package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class EXPCrystal extends SlimefunItem {

    public EXPCrystal(Category c) {

        super(c, Items.EXP_CRYSTAL, RecipeTypes.EXP_CRYSTALLIZER_TYPE, new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 9), null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.EXP_CRYSTAL, 2));

    }

}

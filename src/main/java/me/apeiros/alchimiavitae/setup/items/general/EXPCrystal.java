package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.AlchimiaVitaeRecipeTypes;
import me.apeiros.alchimiavitae.utils.Categories;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class EXPCrystal extends SlimefunItem {

    public EXPCrystal() {

        super(Categories.GENERAL, AlchimiaVitaeItems.EXP_CRYSTAL, AlchimiaVitaeRecipeTypes.EXP_CRYSTALLIZER_TYPE, new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 9), null, null,
                null, null, null,
                null, null, null
        });

    }

}

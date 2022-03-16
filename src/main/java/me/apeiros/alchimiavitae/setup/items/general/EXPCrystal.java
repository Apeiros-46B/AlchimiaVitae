package me.apeiros.alchimiavitae.setup.items.general;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Utils;
import org.bukkit.inventory.ItemStack;

public class EXPCrystal extends SlimefunItem {

    public EXPCrystal(ItemGroup c) {

        super(c, Items.EXP_CRYSTAL, Utils.RecipeTypes.EXP_CRYSTALLIZER_TYPE, new ItemStack[] {
                new SlimefunItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 9), null, null,
                null, null, null,
                null, null, null
        }, new SlimefunItemStack(Items.EXP_CRYSTAL, 2));

    }

}

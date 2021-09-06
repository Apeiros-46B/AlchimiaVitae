package me.apeiros.alchimiavitae.setup.items.plants;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GoodMagicPlant extends UnplaceableBlock {

    public GoodMagicPlant(ItemGroup c) {

        super(c, Items.GOOD_MAGIC_PLANT, RecipeTypes.PLANT_INFUSION_CHAMBER_TYPE, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), SlimefunItems.MAGIC_LUMP_3, null,
                null, null, null,
                null, null, null
        });

    }

}

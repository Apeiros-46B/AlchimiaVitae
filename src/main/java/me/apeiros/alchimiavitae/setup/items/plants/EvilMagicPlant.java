package me.apeiros.alchimiavitae.setup.items.plants;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class EvilMagicPlant extends UnplaceableBlock {

    public EvilMagicPlant(ItemGroup c) {

        super(c, Items.EVIL_MAGIC_PLANT, RecipeTypes.PLANT_INFUSION_CHAMBER_TYPE, new ItemStack[] {
                new ItemStack(Material.OAK_SAPLING), Items.CONDENSED_SOUL, null,
                null, null, null,
                null, null, null
        });

    }

}

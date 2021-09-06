package me.apeiros.alchimiavitae.setup.items.electric;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class EXPCrystallizer extends AContainer implements RecipeDisplayItem {

    public EXPCrystallizer(ItemGroup c) {

        super(c, Items.EXP_CRYSTALLIZER, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                SlimefunItems.TALISMAN_WISE, SlimefunItems.MAGICAL_GLASS, SlimefunItems.TALISMAN_WISE,
                SlimefunItems.HARDENED_GLASS, SlimefunItems.EXP_COLLECTOR, SlimefunItems.HARDENED_GLASS,
                SlimefunItems.HEATING_COIL, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.HEATING_COIL
        });

        this.setProcessingSpeed(1).setCapacity(64).setEnergyConsumption(16);

    }

    public void registerDefaultRecipes() {
        this.registerRecipe(10, new ItemStack[]{new SlimefunItemStack(SlimefunItems.FILLED_FLASK_OF_KNOWLEDGE, 9)}, new ItemStack[]{new SlimefunItemStack(Items.EXP_CRYSTAL, 2), new SlimefunItemStack(SlimefunItems.FLASK_OF_KNOWLEDGE, 9)});
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.EMERALD);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "AV_EXP_CRYSTALLIZER";
    }
}

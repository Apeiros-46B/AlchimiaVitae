package me.apeiros.alchimiavitae.setup.items.electric;

import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.AlchimiaVitaeItems;
import me.apeiros.alchimiavitae.utils.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.checkerframework.common.returnsreceiver.qual.This;

import javax.annotation.Nonnull;

public class AdvancedBrewingChamber extends AContainer implements RecipeDisplayItem {

    public AdvancedBrewingChamber() {
        super(Categories.BREWS, AlchimiaVitaeItems.ADVANCED_BREWING_CHAMBER, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                SlimefunItems.HARDENED_GLASS, new ItemStack(Material.BREWING_STAND), SlimefunItems.HARDENED_GLASS,
                SlimefunItems.WATER_RUNE, SlimefunItems.HEATED_PRESSURE_CHAMBER_2, SlimefunItems.FIRE_RUNE,
                SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.AUTO_BREWER, SlimefunItems.HEATING_COIL
        });
    }

    protected void registerDefaultRecipes() {
        this.registerRecipe(40, SlimefunItems.OIL_BUCKET, SlimefunItems.FUEL_BUCKET);
    }

    private ItemStack brew(Material input, Material potionType, PotionMeta potion) {
        PotionData data = potion.getBasePotionData();
        if (data.getType() == PotionType.WATER) {
            potion.setBasePotionData(new PotionData(PotionType.THICK, false, false));
            return new ItemStack(potionType);
        } else if (data.getType() == PotionType.UNCRAFTABLE) {
            PotionType potionRecipe;
            if (input == Material.REDSTONE) {
                potion.setBasePotionData(new PotionData(data.getType(), true, data.isUpgraded()));
                return new ItemStack(potionType);
            }

            if (input == Material.GLOWSTONE_DUST) {
                potion.setBasePotionData(new PotionData(data.getType(), data.isExtended(), true));
                return new ItemStack(potionType);
            }

        } else {

        }

        return null;
    }

    public ItemStack getProgressBar() {
        return new ItemStack(Material.BREWING_STAND);
    }

    @Nonnull
    public String getMachineIdentifier() {
        return "ADVANCED_BREWER";
    }
}

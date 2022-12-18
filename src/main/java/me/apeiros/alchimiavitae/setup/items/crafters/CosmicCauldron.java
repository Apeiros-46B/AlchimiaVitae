package me.apeiros.alchimiavitae.setup.items.crafters;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public class CosmicCauldron extends Crafter<SlimefunItemStack> {

    public CosmicCauldron(ItemGroup ig) {
        super(ig, AlchimiaItems.COSMIC_CAULDRON, AlchimiaUtils.RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                AlchimiaItems.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, AlchimiaItems.EXP_CRYSTAL,
                AlchimiaItems.DARKSTEEL, AlchimiaItems.DIVINE_ALTAR, AlchimiaItems.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        });

        // Set up recipes
        this.setupRecipes();
    }

    // {{{ Set up recipes
    @Override
    protected void setupRecipes() {
        // Instantiate map
        this.recipes = new RecipeMap<>();

        // Potion of Osmosis
        this.newRecipe(null, null,
            // Out
            AlchimiaItems.POTION_OF_OSMOSIS,

            // In
            AlchimiaItems.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_BLOCK), AlchimiaItems.EXP_CRYSTAL,
            AlchimiaItems.DARK_ESSENCE, new ItemStack(Material.DRAGON_BREATH), AlchimiaItems.LIGHT_ESSENCE,
            AlchimiaItems.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), AlchimiaItems.ILLUMIUM
        );

        // Benevolent Brew
        this.newRecipe(null, null,
            AlchimiaItems.BENEVOLENT_BREW,

            AlchimiaItems.EXP_CRYSTAL, new ItemStack(Material.LILAC), new ItemStack(Material.CORNFLOWER),
            AlchimiaItems.LIGHT_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.TOTEM_OF_UNDYING),
            AlchimiaItems.ILLUMIUM, new ItemStack(Material.LILY_OF_THE_VALLEY), new ItemStack(Material.POPPY)
        );

        // Malevolent Concoction
        this.newRecipe(null, null,
            AlchimiaItems.MALEVOLENT_CONCOCTION,

            AlchimiaItems.EXP_CRYSTAL, new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.BONE_BLOCK),
            AlchimiaItems.DARK_ESSENCE, new ItemStack(Material.DRAGON_BREATH), new ItemStack(Material.LAVA_BUCKET),
            AlchimiaItems.DARKSTEEL, AlchimiaItems.CONDENSED_SOUL, new ItemStack(Material.ROTTEN_FLESH)
        );
    }
    // }}}

    // {{{ Set up effects
    // On instance creation
    @Override
    protected void newInstanceEffects(World w, Location l) {
        // Play effects
        w.spawnParticle(Particle.TOTEM, l, 100, 3, 3, 3);
        w.playSound(l, Sound.BLOCK_BEACON_ACTIVATE, 1F, 1F);
    }

    // On craft
    @Override
    protected void finish(
            int layer,
            long delay,
            World w,
            Location l,
            BlockMenu menu,
            SlimefunItemStack item) {

        // Schedule task
        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
            switch (layer) {
                case 0 -> {
                    // Pre-craft
                    w.playSound(l, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
                    w.spawnParticle(Particle.SPELL_WITCH, l, 2, 1, 1, 1);

                    // Call the method again with the next layer
                    this.finish(layer + 1, delay, w, l, menu, item);
                }
                case 1 -> {
                    // Pre-craft
                    w.playSound(l, Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
                    w.playSound(l, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1, 1);
                    w.spawnParticle(Particle.CRIT_MAGIC, l, 200, 1, 1, 1);

                    // Call the method again with the next layer
                    this.finish(layer + 1, delay, w, l, menu, item);
                }
                default -> {
                    // Output the item
                    ItemStack newItem = item.clone();

                    if (menu.fits(newItem, OUT_SLOTS)) {
                        menu.pushItem(newItem, OUT_SLOTS);
                    } else {
                        w.dropItemNaturally(l.add(0, 0.5, 0), newItem);
                    }

                    // Post-craft
                    w.playSound(l, Sound.ITEM_BOTTLE_FILL, 1, 1);
                    w.playSound(l, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.5F, 1);
                    w.playSound(l, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
                    w.playSound(l, Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
                    w.playSound(l, Sound.ITEM_LODESTONE_COMPASS_LOCK, 2, 1);
                    w.spawnParticle(Particle.FLASH, l, 1, 0.1, 0.1, 0.1);
                    w.spawnParticle(Particle.END_ROD, l, 200, 0.1, 4, 0.1);
                }
            }
        }, delay);
    }
    // }}}

}

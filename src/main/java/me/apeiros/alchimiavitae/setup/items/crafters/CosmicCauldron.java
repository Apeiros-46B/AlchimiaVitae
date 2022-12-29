package me.apeiros.alchimiavitae.setup.items.crafters;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/**
 * Cosmic Cauldron
 */
public class CosmicCauldron extends AbstractCrafter<SlimefunItemStack> {

    public CosmicCauldron(ItemGroup ig, DivineAltar divineAltar) {
        super(ig, AlchimiaItems.COSMIC_CAULDRON, AlchimiaUtils.RecipeTypes.DIVINE_ALTAR, new ItemStack[]{
                AlchimiaItems.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, AlchimiaItems.EXP_CRYSTAL,
                AlchimiaItems.DARKSTEEL, AlchimiaItems.DIVINE_ALTAR, AlchimiaItems.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        });

        // Add recipe to Divine Altar
        divineAltar.newRecipe(AlchimiaItems.COSMIC_CAULDRON, this.getRecipe());
    }

    // {{{ Set up effects
    @Override
    protected void newInstanceEffects(World w, Location l) {
        // Play effects
        w.spawnParticle(Particle.TOTEM, l, 100, 3, 3, 3);
        w.playSound(l, Sound.BLOCK_BEACON_ACTIVATE, 1F, 1F);
    }
    // }}}

    // {{{ Potion recipes are registered in the potion classes
    @Override
    protected void addDefaultRecipes() {}
    // }}}

    // {{{ Finish crafting
    @Override
    protected void finish(World w, Location l, BlockMenu menu, SlimefunItemStack item) {
        // Schedule task
        new BukkitRunnable() {
            private int layer = 4;

            @Override
            public void run() {
                if (layer == 4) {
                    // Pre-craft
                    w.playSound(l, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
                    w.spawnParticle(Particle.SPELL_WITCH, l, 2, 1, 1, 1);

                    // Decrease layer
                    layer--;
                } else if (layer > 0) {
                    // Pre-craft
                    w.playSound(l, Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
                    w.playSound(l, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1, 1);
                    w.spawnParticle(Particle.CRIT_MAGIC, l, 200, 1, 1, 1);

                    // Decrease layer
                    layer--;
                } else {
                    // Output the item
                    ItemStack newItem = item.clone();

                    if (menu.fits(newItem, OUT_SLOTS)) {
                        menu.pushItem(newItem, OUT_SLOTS);
                    } else {
                        // Drop if it doesn't fit
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

                    // Cancel runnable
                    this.cancel();
                }
            }
        }.runTaskTimer(AlchimiaVitae.i(), 0, 30);
    }
    // }}}

}

package me.apeiros.alchimiavitae.setup.items.crafters;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

/**
 * Divine Altar
 */
public class DivineAltar extends AbstractCrafter<SlimefunItemStack> {

    public DivineAltar(ItemGroup ig) {
        super(ig, AlchimiaItems.DIVINE_ALTAR, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                AlchimiaItems.EXP_CRYSTAL, SlimefunItems.ELECTRO_MAGNET, AlchimiaItems.EXP_CRYSTAL,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.BLISTERING_INGOT_3,
                SlimefunItems.ANCIENT_PEDESTAL, SlimefunItems.HEATED_PRESSURE_CHAMBER, SlimefunItems.ANCIENT_PEDESTAL
        });
    }

    // {{{ Set up effects
    @Override
    protected void newInstanceEffects(World w, Location l) {
        // Play effects
        w.spawnParticle(Particle.REVERSE_PORTAL, l, 100, 0.5, 0.5, 0.5);
        w.playSound(l, Sound.BLOCK_BEACON_ACTIVATE, 1F, 1F);
    }
    // }}}

    // {{{ Default recipes
    @Override
    protected void addDefaultRecipes() {
        // {{{ Prepare
        // Get plugin instance and config
        AlchimiaVitae instance = AlchimiaVitae.i();
        Configuration cfg = instance.getConfig();

        // Get config values
        boolean reinforcedTransmutation       = cfg.getBoolean("options.transmutations.reinforced-transmutation");
        boolean hardenedTransmutation         = cfg.getBoolean("options.transmutations.hardened-transmutation");
        boolean steelTransmutation            = cfg.getBoolean("options.transmutations.steel-transmutation");
        boolean damascusTransmutation         = cfg.getBoolean("options.transmutations.damascus-transmutation");
        boolean compressedCarbonTransmutation = cfg.getBoolean("options.transmutations.compressed-carbon-transmutation");

        // Get ItemGroup and RecipeType
        ItemGroup ig = AlchimiaUtils.ItemGroups.ALTAR_RECIPES;
        RecipeType rt = AlchimiaUtils.RecipeTypes.DIVINE_ALTAR;
        // }}}

        // {{{ Reinforced Alloy Ingot
        if (reinforcedTransmutation) {
            this.newRecipe(ig, rt,
                // Out
                new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 2),

                // In
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                AlchimiaItems.DARKSTEEL, AlchimiaItems.MYSTERY_METAL, AlchimiaItems.ILLUMIUM,
                null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
            );
        }
        // }}}

        // {{{ Hardened Metal
        if (hardenedTransmutation) {
            this.newRecipe(ig, rt,
                new SlimefunItemStack(SlimefunItems.HARDENED_METAL_INGOT, 2),

                null, SlimefunItems.STEEL_INGOT, null,
                AlchimiaItems.DARKSTEEL, AlchimiaItems.MYSTERY_METAL, AlchimiaItems.ILLUMIUM,
                null, SlimefunItems.STEEL_INGOT, null
            );
        }
        // }}}

        // {{{ Steel Ingot
        if (steelTransmutation) {
            this.newRecipe(ig, rt,
                new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 8),

                null, new ItemStack(Material.IRON_BLOCK), null,
                AlchimiaItems.DARKSTEEL, AlchimiaItems.MYSTERY_METAL, AlchimiaItems.ILLUMIUM,
                null, SlimefunItems.CARBON, null
            );
        }
        // }}}

        // {{{ Damascus Steel Ingot
        if (damascusTransmutation) {
            this.newRecipe(ig, rt,
                new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 8),

                null, new ItemStack(Material.IRON_BLOCK), null,
                AlchimiaItems.DARKSTEEL, AlchimiaItems.MYSTERY_METAL, AlchimiaItems.ILLUMIUM,
                null, SlimefunItems.COMPRESSED_CARBON, null
            );
        }
        // }}}

        // {{{ Compressed Carbon
        if (compressedCarbonTransmutation) {
            this.newRecipe(ig, rt,
                SlimefunItems.COMPRESSED_CARBON,

                new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
            );
        }
        // }}}
    }
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
                    w.playSound(l, Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1, 1);
                    w.playSound(l, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);

                    // Decrease layer
                    layer--;
                } else if (layer > 0) {
                    // Pre-craft
                    w.playSound(l, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                    w.playSound(l, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                    w.spawnParticle(Particle.FLASH, l, 2, 0.1, 0.1, 0.1);

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
                    w.strikeLightningEffect(l.add(0, 0.5, 0));
                    w.playSound(l, Sound.ITEM_TRIDENT_THUNDER, 1, 1);
                    w.playSound(l, Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                    w.spawnParticle(Particle.FLASH, l, 5, 0.1, 0.1, 0.1);
                    w.spawnParticle(Particle.REVERSE_PORTAL, l, 300, 2, 2, 2);

                    // Cancel runnable
                    this.cancel();
                }
            }
        }.runTaskTimer(AlchimiaVitae.i(), 0, 30);
    }
    // }}}

}

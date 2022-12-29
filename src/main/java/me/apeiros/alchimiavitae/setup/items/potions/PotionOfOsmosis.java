package me.apeiros.alchimiavitae.setup.items.potions;

import java.util.Collection;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.setup.items.crafters.CosmicCauldron;

public class PotionOfOsmosis extends AbstractListenerPotion {

    public PotionOfOsmosis(ItemGroup ig, CosmicCauldron cauldron) {
        super(ig, AlchimiaItems.POTION_OF_OSMOSIS, AlchimiaUtils.RecipeTypes.COSMIC_CAULDRON, new ItemStack[] {
                AlchimiaItems.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_SCRAP), AlchimiaItems.EXP_CRYSTAL,
                AlchimiaItems.DARK_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), AlchimiaItems.LIGHT_ESSENCE,
                AlchimiaItems.DARKSTEEL, new ItemStack(Material.DRAGON_BREATH), AlchimiaItems.ILLUMIUM
        }, cauldron);
    }

    // {{{ Handle potion drink
    @EventHandler(ignoreCancelled = true)
    public void onDrink(PlayerItemConsumeEvent e) {
        ItemStack potion = e.getItem();

        // Make sure the potion is similar to this one
        if (!this.isSimilar(potion))
            return;

        // Cancel the event to prevent the potion from being consumed normally
        e.setCancelled(true);

        Player p = e.getPlayer();
        Collection<PotionEffect> effects = p.getActivePotionEffects();

        // Make sure the player has effects
        if (p.getActivePotionEffects().isEmpty()) {
            p.sendMessage(AlchimiaUtils.format("<red>There are no effects to absorb!"));
            return;
        }

        // Consume Potion of Osmosis
        p.getInventory().removeItem(potion);

        // Remove the player's potion effects
        for (PotionEffect effect : effects) {
            p.removePotionEffect(effect.getType());
        }

        // Make a new potion
        ItemStack newPotion = AlchimiaUtils.makePotion(
                "AV_CORUSCATING_POTION",
                AlchimiaUtils.format("<gradient:#6fe3e1:#53e6a6>Coruscating Potion</gradient>"),
                Color.FUCHSIA, effects, false,
                "&aCreated from a",
                AlchimiaUtils.format("<gradient:#6274e7:#8752a3>Potion of Osmosis</gradient>"),
                "", AlchimiaUtils.itemType("Potion"));

        // {{{ Finish
        new BukkitRunnable() {
            private int layer = 2;

            @Override
            public void run() {
                World w = p.getWorld();
                Location l = p.getLocation();

                if (layer == 2) {
                    // Effects
                    w.playSound(l, Sound.BLOCK_BREWING_STAND_BREW, 1, 1);

                    // Decrease layer
                    layer--;
                } else if (layer == 1) {
                    // Effects
                    w.playSound(l, Sound.BLOCK_BEACON_ACTIVATE, 1, 1);

                    // Decrease layer
                    layer--;
                } else {
                    // Effects
                    w.playSound(l, Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1, 1);
                    w.playSound(l, Sound.ITEM_BOTTLE_FILL, 1, 1);
                    w.playSound(l, Sound.ITEM_TOTEM_USE, 0.6F, 1);
                    w.spawnParticle(Particle.END_ROD, l, 60, 1, 1, 1);

                    // Add potion to player's inventory
                    if (!p.getInventory().addItem(newPotion).isEmpty()) {
                        // Drop it on the ground if no inventory space
                        w.dropItemNaturally(l, newPotion);
                    }

                    // Cancel runnable
                    this.cancel();
                }
            }
        }.runTaskTimer(AlchimiaVitae.i(), 0, 30);
        // }}}
    }
    // }}}

}

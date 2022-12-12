package me.apeiros.alchimiavitae.setup.items.potions;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PotionOfOsmosis extends SimpleSlimefunItem<ItemUseHandler> {

    public PotionOfOsmosis(ItemGroup c) {

        super(c, Items.POTION_OF_OSMOSIS, Utils.RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_BLOCK), Items.EXP_CRYSTAL,
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), Items.GOOD_ESSENCE,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.ILLUMIUM
        });

    }

    @NotNull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            // Player & item
            Player p = e.getPlayer();
            ItemStack potion = e.getItem();

            // If player has no effects
            if (p.getActivePotionEffects().isEmpty()) {
                p.sendMessage(Utils.format("<red>There are no effects to absorb!"));
                return;
            }

            // Collection of player's effects
            Collection<PotionEffect> playerEffectsList = p.getActivePotionEffects();

            // Remove the player's current potion effects
            for (PotionEffect eff : playerEffectsList) {
                p.removePotionEffect(eff.getType());
            }

            // Make a new potion
            ItemStack newPotion = Utils.makePotion(Utils.format("<gradient:#6fe3e1:#53e6a6>Coruscating Potion</gradient>"), Color.FUCHSIA, playerEffectsList, false);
            PotionMeta meta = (PotionMeta) newPotion.getItemMeta();

            // Make a lore
            List<String> lore = new ArrayList<>();
            lore.add(Utils.format("<green>Created from a"));
            lore.add(Utils.format("<gradient:#6274e7:#8752a3>Potion of Osmosis</gradient>"));

            // Set the lore
            meta.setLore(lore);

            // Set item meta
            newPotion.setItemMeta(meta);

            // Remove potion of osmosis
            p.getInventory().removeItem(potion);

            // Effects
            // Layer 0
            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);

            // Layer 1
            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);

                // Layer 2
                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1, 1);
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);
                    p.getWorld().playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 0.6F, 1);
                    p.getWorld().spawnParticle(Particle.END_ROD, p.getLocation(), 60, 1, 1, 1);

                    // Message
                    p.sendMessage(Utils.format("<green>Successfully bottled your active effects into a potion!"));

                    // Add new potion
                    p.getInventory().addItem(newPotion);
                }, 30);
            }, 30);
        };
    }
}

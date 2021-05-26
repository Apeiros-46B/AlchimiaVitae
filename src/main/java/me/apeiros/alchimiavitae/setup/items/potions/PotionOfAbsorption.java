package me.apeiros.alchimiavitae.setup.items.potions;

import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.PotionUtils;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.Category;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static me.apeiros.alchimiavitae.AlchimiaVitae.mm;

public class PotionOfAbsorption extends SimpleSlimefunItem<ItemUseHandler> {

    public PotionOfAbsorption(Category c) {

        super(c, Items.POTION_OF_ABSORPTION, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_BLOCK), Items.EXP_CRYSTAL,
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), Items.GOOD_ESSENCE,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.ILLUMIUM
        });

    }

    @NotNull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            // If stack size more than 1
            if (e.getItem().getAmount() > 1) {
                e.getPlayer().sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse("<red>You cannot stack Potions of Absorption!")));
                return;
            }

            // Make a new potion
            ItemStack item = PotionUtils.makePotion(
                    new PotionEffect[]{},
                    BukkitComponentSerializer.legacy().serialize
                            (mm.parse("<gradient:#6fe3e1:#53e6a6>Coruscating Potion</gradient>")),
                    "", Color.FUCHSIA);
            ItemMeta meta = item.getItemMeta();

            // List of player's effects
            Collection<PotionEffect> playerEffectsList = e.getPlayer().getActivePotionEffects();

            // Remove the player's current potion effects
            for (PotionEffect eff : playerEffectsList) {
                e.getPlayer().removePotionEffect(eff.getType());
            }

            // Add the player's effects to the potion
            for (PotionEffect eff : playerEffectsList) {
                ((PotionMeta) meta).addCustomEffect(eff, true);
            }

            // Make a lore
            List<String> lore = new ArrayList<>();
            lore.add(BukkitComponentSerializer.legacy().serialize(mm.parse(
                    "<green>Created from a")));
            lore.add(BukkitComponentSerializer.legacy().serialize(mm.parse(
                    "<gradient:#6274e7:#8752a3>Potion of Absorption</gradient>")));

            // Set the lore
            meta.setLore(lore);

            // Set item meta
            item.setItemMeta(meta);

            // Remove absorption potion
            e.getPlayer().getInventory().remove(e.getItem());
            
            // Effect
            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);

            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.inst(), () -> {
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
            }, 30);

            // Effect 2, message, and add new potion
            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.inst(), () -> {
                // Effect 2
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1, 1);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_TOTEM_USE, 0.6F, 1);
                e.getPlayer().getWorld().spawnParticle(Particle.END_ROD, e.getPlayer().getLocation(), 60, 1, 1, 1);

                // Message
                e.getPlayer().sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse(
                        "<green>Successfully bottled your active effects into a potion!")));

                // Add new potion
                e.getPlayer().getInventory().addItem(item);
            }, 30);
        };
    }
}

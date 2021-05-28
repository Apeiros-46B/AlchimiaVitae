package me.apeiros.alchimiavitae.utils;

import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Holds methods that can create any potion
 */
@UtilityClass
public class PotionUtils {

    /*public static SlimefunItemStack makePotion(PotionEffect[] effects, String name, String suffix, Color color) {
        return new SlimefunItemStack(
        "AV_" + ChatColor.stripColor(ChatColors.color(name.replace(" ", "_"))).
                toUpperCase() + "_" + suffix.toUpperCase().replace(" ", "_"),
        Material.POTION, name, meta -> {
            PotionMeta potionMeta = (PotionMeta) meta;

            for (PotionEffect effect : effects) {
                potionMeta.addCustomEffect(effect, true);
            }

            potionMeta.setColor(color);
        });
    }*/

    public static SlimefunItemStack makePotion(Component name, Color color, Map<PotionEffectType, int[]> effects) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(name));
        potionMeta.setColor(color);

        for (Map.Entry<PotionEffectType, int[]> e : effects.entrySet()) {
            potionMeta.addCustomEffect(new PotionEffect(e.getKey(), e.getValue()[0], e.getValue()[1], true, true, true), true);
        }

        String id = "AV_" + ChatColor.stripColor(BukkitComponentSerializer.legacy().serialize(name).toUpperCase().replace(" ", "_")) + "_POTION";
        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion);
    }

    public static SlimefunItemStack makePotion(Component name, Color color, List<PotionEffect> effects) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(name));
        potionMeta.setColor(color);

        for (PotionEffect e : effects) {
            potionMeta.addCustomEffect(e, true);
        }

        String id = "AV_" + ChatColor.stripColor(BukkitComponentSerializer.legacy().serialize(name).toUpperCase().replace(" ", "_")) + "_POTION";
        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion);
    }

    public static SlimefunItemStack makePotion(Component name, Color color, Collection<PotionEffect> effects) {
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(name));
        potionMeta.setColor(color);

        for (PotionEffect e : effects) {
            potionMeta.addCustomEffect(e, true);
        }

        String id = "AV_" + ChatColor.stripColor(BukkitComponentSerializer.legacy().serialize(name).toUpperCase().replace(" ", "_")) + "_POTION";
        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion);
    }
}

package me.apeiros.alchimiavitae.utils;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Map;

/**
 * Holds methods that can create any potion
 */
@UtilityClass
public class PotionUtils {

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

    public static SlimefunItemStack makeSplashPotion(Component name, Color color, Map<PotionEffectType, int[]> effects) {
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

        potionMeta.setDisplayName(BukkitComponentSerializer.legacy().serialize(name));
        potionMeta.setColor(color);

        for (Map.Entry<PotionEffectType, int[]> e : effects.entrySet()) {
            potionMeta.addCustomEffect(new PotionEffect(e.getKey(), e.getValue()[0], e.getValue()[1], true, true, true), true);
        }

        String id = "AV_" + ChatColor.stripColor(BukkitComponentSerializer.legacy().serialize(name).toUpperCase().replace(" ", "_")) + "_SPLASH_POTION";
        potion.setItemMeta(potionMeta);

        return new SlimefunItemStack(id, potion);
    }
}

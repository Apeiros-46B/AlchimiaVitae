package me.apeiros.alchimiavitae.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

/**
 * Map for holding matching recipes to infusions
 */
public class InfusionMap {

    private final Map<Integer, NamespacedKey> baseMap = new HashMap<>();

    public void put(final ItemStack[] k, final NamespacedKey v) {
        baseMap.put(Arrays.hashCode(k), v);
    }

    public NamespacedKey get(final ItemStack[] k) {
        return baseMap.get(Arrays.hashCode(k));
    }

}

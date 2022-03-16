package me.apeiros.alchimiavitae.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Map for holding matching recipes to Infusions
 */
public class InfusionMap {

    private final Map<Integer, NamespacedKey> baseMap = new HashMap<>();

    public void put(ItemStack[] k, NamespacedKey v) {
        baseMap.put(Arrays.hashCode(k), v);
    }

    public NamespacedKey get(ItemStack[] k) {
        return baseMap.get(Arrays.hashCode(k));
    }

}
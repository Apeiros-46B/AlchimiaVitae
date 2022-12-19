package me.apeiros.alchimiavitae.listeners.infusion;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion.Infusion;

/**
 * {@link Listener} for Auto-Replant (hoe) infusion
 */
public class HoeListener implements Listener {

    public HoeListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // {{{ Handler to prevent block breaking and drop crops (fires when a block is broken)
    @EventHandler(ignoreCancelled = true)
    public void onCropHarvest(BlockBreakEvent e) {
        // Make sure the block broken is a crop
        if (!(e.getBlock().getBlockData() instanceof Ageable))
            return;

        Ageable a = (Ageable) e.getBlock().getBlockData();

        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        // Make sure the player's tool has meta
        if (meta == null)
            return;

        // Make sure the tool has the infusion
        if (!Infusion.AUTO_REPLANT.has(meta.getPersistentDataContainer()))
            return;

        // Make sure the crop is at maximum age
        if (a.getAge() != a.getMaximumAge())
            return;

        // Cancel event (block is not broken)
        e.setCancelled(true);

        // Reset crop age to lowest
        a.setAge(0);
        e.getBlock().setBlockData(a);

        World w = p.getWorld();

        // Check if the tool has fortune
        boolean isWheat = e.getBlock().getType() == Material.WHEAT;
        boolean hasFortune = item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
        int amountToDrop = hasFortune ? item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 2 : 2;

        // Drop items
        for (ItemStack i : e.getBlock().getDrops(item, p)) {
            // Drop wheat properly
            ItemStack itemToDrop = isWheat ? new ItemStack(Material.WHEAT) : i;
            w.dropItemNaturally(e.getBlock().getLocation(), new CustomItemStack(itemToDrop, amountToDrop));
        }

        // Drop additional seeds if the crop is wheat
        if (isWheat)
            w.dropItemNaturally(e.getBlock().getLocation(), new CustomItemStack(new ItemStack(Material.WHEAT_SEEDS), 2));

        // Effects
        Particle particleToSpawn = e.getBlock().getType() == Material.NETHER_WART ? Particle.CRIMSON_SPORE : Particle.VILLAGER_HAPPY;
        w.spawnParticle(particleToSpawn, e.getBlock().getLocation(), 50, 1, 1, 1);
    }
    // }}}

}

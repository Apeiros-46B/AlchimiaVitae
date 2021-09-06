package me.apeiros.alchimiavitae.listeners.infusion;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InfusionHoeListener implements Listener {

    // Constructor
    public InfusionHoeListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Event
    @EventHandler(ignoreCancelled = true)
    public void onHarvestCrop(BlockBreakEvent e) {
        // Check if the block broken is a crop
        if (e.getBlock().getBlockData() instanceof Ageable) {
            Player p = e.getPlayer();
            Ageable a = (Ageable) e.getBlock().getBlockData();
            ItemStack item = p.getInventory().getItemInMainHand();

            // Null check
            if (p.getInventory().getItemInMainHand().getItemMeta() != null) {
                // Check if the tool has the Auto-Replant infusion
                if (p.getInventory().getItemInMainHand().getItemMeta().
                        getPersistentDataContainer().has(AltarOfInfusion.SPIKED_HOOK, PersistentDataType.BYTE)) {
                    // Check if the crop is at maximum age
                    if (a.getAge() == a.getMaximumAge()) {
                        // Cancel event
                        e.setCancelled(true);
                        e.getBlock().setBlockData(e.getBlock().getType().createBlockData());

                        // Spawn particles
                        Particle particleToSpawn = e.getBlock().getType() == Material.NETHER_WART ? Particle.CRIMSON_SPORE : Particle.VILLAGER_HAPPY;
                        e.getBlock().getWorld().spawnParticle(particleToSpawn, e.getBlock().getLocation(), 50, 1, 1, 1);

                        // Drop drops
                        if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
                            for (ItemStack i : e.getBlock().getDrops(item, p)) {
                                ItemStack itemToDrop = e.getBlock().getType() == Material.WHEAT ? new ItemStack(Material.WHEAT) : i;
                                p.getWorld().dropItemNaturally(e.getBlock().getLocation(),
                                        new CustomItemStack(itemToDrop, item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 2));
                            }
                        } else {
                            for (ItemStack i : e.getBlock().getDrops(item, p)) {
                                ItemStack itemToDrop = e.getBlock().getType() == Material.WHEAT ? new ItemStack(Material.WHEAT) : i;
                                p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new CustomItemStack(itemToDrop, 2));
                            }
                        }

                        // Drop seeds if the crop is wheat
                        if (e.getBlock().getType() == Material.WHEAT) {
                            p.getWorld().dropItemNaturally(e.getBlock().getLocation(), new CustomItemStack(new ItemStack(Material.WHEAT_SEEDS), 2));
                        }
                    }
                }
            }
        }
    }
}

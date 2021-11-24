package me.apeiros.alchimiavitae.listeners;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SheepDyeListener implements Listener {

    // Constructor
    public SheepDyeListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Fires when a player dyes a sheep
    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Sheep) {
            ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
            ItemStack offHand = e.getPlayer().getInventory().getItemInOffHand();

            if (SlimefunUtils.isItemSimilar(mainHand, Items.CONDENSED_SOUL, true) ||
                SlimefunUtils.isItemSimilar(offHand, Items.CONDENSED_SOUL, true)) {
                e.setCancelled(true);
            }
        }
    }
}
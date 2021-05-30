package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InfusionTotemRightClickEvent implements Listener {

    // Key
    private final NamespacedKey infusionTotemStorage = new NamespacedKey(AlchimiaVitae.i(), "infusion_totemstorage");

    // Constructor
    public InfusionTotemRightClickEvent(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Event
    @EventHandler(ignoreCancelled = true)
    public void onShiftRightClick(PlayerInteractEvent e) {
        if (e.getPlayer().isSneaking() && e.getPlayer().getInventory().getChestplate() != null && e.getItem() != null) {
            // Get the container
            PersistentDataContainer container = e.getPlayer().getInventory().getChestplate().getItemMeta().getPersistentDataContainer();

            if (e.getItem().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING)) &&
                    container.has(infusionTotemStorage, PersistentDataType.INTEGER)) {
                if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // Amount of totems stored in the chestplate
                    int totemsStored = container.get(infusionTotemStorage, PersistentDataType.INTEGER);

                    // Remove the totem in the hand
                    e.getPlayer().getInventory().remove(e.getItem());

                    // Increment the totemsStored variable and set it to the container
                    totemsStored++;
                    container.set(infusionTotemStorage, PersistentDataType.INTEGER, totemsStored);
                }
            }
        }
    }
}

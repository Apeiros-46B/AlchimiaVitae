package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class InfusionTotemRightClickListener implements Listener {

    // Key
    private final NamespacedKey infusionTotemStorage = new NamespacedKey(AlchimiaVitae.i(), "infusion_totemstorage");

    // Constructor
    public InfusionTotemRightClickListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Event
    @EventHandler(ignoreCancelled = true)
    public void onShiftRightClick(PlayerInteractEvent e) {
        if (e.getPlayer().isSneaking() && e.getPlayer().getInventory().getChestplate() != null && e.getItem() != null &&
                (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            // Get the container
            ItemMeta meta = e.getPlayer().getInventory().getChestplate().getItemMeta();

            // Null check
            if (meta != null) {
                PersistentDataContainer container = meta.getPersistentDataContainer();

                // Check if the chestplate has the container and that the item is a totem
                if (e.getItem().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING)) &&
                        container.has(infusionTotemStorage, PersistentDataType.INTEGER)) {
                    // Amount of totems stored in the chestplate
                    int totemsStored = container.get(infusionTotemStorage, PersistentDataType.INTEGER);

                    // Check if there are already 8 totems
                    if (totemsStored >= 8) {
                        e.getPlayer().sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>There is no more space for this Totem!")));
                    }

                    // Remove the totem in the hand
                    e.getPlayer().getInventory().getItemInMainHand().setAmount(0);

                    // Increment the totemsStored variable, set it to the container, and set the meta to the item
                    totemsStored++;
                    container.set(infusionTotemStorage, PersistentDataType.INTEGER, totemsStored);
                    e.getPlayer().getInventory().getChestplate().setItemMeta(meta);

                    // Send a message to the player
                    e.getPlayer().sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<green>Your Totem has been added to the Battery of Totems.")));
                    e.getPlayer().sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<green>There are now " + totemsStored + " Totems stored.")));

                    // Play effects
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 1, 1);
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.8F, 1);
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 1, 1);
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_TOTEM_USE, 0.2F, 1);
                    e.getPlayer().getWorld().spawnParticle(Particle.END_ROD, e.getPlayer().getLocation(), 200, 1, 2, 1);
                }
            }
        }
    }
}

package me.apeiros.alchimiavitae.listeners.infusion;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ThreadLocalRandom;

public class InfusionSwordListener implements Listener {

    // Constructor
    public InfusionSwordListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Event
    @EventHandler(ignoreCancelled = true)
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof HumanEntity) {
            // Null check
            if (((Player) e.getDamager()).getInventory().getItemInMainHand().getItemMeta() != null) {
                if (((Player) e.getDamager()).getInventory().getItemInMainHand().getItemMeta()
                        .getPersistentDataContainer().has(AltarOfInfusion.SHIELD_DISRUPTOR, PersistentDataType.BYTE)) {
                    // Generate a random number
                    ThreadLocalRandom r = ThreadLocalRandom.current();
                    int rNum = r.nextInt(4);

                    // Save shield as an ItemStack
                    ItemStack shield = new ItemStack(Material.AIR);
                    int shieldSlot = ((HumanEntity) e.getEntity()).getInventory().getHeldItemSlot();
                    if (((HumanEntity) e.getEntity()).getInventory().getItemInMainHand().getType() == Material.SHIELD) {
                        shield = ((HumanEntity) e.getEntity()).getInventory().getItemInMainHand();
                        shieldSlot = ((HumanEntity) e.getEntity()).getInventory().getHeldItemSlot();
                    } else if (((HumanEntity) e.getEntity()).getInventory().getItemInOffHand().getType() == Material.SHIELD) {
                        shield = ((HumanEntity) e.getEntity()).getInventory().getItemInMainHand();
                        shieldSlot = ((HumanEntity) e.getEntity()).getInventory().getHeldItemSlot();
                    }

                    // Conditions for disabling shield
                    if (((HumanEntity) e.getEntity()).isBlocking() && ((Player) e.getDamager()).isSprinting() && rNum <= 2) {
                        // Disable shield
                        ((HumanEntity) e.getEntity()).getInventory().remove(shield);
                        ((HumanEntity) e.getEntity()).setCooldown(Material.SHIELD, 100);
                        e.getEntity().playEffect(EntityEffect.SHIELD_BREAK);
                    } else if (((HumanEntity) e.getEntity()).isBlocking() && rNum == 0) {
                        // Disable shield
                        ((HumanEntity) e.getEntity()).getInventory().remove(shield);
                        ((HumanEntity) e.getEntity()).setCooldown(Material.SHIELD, 100);
                        e.getEntity().playEffect(EntityEffect.SHIELD_BREAK);
                    }
                }
            }
        }
    }
}

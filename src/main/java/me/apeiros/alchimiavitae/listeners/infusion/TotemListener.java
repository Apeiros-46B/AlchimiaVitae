package me.apeiros.alchimiavitae.listeners.infusion;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;

/**
 * {@link Listener} for Totem Battery (chestplate) infusion
 */
public class TotemListener implements Listener {

    public TotemListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // {{{ Handler to add totems to battery (fires on player interaction)
    // Don't ignore cancelled events
    @EventHandler(ignoreCancelled = false)
    public void onShiftRightClick(PlayerInteractEvent e) {
        // TODO: figure out what this does
        if (e.getHand() != EquipmentSlot.HAND)
            return;

        Player p = e.getPlayer();

        // Make sure the player is sneaking (SHIFT+right click)
        if (!p.isSneaking())
            return;

        Action action = e.getAction();

        // Make sure the player is right-clicking
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
            return;

        // Make sure the player has a chestplate
        if (p.getInventory().getChestplate() == null)
            return;

        // Get meta
        ItemMeta meta = p.getInventory().getChestplate().getItemMeta();

        // Make sure there is a meta
        if (meta == null)
            return;

        // Get PDC
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Make sure the chestplate has the infusion
        if (!pdc.has(AltarOfInfusion.TOTEM_BATTERY, PersistentDataType.INTEGER))
            return;

        // Get the number of totems stored in the chestplate
        int totemsStored = pdc.get(AltarOfInfusion.TOTEM_BATTERY, PersistentDataType.INTEGER);

        // Make sure the item is a totem
        if (e.getItem() == null || !e.getItem().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING))) {
            // If the item isn't a totem, inform the player of the current number of totems
            p.sendMessage(AlchimiaUtils.format(
                    "<green>There "
                    + (totemsStored == 1
                          ? "is 1 totem"
                          : "are " + totemsStored + " totems"
                      )
                    + " stored in the Battery of Totems."
                )
            );

            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 1F, 1);
            return;
        }

        // Check if there are already 8 totems
        if (totemsStored == 8) {
            p.sendMessage(AlchimiaUtils.format("<red>There is no more space for this totem!"));
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
            return;
        }

        // Remove the totem in the hand
        p.getInventory().getItemInMainHand().setAmount(0);

        // Increase the number of totems in the battery
        totemsStored++;
        pdc.set(AltarOfInfusion.TOTEM_BATTERY, PersistentDataType.INTEGER, totemsStored);
        p.getInventory().getChestplate().setItemMeta(meta);

        // Inform the player of the new number of totems
        p.sendMessage(AlchimiaUtils.format("<green>Your totem has been added to the Battery of Totems."));
        p.sendMessage(AlchimiaUtils.format(
                "<green>There "
                + (totemsStored == 1
                      ? "is now 1 totem"
                      : "are now " + totemsStored + " totems"
                  )
                + " stored."
            )
        );

        // Effects
        World w = p.getWorld();
        Location l = p.getLocation();

        w.playSound(l, Sound.ITEM_ARMOR_EQUIP_GOLD, 1, 1);
        w.playSound(l, Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
        w.playSound(l, Sound.BLOCK_BEACON_ACTIVATE, 0.8F, 1);
        w.playSound(l, Sound.ENTITY_EVOKER_PREPARE_ATTACK, 1, 1);
        w.playSound(l, Sound.ITEM_TOTEM_USE, 0.2F, 1);
        w.spawnParticle(Particle.END_ROD, l, 200, 1, 2, 1);
    }
    // }}}

    // {{{ Handler to resurrect player (fires on damage)
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        // Make sure the damaged entity is a player
        if (!(e.getEntity() instanceof Player))
            return;

        // Make sure the damage is not from the void
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID)
            return;

        Player p = (Player) e.getEntity();

        // Make sure the damage was fatal
        if (p.getHealth() - e.getFinalDamage() > 0)
            return;

        ItemStack chestplate = p.getInventory().getChestplate();

        // Make sure the player has a chestplate
        if (chestplate == null)
            return;

        ItemMeta meta = chestplate.getItemMeta();

        // Make sure the chestplate has meta
        if (meta == null)
            return;

        // Get PDC
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Make sure the chestplate has the infusion
        if (!pdc.has(AltarOfInfusion.TOTEM_BATTERY, PersistentDataType.INTEGER))
            return;

        // Make sure there are totems stored
        if (pdc.get(AltarOfInfusion.TOTEM_BATTERY, PersistentDataType.INTEGER) <= 0)
            return;

        // Cancel damage
        e.setCancelled(true);

        // Get the number of totems stored in the chestplate
        int totemsStored = pdc.get(AltarOfInfusion.TOTEM_BATTERY, PersistentDataType.INTEGER);

        // Decrease the number of totems in the chestplate
        totemsStored--;
        pdc.set(AltarOfInfusion.TOTEM_BATTERY, PersistentDataType.INTEGER, totemsStored);
        p.getInventory().getChestplate().setItemMeta(meta);

        // Set health to half a heart, add absorption, and add potion effects
        p.setHealth(1);
        p.setAbsorptionAmount(4);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 900, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, 1));

        // Inform the player of remaining totems
        String color = switch (totemsStored) {
            default -> "<green>";
            case 3 -> "<yellow>";
            case 2 -> "<gold>";
            case 1 -> "<red>";
            case 0 -> "<dark_red>";
        };

        p.sendMessage(AlchimiaUtils.format(
                color + "There "
                + (totemsStored == 1
                      ? "is 1 totem"
                      : "are " + (totemsStored == 0 ? "no" : totemsStored) + " totems"
                  )
                + " left in the Battery of Totems."
            )
        );

        // Effects
        p.playEffect(EntityEffect.TOTEM_RESURRECT);
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.5F, 1F);
    }
    // }}}

}

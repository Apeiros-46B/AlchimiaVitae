package me.apeiros.alchimiavitae.listeners.infusion;

import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.items.crafters.AltarOfInfusion;
import me.apeiros.alchimiavitae.utils.Utils;

/**
 * {@link Listener} for Totem Battery (chestplate) infusion
 */
public class TotemListener implements Listener {

    public TotemListener(AlchimiaVitae p) {
        p.getServer().getPluginManager().registerEvents(this, p);
    }

    // Click event
    @EventHandler(ignoreCancelled = true)
    public void onShiftRightClick(PlayerInteractEvent e) {
        if (e.getPlayer().isSneaking() && e.getPlayer().getInventory().getChestplate() != null &&
                e.getHand() == EquipmentSlot.HAND && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            // Get the container
            ItemMeta meta = e.getPlayer().getInventory().getChestplate().getItemMeta();

            // Null check
            if (meta != null) {
                PersistentDataContainer pdc = meta.getPersistentDataContainer();

                // Check if the chestplate has the container
                if (pdc.has(AltarOfInfusion.TOTEM_STORAGE, PersistentDataType.INTEGER)) {
                    // Amount of totems stored in the chestplate
                    int totemsStored = pdc.get(AltarOfInfusion.TOTEM_STORAGE, PersistentDataType.INTEGER);
                    Player p = e.getPlayer();

                    // Check if the item is a totem
                    if (e.getItem() != null) {
                        if (e.getItem().isSimilar(new ItemStack(Material.TOTEM_OF_UNDYING))) {
                            // Check if there are already 8 totems
                            if (totemsStored >= 8) {
                                p.sendMessage(Utils.format("<red>There is no more space for this Totem!"));
                                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
                                return;
                            }

                            // Remove the totem in the hand
                            p.getInventory().getItemInMainHand().setAmount(0);

                            // Increment the totemsStored variable, set it to the container, and set the meta to the item
                            totemsStored++;
                            pdc.set(AltarOfInfusion.TOTEM_STORAGE, PersistentDataType.INTEGER, totemsStored);
                            p.getInventory().getChestplate().setItemMeta(meta);

                            // Send a message to the player
                            p.sendMessage(Utils.format("<green>Your Totem has been added to the Battery of Totems."));
                            p.sendMessage(Utils.format(totemsStored == 1 ? "<green>There is now 1 Totem stored." : "<green>There are now " + totemsStored + " Totems stored."));

                            // Play effects
                            p.getWorld().playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GOLD, 1, 1);
                            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);
                            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 0.8F, 1);
                            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 1, 1);
                            p.getWorld().playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, 0.2F, 1);
                            p.getWorld().spawnParticle(Particle.END_ROD, e.getPlayer().getLocation(), 200, 1, 2, 1);
                        }
                    } else {
                        p.sendMessage(Utils.format(totemsStored == 1 ? "<green>There is 1 Totem stored in the Battery of Totems." : "<green>There are " + totemsStored + " Totems stored in the Battery of Totems."));
                        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_AMBIENT, 1F, 1);
                    }
                }
            }
        }
    }

    // Death event
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if (!e.getCause().equals(EntityDamageEvent.DamageCause.VOID) && e.getEntity() instanceof Player p) {

            if (p.getInventory().getChestplate() != null) {
                ItemMeta meta = p.getInventory().getChestplate().getItemMeta();

                // Null check
                if (meta != null) {
                    // Get container
                    PersistentDataContainer container = meta.getPersistentDataContainer();

                    // Check if the chestplate has the infusion
                    if (container.has(AltarOfInfusion.TOTEM_STORAGE, PersistentDataType.INTEGER)) {
                        if (container.get(AltarOfInfusion.TOTEM_STORAGE, PersistentDataType.INTEGER) > 0) {
                            if (p.getHealth() - e.getFinalDamage() <= 0) {
                                // The amount of totems stored in the chestplate
                                int totemsStored = container.get(AltarOfInfusion.TOTEM_STORAGE, PersistentDataType.INTEGER);

                                // Cancel the damage event
                                e.setCancelled(true);

                                // Decrement the totemsStored variable and set it to the container
                                totemsStored--;
                                container.set(AltarOfInfusion.TOTEM_STORAGE, PersistentDataType.INTEGER, totemsStored);
                                p.getInventory().getChestplate().setItemMeta(meta);

                                // Add potion effects, add absorption, and heal by half a heart
                                p.setHealth(1);
                                p.setAbsorptionAmount(4);
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 900, 2));
                                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, 1));

                                // Totem visual effects
                                p.playEffect(EntityEffect.TOTEM_RESURRECT);

                                // Custom effects
                                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.5F, 1F);

                                // Send a message to the player with totem amount
                                if (totemsStored >= 4) {
                                    p.sendMessage(Utils.format("<green>There are " + totemsStored + " Totems left in the Battery of Totems."));
                                } else if (totemsStored >= 2) {
                                    p.sendMessage(Utils.format("<yellow>There are " + totemsStored + " Totems left in the Battery of Totems."));
                                } else if (totemsStored == 1) {
                                    p.sendMessage(Utils.format("<red>There is 1 Totem left in the Battery of Totems."));
                                } else {
                                    p.sendMessage(Utils.format("<dark_red>There are no Totems left in the Battery of Totems!"));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

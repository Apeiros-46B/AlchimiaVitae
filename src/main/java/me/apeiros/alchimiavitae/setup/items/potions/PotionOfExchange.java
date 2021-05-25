package me.apeiros.alchimiavitae.setup.items.potions;

import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class PotionOfExchange extends SlimefunItem implements Listener {

    public PotionOfExchange(Category c, AlchimiaVitae p) {

        super(c, Items.POTION_OF_EXCHANGE, RecipeTypes.PLANT_INFUSION_CHAMBER_TYPE, new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_BLOCK), Items.EXP_CRYSTAL,
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), Items.GOOD_ESSENCE,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.ILLUMIUM
        });

        p.getServer().getPluginManager().registerEvents(this, p);

    }

    @EventHandler(ignoreCancelled = true)
    public void onPotionDrink(PlayerItemConsumeEvent e) {
        if (e.getItem().getItemMeta().getPersistentDataContainer().has(
                new NamespacedKey(AlchimiaVitae.inst(), "exchange_potion"), PersistentDataType.BYTE)) {
            e.setCancelled(true);
            List<PotionEffect> playerEffectsList = (List<PotionEffect>) e.getPlayer().getActivePotionEffects();
            List<PotionEffect> potionEffectsList = ((PotionMeta) e.getItem().getItemMeta()).getCustomEffects();

            // Remove the player's current potion effects
            for (PotionEffect eff : playerEffectsList) {
                e.getPlayer().removePotionEffect(eff.getType());
            }

            // Add the potion's effects to the player
            for (PotionEffect eff : potionEffectsList) {
                e.getPlayer().addPotionEffect(eff);
            }

            // Remove the potion's current potion effects
            for (PotionEffect eff : potionEffectsList) {
                ((PotionMeta) e.getItem().getItemMeta()).removeCustomEffect(eff.getType());
            }

            // Add the player's effects to the potion
            for (PotionEffect eff : playerEffectsList) {
                ((PotionMeta) e.getItem().getItemMeta()).addCustomEffect(eff, true);
            }
        }
    }
}

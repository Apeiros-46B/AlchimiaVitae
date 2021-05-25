package me.apeiros.alchimiavitae.setup.items.potions;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class PotionOfExchange extends SlimefunItem implements Listener {

    public PotionOfExchange(Category c, AlchimiaVitae p) {

        super(c, Items.EVIL_MAGIC_PLANT, RecipeTypes.PLANT_INFUSION_CHAMBER_TYPE, new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.HONEY_BLOCK), Items.EXP_CRYSTAL,
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), Items.GOOD_ESSENCE,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.ILLUMIUM
        });

    }

    @EventHandler(ignoreCancelled = true)
    public void onPotionDrink(PlayerItemConsumeEvent e) {
        if (SlimefunUtils.isItemSimilar(e.getItem(), Items.POTION_OF_EXCHANGE, false, false)) {
            e.setCancelled(true);
            Collection<PotionEffect> effectsList = e.getPlayer().getActivePotionEffects();

            for (PotionEffect eff : effectsList) {
                e.getPlayer().removePotionEffect(eff.getType());
            }


        }
    }
}

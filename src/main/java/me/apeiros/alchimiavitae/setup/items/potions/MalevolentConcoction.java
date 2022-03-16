package me.apeiros.alchimiavitae.setup.items.potions;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;

public class MalevolentConcoction extends SlimefunItem implements Listener {

    public MalevolentConcoction(ItemGroup c) {

        super(c, Items.MALEVOLENT_CONCOCTION, Utils.RecipeTypes.ORNATE_CAULDRON_TYPE, new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.BONE_BLOCK),
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), new ItemStack(Material.LAVA_BUCKET),
                Items.DARKSTEEL, Items.CONDENSED_SOUL, new ItemStack(Material.ROTTEN_FLESH)
        });

        AlchimiaVitae p = AlchimiaVitae.i();
        p.getServer().getPluginManager().registerEvents(this, p);

    }

    @EventHandler(ignoreCancelled = true)
    public void onPotionSplash(PotionSplashEvent e) {
        if (SlimefunUtils.isItemSimilar(e.getEntity().getItem(), Items.MALEVOLENT_CONCOCTION, true, false)) {
            e.getEntity().getWorld().spawnParticle(Particle.SPELL_WITCH, e.getEntity().getLocation(), 75, 1, 1, 1);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 0.4F, 1);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1, 1);

            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                e.getEntity().getWorld().spawnParticle(Particle.WARPED_SPORE, e.getEntity().getLocation(), 200, 3, 1, 3);
                e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 1, 1);
                e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 1, 1);
            }, 10);
        }
    }
}

package me.apeiros.alchimiavitae.setup.items.potions;

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class BenevolentBrew extends SlimefunItem implements Listener {

    public BenevolentBrew(Category c, AlchimiaVitae p) {

        super(c, Items.BENEVOLENT_BREW, RecipeTypes.ORNATE_CAULDRON_TYPE, new ItemStack[] {
                Items.EXP_CRYSTAL, new ItemStack(Material.LILAC), new ItemStack(Material.CORNFLOWER),
                Items.GOOD_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.TOTEM_OF_UNDYING),
                Items.ILLUMIUM, new ItemStack(Material.LILY_OF_THE_VALLEY), new ItemStack(Material.POPPY)
        });

        p.getServer().getPluginManager().registerEvents(this, p);

    }

    @EventHandler(ignoreCancelled = true)
    public void onPotionDrink(PlayerItemConsumeEvent e) {
        if (SlimefunUtils.isItemSimilar(e.getItem(), Items.BENEVOLENT_BREW, true, false)) {
            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_BOTTLE_EMPTY, 1, 1);
            e.getPlayer().setAbsorptionAmount(e.getPlayer().getAbsorptionAmount() + AlchimiaVitae.i().getConfig().getDouble(
                    "options.potions.benevolent-brew.absorption-halfhearts"));

            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                e.getPlayer().getWorld().spawnParticle(Particle.TOTEM, e.getPlayer().getLocation(), 200, 1, 1, 1);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_TOTEM_USE, 0.4F, 1);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);

                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    e.getPlayer().getWorld().spawnParticle(Particle.BLOCK_CRACK, e.getPlayer().getLocation(), 200, 3, 3, 3, Material.EMERALD_BLOCK.createBlockData());
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1, 1);
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                }, 30);
            }, 30);
        }
    }
}

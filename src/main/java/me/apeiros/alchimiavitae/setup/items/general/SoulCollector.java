package me.apeiros.alchimiavitae.setup.items.general;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.WeaponUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;

public class SoulCollector extends SlimefunItem {

    public SoulCollector(ItemGroup ig) {
        super(ig, AlchimiaItems.SOUL_COLLECTOR, RecipeType.ANCIENT_ALTAR, new ItemStack[] {
                SlimefunItems.EARTH_RUNE, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.AIR_RUNE,
                SlimefunItems.WATER_RUNE, SlimefunItems.NECROTIC_SKULL, SlimefunItems.FIRE_RUNE,
                SlimefunItems.ESSENCE_OF_AFTERLIFE, new ItemStack(Material.TOTEM_OF_UNDYING), SlimefunItems.ESSENCE_OF_AFTERLIFE
        });
    }

    // {{{ Handler to prevent use on players
    @Nonnull
    private WeaponUseHandler getWeaponUseHandler() {
        return (e, p, item) -> {
            if (!(e.getEntity() instanceof Player))
                return;

            // The Soul Collector cannot be used on players
            e.setCancelled(true);
            p.sendMessage(AlchimiaUtils.format("<red>You cannot hurt a player using the Soul Collector!"));
            p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
        };
    }
    // }}}

    // {{{ Handler to drop souls and multiply experience
    @Nonnull
    private EntityKillHandler getEntityKillHandler() {
        return (e, en, p, item) -> {
            // Collect souls and multiply dropped EXP by 3 (1 in the case of an Ender Dragon)
            ThreadLocalRandom rand = ThreadLocalRandom.current();

            // 1/3 chance
            if (rand.nextInt(3) != 0)
                return;

            int souls = 1;
            int expMultiplier = 3;

            // Get number of souls to drop and how much to multiply dropped experience by
            switch (e.getEntityType()) {
                case WITHER -> souls = 1 + rand.nextInt(15); // max 15
                case WITHER_SKELETON -> souls = 1 + rand.nextInt(5); // max 5
                case ENDER_DRAGON -> expMultiplier = 1;
                default -> {}
            }

            // Add souls to drops
            for (int i = 0; i < souls; i++) {
                e.getDrops().add(AlchimiaItems.CONDENSED_SOUL.clone());
            }

            // Multiple dropped experience
            e.setDroppedExp(e.getDroppedExp() * expMultiplier);

            // Effect
            p.playSound(p.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.4F, 1);
        };
    }
    // }}}

    // {{{ Register handlers
    @Override
    public void preRegister() {
        this.addItemHandler(getWeaponUseHandler());
        this.addItemHandler(getEntityKillHandler());
    }
    // }}}

}

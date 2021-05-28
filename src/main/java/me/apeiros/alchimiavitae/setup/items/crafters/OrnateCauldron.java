package me.apeiros.alchimiavitae.setup.items.crafters;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.ChestMenuItems;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class OrnateCauldron extends AbstractContainer {
    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};

    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] CRAFT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};

    private static final int[] CRAFT_BUTTON = {15, 16, 24, 25, 33, 34};

    public static final Map<MultiInput, ItemStack> RECIPES = new HashMap<>();

    public OrnateCauldron(Category c) {

        super(c, Items.ORNATE_CAULDRON, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, Items.DIVINE_ALTAR, Items.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        });

    }

    @NotNull
    @Override
    protected int[] getTransportSlots(@NotNull DirtyChestMenu dirtyChestMenu, @NotNull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

    @Override
    protected void tick(@NotNull Block block) {

    }

    @Override
    protected void setupMenu(@NotNull BlockMenuPreset blockMenuPreset) {
        // Input background
        for (int slot : IN_BG) {
            blockMenuPreset.addItem(slot, ChestMenuItems.IN_BG, ChestMenuUtils.getEmptyClickHandler());
        }

        // Input slots
        for (int slot : IN_SLOTS) {
            blockMenuPreset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 44);
        }

        // Craft button background
        for (int slot : CRAFT_BG) {
            blockMenuPreset.addItem(slot, ChestMenuItems.CRAFT_BG, ChestMenuUtils.getEmptyClickHandler());
        }

        // Craft button
        for (int slot : CRAFT_BUTTON) {
            blockMenuPreset.addItem(slot, ChestMenuItems.CRAFT_BTN);
        }
    }

    @Override
    protected void onNewInstance(@NotNull BlockMenu menu, @NotNull Block b) {
        // Spawn ender particles
        b.getWorld().spawnParticle(Particle.END_ROD, b.getLocation(), 100, 3, 3, 3);

        // Craft button click handler
        for (int slot : CRAFT_BUTTON) {
            menu.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> {
                // Craft item
                craft(b, menu, player);
                return false;
            });
        }

        // Menu close handler
        menu.addMenuCloseHandler(player -> menu.dropItems(player.getLocation(), IN_SLOTS));
    }

    public void craft(@NotNull Block b, @NotNull BlockMenu inv, @NotNull Player p) {
        // Get expected output
        ItemStack output = RECIPES.get(new MultiInput(inv, IN_SLOTS));

        // Invalid recipe
        if (output == null) {
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>That recipe is invalid!")));
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>Please try again.")));
            return;
        }

        // Consume items
        for (int slot : IN_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot, 1);
            }
        }

        // First pre-craft effect burst
        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
            b.getWorld().spawnParticle(Particle.SPELL_WITCH, b.getLocation(), 2, 1, 1, 1);

            // Pre-craft effects
            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
                b.getWorld().playSound(b.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1, 1);
                b.getWorld().spawnParticle(Particle.CRIT_MAGIC, b.getLocation(), 200, 1, 1, 1);

                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    // Post-craft effects
                    b.getWorld().playSound(b.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.5F, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, 2, 1);
                    b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 1, 0.1, 0.1, 0.1);
                    b.getWorld().spawnParticle(Particle.END_ROD, b.getLocation(), 200, 0.1, 4, 0.1);

                    // Drop item
                    b.getWorld().dropItemNaturally(b.getLocation().add(0, 2, 0), output.clone()).setGlowing(true);

                    // Send message
                    p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse(
                            "<gradient:#50fa75:#3dd2ff>Successful brew!</gradient>")));
                }, 30);
            }, 30);
        }, 30);
    }
}

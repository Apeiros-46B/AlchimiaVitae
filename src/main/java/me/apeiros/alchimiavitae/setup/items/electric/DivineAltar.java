package me.apeiros.alchimiavitae.setup.items.electric;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.ChestMenuItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
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

import static me.apeiros.alchimiavitae.AlchimiaVitae.mm;

public class DivineAltar extends AbstractContainer {

    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};

    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] CRAFT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};

    private static final int[] CRAFT_BUTTON = {15, 16, 24, 25, 33, 34};
    private static final int OUT_SLOT = 24;

    private static int timer = 0;
    private static int count = 0;

    public static final Map<MultiInput, ItemStack> RECIPES = new HashMap<>();

    public DivineAltar(Category c) {

        super(c, Items.DIVINE_ALTAR, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.ELECTRIC_MOTOR, Items.EXP_CRYSTAL,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.BLISTERING_INGOT_3,
                SlimefunItems.ANCIENT_PEDESTAL, SlimefunItems.HEATED_PRESSURE_CHAMBER_2, SlimefunItems.ANCIENT_PEDESTAL
        });

    }

    @NotNull
    @Override
    protected int[] getTransportSlots(@NotNull DirtyChestMenu dirtyChestMenu, @NotNull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

    @Override
    protected void tick(@NotNull Block block) {
        // Spawn Standard Galactic symbol particles
        block.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation(), 30, 0.5, 0.5, 0.5);
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
        b.getWorld().spawnParticle(Particle.REVERSE_PORTAL, b.getLocation(), 100, 0.5, 0.5, 0.5);

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
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse("<red>That recipe is invalid!")));
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse("<red>Please try again.")));
            return;
        }

        // Consume items
        for (int slot : IN_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot, 1);
            }
        }

        // Pre-craft effects
        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.inst(), () -> {
            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);
        }, 30);

        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.inst(), () -> {
            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);
        }, 30);

        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.inst(), () -> {
            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);
        }, 30);

        // Post-craft effects
        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.inst(), () -> {
            // Post-craft effects
            b.getWorld().strikeLightningEffect(b.getLocation().add(0, 1, 0));
            b.getWorld().playSound(b.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 5, 0.1, 0.1, 0.1);
            b.getWorld().spawnParticle(Particle.REVERSE_PORTAL, b.getLocation(), 300, 2, 2, 2);

            // Drop item
            b.getWorld().dropItemNaturally(b.getLocation().add(0, 2, 0), output.clone()).setGlowing(true);

            // Send message
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse(
                    "<gradient:#50fa75:#3dd2ff>Successful craft!</gradient>")));
        }, 30);
    }
}

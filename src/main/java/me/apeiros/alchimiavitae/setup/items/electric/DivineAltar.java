package me.apeiros.alchimiavitae.setup.items.electric;

import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.ChestMenuItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
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

    private static final int[] OUT_SLOTS = {15, 16, 24, 25, 33, 34};

    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] OUT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};

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
        block.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, block.getLocation(), 30, 0.5, 0.5, 0.5);
    }

    @Override
    protected void setupMenu(@NotNull BlockMenuPreset blockMenuPreset) {
        // Input background
        for (int slot : IN_BG) {
            blockMenuPreset.addItem(slot, ChestMenuItems.IN_BG_CLICK_CRAFT);
        }

        // Input slots
        for (int slot : IN_SLOTS) {
            blockMenuPreset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 44);
        }

        // Output background
        for (int slot : OUT_BG) {
            blockMenuPreset.addItem(slot, ChestMenuItems.OUT_BG_CLICK_CRAFT);
        }

        // Output slots
        for (int slot : OUT_SLOTS) {
            blockMenuPreset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 44);
        }

        blockMenuPreset.setSize(45);
    }

    @Override
    protected void onNewInstance(@NotNull BlockMenu menu, @NotNull Block b) {
        b.getWorld().spawnParticle(Particle.REVERSE_PORTAL, b.getLocation(), 40, 0.5, 0.5, 0.5);
        for (int slot : IN_BG) {
            menu.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> {
                craft(b, menu, player);
                return false;
            });
        }

        for (int slot : OUT_BG) {
            menu.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> {
                craft(b, menu, player);
                return false;
            });
        }
    }

    public void craft(@NotNull Block b, @NotNull BlockMenu inv, @NotNull Player p) {
        // Get expected output
        ItemStack output = RECIPES.get(new MultiInput(inv, IN_SLOTS));

        if (output == null) {
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse("<red>That recipe is invalid!")));
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse("<red>Please try again.")));
            return;
        }

        // Consume items
        for (int slot : IN_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot, inv.getItemInSlot(slot).getAmount());
            }
        }

        // Output
        inv.addItem(firstEmptySlot(inv, OUT_SLOTS), output.clone());

        // Send message
        p.sendMessage(BukkitComponentSerializer.legacy().serialize(mm.parse(
                "<gradient:#50fa75:#3dd2ff>Successful craft!</gradient>")));

        // Effects
        b.getWorld().strikeLightningEffect(b.getLocation());
        b.getWorld().playSound(b.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1, 1);
        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
        b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 40, 0.2, 0.2, 0.2);
    }

    private int firstEmptySlot(@NotNull BlockMenu inv, @NotNull int[] slots) {
        int empty = -1;

        for (int slot : slots) {
            if (inv.getItemInSlot(slot) == null) {
                empty = slot;
                break;
            }
        }

        return empty;
    }
}

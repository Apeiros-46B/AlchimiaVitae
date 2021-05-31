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
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class AltarOfInfusion extends AbstractContainer {

    private final NamespacedKey chestplateInfusionTotemStorage = new NamespacedKey(AlchimiaVitae.i(), "infusion_totemstorage");
    private final NamespacedKey hoeInfusionAutoReplant = new NamespacedKey(AlchimiaVitae.i(), "infusion_autoreplant");
    private final NamespacedKey bowInfusionTrueAim = new NamespacedKey(AlchimiaVitae.i(), "infusion_trueaim");
    private final NamespacedKey bowInfusionVolatile = new NamespacedKey(AlchimiaVitae.i(), "infusion_volatile");
    private final NamespacedKey bowInfusionForceful = new NamespacedKey(AlchimiaVitae.i(), "infusion_forceful");
    private final NamespacedKey bowInfusionHealing = new NamespacedKey(AlchimiaVitae.i(), "infusion_healing");
    private final NamespacedKey axeInfusionDestructiveCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_destructivecrits");
    private final NamespacedKey axeInfusionPhantomCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_phantomcrits");

    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};

    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] CRAFT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};

    private static final int[] CRAFT_BUTTON = {15, 16, 24, 25, 33, 34};

    public static final Map<MultiInput, ItemStack> RECIPES = new HashMap<>();

    public AltarOfInfusion(Category c) {

        super(c, Items.ALTAR_OF_INFUSION, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.WITHER_PROOF_GLASS, Items.EXP_CRYSTAL,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, Items.DIVINE_ALTAR, SlimefunItems.BLISTERING_INGOT_3
        });

    }

    @NotNull
    @Override
    protected int[] getTransportSlots(@NotNull DirtyChestMenu dirtyChestMenu, @NotNull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

    @Override
    protected void tick(@NotNull Block block) {
        // Spawn end rod particles
        block.getWorld().spawnParticle(Particle.END_ROD, block.getLocation(), 3, 0.5, 0.5, 0.5);
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
        // Spawn end rod particles
        b.getWorld().spawnParticle(Particle.END_ROD, b.getLocation(), 100, 0.5, 0.5, 0.5);

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

    private void craft(@NotNull Block b, @NotNull BlockMenu inv, @NotNull Player p) {
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

        // Pre-craft effects
        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1, 1);
        b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1.5F, 1);
        b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);

        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);
            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_CONDUIT_ATTACK_TARGET, 0.5F, 1);
            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
            b.getWorld().playSound(b.getLocation(), Sound.ITEM_TOTEM_USE, 0.1F, 1);
            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
            b.getWorld().playSound(b.getLocation(), Sound.BLOCK_LODESTONE_PLACE, 1.5F, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);

            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_CONDUIT_ATTACK_TARGET, 1.5F, 1);
                b.getWorld().playSound(b.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
                b.getWorld().playSound(b.getLocation(), Sound.ITEM_TOTEM_USE, 0.3F, 1);
                b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);

                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    // Post-craft effects
                    b.getWorld().playSound(b.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.5F, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ITEM_TOTEM_USE, 0.5F, 1);
                    b.getWorld().spawnParticle(Particle.END_ROD, b.getLocation(), 5, 0, 8, 0);
                    b.getWorld().spawnParticle(Particle.PORTAL, b.getLocation(), 300, 2, 2, 2);

                    // Drop item
                    b.getWorld().dropItemNaturally(b.getLocation().add(0, 2, 0), output.clone()).setGlowing(true);

                    // Send message
                    p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse(
                            "<gradient:#50fa75:#3dd2ff>Your item has been infused!</gradient>")));
                }, 30);
            }, 30);
        }, 30);
    }

    private void infuse(@NotNull BlockMenu inv, @NotNull ItemStack tool) {
        String infuseType = "";
        ItemStack[] infuseItems = new ItemStack[8];

        // Index
        int index = 0;
        for (int slot : IN_SLOTS) {
            // Make sure that the slot is not the middle (item to be infused) slot
            if (slot != 20) {
                // Add the item in the inventory to the array
                infuseItems[index] = inv.getItemInSlot(slot);
                // Increment index
                index++;
            }
        }

        // Set the infuseType
        if (tool.getType().isItem()) {
            if (tool.getType().equals(Material.GOLDEN_HOE) ||
                    tool.getType().equals(Material.IRON_HOE) ||
                    tool.getType().equals(Material.DIAMOND_HOE) ||
                    tool.getType().equals(Material.NETHERITE_HOE)) {
                infuseType = "hoe";
            } else if (tool.getType().equals(Material.GOLDEN_AXE) ||
                    tool.getType().equals(Material.IRON_AXE) ||
                    tool.getType().equals(Material.DIAMOND_AXE) ||
                    tool.getType().equals(Material.NETHERITE_AXE)) {
                infuseType = "axe";
            } else if (tool.getType().equals(Material.GOLDEN_CHESTPLATE) ||
                    tool.getType().equals(Material.IRON_CHESTPLATE) ||
                    tool.getType().equals(Material.DIAMOND_CHESTPLATE) ||
                    tool.getType().equals(Material.NETHERITE_CHESTPLATE)) {
                infuseType = "chestplate";
            } else if (tool.getType().equals(Material.BOW) ||
                    tool.getType().equals(Material.CROSSBOW)) {
                infuseType = "bow";
            }
        }



    }
}

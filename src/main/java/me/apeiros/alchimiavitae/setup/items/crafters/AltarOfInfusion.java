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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class AltarOfInfusion extends AbstractContainer {

    private final NamespacedKey chestplateInfusionTotemStorage = new NamespacedKey(AlchimiaVitae.i(), "infusion_totemstorage");
    private final NamespacedKey hoeInfusionAutoReplant = new NamespacedKey(AlchimiaVitae.i(), "infusion_autoreplant");
    private final NamespacedKey bowInfusionTrueAim = new NamespacedKey(AlchimiaVitae.i(), "infusion_trueaim");
    private final NamespacedKey bowInfusionForceful = new NamespacedKey(AlchimiaVitae.i(), "infusion_forceful");
    private final NamespacedKey bowInfusionVolatile = new NamespacedKey(AlchimiaVitae.i(), "infusion_volatile");
    private final NamespacedKey bowInfusionHealing = new NamespacedKey(AlchimiaVitae.i(), "infusion_healing");
    private final NamespacedKey axeInfusionDestructiveCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_destructivecrits");
    private final NamespacedKey axeInfusionPhantomCrits = new NamespacedKey(AlchimiaVitae.i(), "infusion_phantomcrits");

    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final int[] IN_SLOTS_EXCLUDING_MID = {10, 11, 12, 19, 21, 28, 29, 30};

    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] CRAFT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};

    private static final int[] CRAFT_BUTTON = {15, 16, 24, 25, 33, 34};

    private static final int TOOL_SLOT = 20;

    public static final Map<MultiInput, NamespacedKey> RECIPES = new HashMap<>();

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
        // Get expected Infusion
        NamespacedKey infusion = RECIPES.get(new MultiInput(inv, IN_SLOTS_EXCLUDING_MID));

        // Invalid Infusion
        if (infusion == null) {
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>That is not a valid Infusion!")));
            return;
        }

        // Check if item is valid
        if (inv.getItemInSlot(TOOL_SLOT).getType().isItem()) {
            if (inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.GOLDEN_HOE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.IRON_HOE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.DIAMOND_HOE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.NETHERITE_HOE)) {
                // Valid item
            } else if (inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.GOLDEN_AXE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.IRON_AXE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.DIAMOND_AXE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.NETHERITE_AXE)) {
                // Valid item
            } else if (inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.GOLDEN_CHESTPLATE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.IRON_CHESTPLATE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.DIAMOND_CHESTPLATE) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.NETHERITE_CHESTPLATE)) {
                // Valid item
            } else if (inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.BOW) ||
                    inv.getItemInSlot(TOOL_SLOT).getType().equals(Material.CROSSBOW)) {
                // Valid item
            } else {
                // Invalid item
                p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>You cannot infuse that tool!")));
                return;
            }
        }

        // Get the tool
        ItemStack tool = inv.getItemInSlot(TOOL_SLOT);
        if (tool == null || tool.getType().equals(Material.AIR)) {
            return;
        }

        // ItemMeta
        ItemMeta meta = tool.getItemMeta();

        // Container
        PersistentDataContainer container = meta.getPersistentDataContainer();

        // Check if tool is already infused
        if (container.has(chestplateInfusionTotemStorage, PersistentDataType.BYTE) ||
                container.has(axeInfusionDestructiveCrits, PersistentDataType.BYTE) ||
                container.has(axeInfusionPhantomCrits, PersistentDataType.BYTE) ||
                container.has(hoeInfusionAutoReplant, PersistentDataType.BYTE) ||
                container.has(bowInfusionTrueAim, PersistentDataType.BYTE) ||
                container.has(bowInfusionForceful, PersistentDataType.BYTE) ||
                container.has(bowInfusionVolatile, PersistentDataType.BYTE) ||
                container.has(bowInfusionHealing, PersistentDataType.BYTE)) {
            // Tool is already infused
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>You have already applied an Infusion to this item!")));
            return;
        }

        // Check if the tool can be infused
        if (canBeInfused(tool, infusion) && !infusion.equals(chestplateInfusionTotemStorage)) {
            // Tool can be infused and the Infusion is not the totem battery
            container.set(infusion, PersistentDataType.BYTE, (byte) 1);

            // Lore
            List<String> lore;

            // Assign lore
            lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

            // Add lines to lore
            lore.add("");
            lore.add("&7Infusion:");

            // Add infusion name to lore
            if (axeInfusionDestructiveCrits.equals(infusion)) {
                lore.add("&8› &c&lDestructive Criticals");
            } else if (axeInfusionPhantomCrits.equals(infusion)) {
                lore.add("&8› &bPhantom Criticals");
            } else if (bowInfusionTrueAim.equals(infusion)) {
                lore.add("&8› &dTrue Aim");
            } else if (bowInfusionForceful.equals(infusion)) {
                lore.add("&8› &2Forceful");
            } else if (bowInfusionVolatile.equals(infusion)) {
                lore.add("&8› &4&lVolatility");
            } else if (bowInfusionHealing.equals(infusion)) {
                lore.add("&8› &cHealing");
            } else if (hoeInfusionAutoReplant.equals(infusion)) {
                lore.add("&8› &aAutomatic Re-plant");
            }

            // Set lore and meta
            meta.setLore(lore);
            tool.setItemMeta(meta);
        } else if (canBeInfused(tool, infusion) && infusion.equals(chestplateInfusionTotemStorage)) {
            // Tool can be infused and the Infusion is the totem battery
            container.set(infusion, PersistentDataType.INTEGER, 0);

            // Lore
            List<String> lore;

            // Assign lore
            lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

            // Add lines to lore
            lore.add("");
            lore.add("&7Infusion:");

            // Add infusion name to lore
            lore.add("&8› &6&lBattery of Totems");

            // Set lore and meta
            meta.setLore(lore);
            tool.setItemMeta(meta);
        } else {
            // Tool cannot be infused
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>You cannot apply this infusion to this item!")));
            return;
        }

        // Consume items
        for (int slot : IN_SLOTS_EXCLUDING_MID) {
            inv.consumeItem(slot, 1);
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
                    b.getWorld().strikeLightningEffect(b.getLocation().add(0, 1, 0));
                    b.getWorld().playSound(b.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 0.5F, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ITEM_TOTEM_USE, 0.5F, 1);
                    b.getWorld().spawnParticle(Particle.END_ROD, b.getLocation(), 5, 0, 8, 0);
                    b.getWorld().spawnParticle(Particle.PORTAL, b.getLocation(), 300, 2, 2, 2);

                    // Send message
                    p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse(
                            "<gradient:#50fa75:#3dd2ff>Your item has been infused!</gradient>")));
                }, 30);
            }, 30);
        }, 30);
    }

    private boolean canBeInfused(@NotNull ItemStack tool, @NotNull NamespacedKey infusion) {
        // Check if the Infusion can be applied to the tool
        if (tool.getType().isItem()) {
            if (tool.getType().equals(Material.GOLDEN_HOE) ||
                    tool.getType().equals(Material.IRON_HOE) ||
                    tool.getType().equals(Material.DIAMOND_HOE) ||
                    tool.getType().equals(Material.NETHERITE_HOE) &&
                    infusion.equals(hoeInfusionAutoReplant)) {
                return true;
            } else if (tool.getType().equals(Material.GOLDEN_AXE) ||
                    tool.getType().equals(Material.IRON_AXE) ||
                    tool.getType().equals(Material.DIAMOND_AXE) ||
                    tool.getType().equals(Material.NETHERITE_AXE) &&
                    (infusion.equals(axeInfusionDestructiveCrits) ||
                    infusion.equals(axeInfusionPhantomCrits))) {
                return true;
            } else if (tool.getType().equals(Material.GOLDEN_CHESTPLATE) ||
                    tool.getType().equals(Material.IRON_CHESTPLATE) ||
                    tool.getType().equals(Material.DIAMOND_CHESTPLATE) ||
                    tool.getType().equals(Material.NETHERITE_CHESTPLATE) &&
                    infusion.equals(chestplateInfusionTotemStorage)) {
                return true;
            } else {
                return (tool.getType().equals(Material.BOW) ||
                    tool.getType().equals(Material.CROSSBOW)) &&
                    (infusion.equals(bowInfusionTrueAim) ||
                    infusion.equals(bowInfusionForceful) ||
                    infusion.equals(bowInfusionVolatile) ||
                    infusion.equals(bowInfusionHealing));
            }
        }

        return false;
    }
}

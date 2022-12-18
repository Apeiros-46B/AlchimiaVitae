package me.apeiros.alchimiavitae.setup.items.crafters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.machines.CraftingBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.AlchimiaItems;
import me.apeiros.alchimiavitae.utils.InfusionMap;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public class AltarOfInfusion extends CraftingBlock {

    // Infusion keys
    public static final NamespacedKey DESTRUCTIVE_CRITS = AbstractAddon.createKey("infusion_destructivecrits");
    public static final NamespacedKey PHANTOM_CRITS = AbstractAddon.createKey("infusion_phantomcrits");
    public static final NamespacedKey TRUE_AIM = AbstractAddon.createKey("infusion_trueaim");
    public static final NamespacedKey FORCEFUL = AbstractAddon.createKey("infusion_forceful");
    public static final NamespacedKey VOLATILITY = AbstractAddon.createKey("infusion_volatile");
    public static final NamespacedKey HEALING = AbstractAddon.createKey("infusion_healing");
    public static final NamespacedKey AUTO_REPLANT = AbstractAddon.createKey("infusion_autoreplant");
    public static final NamespacedKey TOTEM_BATTERY = AbstractAddon.createKey("infusion_totemstorage");
    public static final NamespacedKey KNOCKBACK = AbstractAddon.createKey("infusion_knockback");

    // Tool types
    private static final List<Material> VALID_AXE = Arrays.asList(
            Material.GOLDEN_AXE,
            Material.IRON_AXE,
            Material.DIAMOND_AXE,
            Material.NETHERITE_AXE);

    private static final List<Material> VALID_BOW = Arrays.asList(Material.BOW, Material.CROSSBOW);

    private static final List<Material> VALID_HOE = Arrays.asList(
            Material.GOLDEN_HOE,
            Material.IRON_HOE,
            Material.DIAMOND_HOE,
            Material.NETHERITE_HOE);

    private static final List<Material> VALID_CHESTPLATE = Arrays.asList(
            Material.GOLDEN_CHESTPLATE,
            Material.IRON_CHESTPLATE,
            Material.DIAMOND_CHESTPLATE,
            Material.NETHERITE_CHESTPLATE);

    private static final List<Material> VALID_FISHING_ROD = Collections.singletonList(Material.FISHING_ROD);

    // Slots
    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final int[] IN_SLOTS_EXCLUDING_MID = {10, 11, 12, 19, 21, 28, 29, 30};

    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] CRAFT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};

    private static final int[] CRAFT_BUTTON = {15, 16, 24, 25, 33, 34};

    private static final int TOOL_SLOT = 20;

    // Recipes
    private static final InfusionMap recipes = new InfusionMap();

    // Constructor
    public AltarOfInfusion(ItemGroup c) {

        super(c, AlchimiaItems.ALTAR_OF_INFUSION, AlchimiaUtils.RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                AlchimiaItems.EXP_CRYSTAL, SlimefunItems.WITHER_PROOF_GLASS, AlchimiaItems.EXP_CRYSTAL,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, AlchimiaItems.DIVINE_ALTAR, SlimefunItems.BLISTERING_INGOT_3
        });

        // Get plugin and config
        AlchimiaVitae av = AlchimiaVitae.i();
        Configuration cfg = av.getConfig();

        // Get config values
        boolean destructiveCritsEnabled = cfg.getBoolean("options.infusions.infusion-destructivecrits");
        boolean phantomCritsEnabled = cfg.getBoolean("options.infusions.infusion-phantomcrits");
        boolean trueAimEnabled = cfg.getBoolean("options.infusions.infusion-trueaim");
        boolean forcefulEnabled = cfg.getBoolean("options.infusions.infusion-forceful");
        boolean volatileEnabled = cfg.getBoolean("options.infusions.infusion-volatile");
        boolean healingEnabled = cfg.getBoolean("options.infusions.infusion-healing");
        boolean autoReplantEnabled = cfg.getBoolean("options.infusions.infusion-autoreplant");
        boolean totemStorageEnabled = cfg.getBoolean("options.infusions.infusion-totemstorage");
        boolean knockbackEnabled = cfg.getBoolean("options.infusions.infusion-knockback");

        // ItemStacks
        CustomItemStack validInfuseAxe = new CustomItemStack(Material.DIAMOND_AXE, "&b&lA valid axe to infuse", "&aA gold, iron, diamond,", "&aor netherite axe will suffice");
        CustomItemStack validInfuseBow = new CustomItemStack(Material.BOW, "&b&lA valid bow to infuse", "&aA bow or crossbow will suffice");
        CustomItemStack validInfuseHoe = new CustomItemStack(Material.DIAMOND_HOE, "&b&lA valid hoe to infuse", "&aA gold, iron, diamond,", "&aor netherite hoe will suffice");
        CustomItemStack validInfuseChestplate = new CustomItemStack(Material.DIAMOND_CHESTPLATE, "&b&lA valid chestplate to infuse", "&aA gold, iron, diamond,", "&aor netherite chestplate will suffice");
        CustomItemStack validInfuseRod = new CustomItemStack(Material.FISHING_ROD, "&b&lA valid fishing rod to infuse", "&aA normal fishing rod will suffice");

        SlimefunItemStack item;

        // Register Infusions
        if (destructiveCritsEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE, new ItemStack(Material.STONECUTTER),
                    AlchimiaItems.DARKSTEEL, SlimefunItems.WITHER_PROOF_OBSIDIAN,
                    new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN, new ItemStack(Material.TNT)
            }, DESTRUCTIVE_CRITS);

            item = new SlimefunItemStack("AV_DESTRUCTIVE_CRITS_INFUSION", Material.TNT, "&c&lDestructive Criticals",
                    "&41/20 chance to give opponent Mining Fatigue III for 8 seconds on crit",
                    "&41/5 chance to give opponent Slowness I for 15 seconds on crit",
                    "&41/5 chance to give opponent Weakness I for 15 seconds on crit",
                    "&4Deals 0-5 extra damage to opponent's armor on crit");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE, new ItemStack(Material.STONECUTTER),
                    AlchimiaItems.DARKSTEEL, validInfuseAxe, SlimefunItems.WITHER_PROOF_OBSIDIAN,
                    new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN, new ItemStack(Material.TNT)
            }, item).register(av);
        }

        if (phantomCritsEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.PHANTOM_MEMBRANE),
                    AlchimiaItems.DARKSTEEL, SlimefunItems.HARDENED_GLASS,
                    new ItemStack(Material.PHANTOM_MEMBRANE), AlchimiaItems.CONDENSED_SOUL, new ItemStack(Material.PHANTOM_MEMBRANE)
            }, PHANTOM_CRITS);

            item = new SlimefunItemStack("AV_PHANTOM_CRITS_INFUSION", Material.PHANTOM_MEMBRANE, "&bPhantom Criticals",
                    "&aA small chance to deal extra",
                    "&adamage on a crit, bypassing armor");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.PHANTOM_MEMBRANE),
                    AlchimiaItems.DARKSTEEL, validInfuseAxe, SlimefunItems.HARDENED_GLASS,
                    new ItemStack(Material.PHANTOM_MEMBRANE), AlchimiaItems.CONDENSED_SOUL, new ItemStack(Material.PHANTOM_MEMBRANE)
            }, item).register(av);
        }

        if (trueAimEnabled) {
            recipes.put(new ItemStack[] {
                    SlimefunItems.SYNTHETIC_SHULKER_SHELL, SlimefunItems.INFUSED_MAGNET, SlimefunItems.STAFF_WIND,
                    AlchimiaItems.DARKSTEEL, AlchimiaItems.EXP_CRYSTAL,
                    new ItemStack(Material.SHULKER_BOX), SlimefunItems.INFUSED_ELYTRA, SlimefunItems.STEEL_THRUSTER
            }, TRUE_AIM);

            item = new SlimefunItemStack("AV_TRUE_AIM_INFUSION", Material.SHULKER_SHELL, "&dTrue Aim",
                    "&5Partially using the levitation charm", "&5Shulkers use to terminate their victims,",
                    "&5a bow infused with this magic can fire", "&5arrows that are not affected by gravity");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.SYNTHETIC_SHULKER_SHELL, SlimefunItems.INFUSED_MAGNET, SlimefunItems.STAFF_WIND,
                    AlchimiaItems.DARKSTEEL, validInfuseBow, AlchimiaItems.EXP_CRYSTAL,
                    new ItemStack(Material.SHULKER_BOX), SlimefunItems.INFUSED_ELYTRA, SlimefunItems.STEEL_THRUSTER
            }, item).register(av);
        }

        if (forcefulEnabled) {
            recipes.put(new ItemStack[] {
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.STAFF_WIND,
                    SlimefunItems.INFUSED_MAGNET, SlimefunItems.STEEL_THRUSTER,
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.TALISMAN_TRAVELLER
            }, FORCEFUL);

            item = new SlimefunItemStack("AV_FORCEFUL_INFUSION", Material.PISTON, "&2Forceful",
                    "&aThis infusion uses mechanical", "&adevices and electromagnets to accelerate",
                    "&aprojectiles to blistering speeds", "&aArrows will travel 2x further and deal more damage");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.STAFF_WIND,
                    SlimefunItems.INFUSED_MAGNET, validInfuseBow, SlimefunItems.STEEL_THRUSTER,
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.TALISMAN_TRAVELLER
            }, item).register(av);
        }

        if (volatileEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE, SlimefunItems.TALISMAN_FIRE,
                    AlchimiaItems.DARKSTEEL, SlimefunItems.LAVA_GENERATOR_2,
                    new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.LAVA_CRYSTAL
            }, VOLATILITY);

            item = new SlimefunItemStack("AV_VOLATILE_INFUSION", Material.FIRE_CHARGE, "&4&lVolatility",
                    "&cThis extremely dangerous infusion creates", "&cspheres made of pure superheated lava,",
                    "&cdelivering a mini-inferno to the target");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE, SlimefunItems.TALISMAN_FIRE,
                    AlchimiaItems.DARKSTEEL, validInfuseBow, SlimefunItems.LAVA_GENERATOR_2,
                    new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.LAVA_CRYSTAL
            }, item).register(av);
        }

        if (healingEnabled) {
            recipes.put(new ItemStack[] {
                    AlchimiaItems.BENEVOLENT_BREW, SlimefunItems.MEDICINE, SlimefunItems.VITAMINS,
                    AlchimiaItems.ILLUMIUM, new ItemStack(Material.TOTEM_OF_UNDYING),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE, SlimefunItems.MAGIC_SUGAR
            }, HEALING);

            item = new SlimefunItemStack("AV_HEALING_INFUSION", Material.REDSTONE, "&cHealing",
                    "&cThis infusion will heal hit entities", " &cand recover their &4health", "" +
                    "&aHeals for the same amount that a bow shot would damage");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    AlchimiaItems.BENEVOLENT_BREW, SlimefunItems.MEDICINE, SlimefunItems.VITAMINS,
                    AlchimiaItems.ILLUMIUM, validInfuseBow, new ItemStack(Material.TOTEM_OF_UNDYING),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE, SlimefunItems.MAGIC_SUGAR
            }, item).register(av);
        }

        if (autoReplantEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.COMPOSTER), AlchimiaItems.LIGHT_ESSENCE, new ItemStack(Material.WATER_BUCKET),
                    AlchimiaItems.ILLUMIUM, SlimefunItems.FLUID_PUMP,
                    new ItemStack(Material.BONE_BLOCK), AlchimiaItems.LIGHT_MAGIC_PLANT, new ItemStack(Material.GRINDSTONE)
            }, AUTO_REPLANT);

            item = new SlimefunItemStack("AV_AUTO_REPLANT_INFUSION", Material.WHEAT, "&aAutomatic Re-plant",
                    "&2Any fully-grown crops broken",
                    "&2with a hoe infused with this", "&2will &aautomatically &2be replanted");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.COMPOSTER), AlchimiaItems.LIGHT_ESSENCE, new ItemStack(Material.WATER_BUCKET),
                    AlchimiaItems.ILLUMIUM, validInfuseHoe, SlimefunItems.FLUID_PUMP,
                    new ItemStack(Material.BONE_BLOCK), AlchimiaItems.LIGHT_MAGIC_PLANT, new ItemStack(Material.GRINDSTONE)
            }, item).register(av);
        }

        if (totemStorageEnabled) {
            recipes.put(new ItemStack[] {
                    SlimefunItems.NECROTIC_SKULL, AlchimiaItems.CONDENSED_SOUL, AlchimiaItems.BENEVOLENT_BREW,
                    AlchimiaItems.ILLUMIUM, AlchimiaItems.EXP_CRYSTAL,
                    SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ESSENCE_OF_AFTERLIFE
            }, TOTEM_BATTERY);

            item = new SlimefunItemStack("AV_TOTEM_BATTERY_INFUSION", Material.TOTEM_OF_UNDYING, "&6&lTotem Battery",
                    "&eA built-in pocket dimension that holds the energy", "&eof up to 8 Totems of Undying",
                    "&6Store a Totem in this apparatus", "&6by &e&lShift-Right-Clicking &6with a Totem in the hand",
                    "&6while the infused chestplate is worn");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.NECROTIC_SKULL, AlchimiaItems.CONDENSED_SOUL, AlchimiaItems.BENEVOLENT_BREW,
                    AlchimiaItems.ILLUMIUM, validInfuseChestplate, AlchimiaItems.EXP_CRYSTAL,
                    SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ESSENCE_OF_AFTERLIFE
            }, item).register(av);
        }

        if (knockbackEnabled) {
            recipes.put(new ItemStack[] {
                    SlimefunItems.TALISMAN_WHIRLWIND, new ItemStack(Material.STICKY_PISTON), AlchimiaItems.EXP_CRYSTAL,
                    SlimefunItems.GRANDPAS_WALKING_STICK, new ItemStack(Material.STICKY_PISTON),
                    new ItemStack(Material.SLIME_BALL), SlimefunItems.GRANDPAS_WALKING_STICK, SlimefunItems.TALISMAN_WHIRLWIND
            }, KNOCKBACK);

            item = new SlimefunItemStack("AV_KNOCKBACK_INFUSION", Material.SLIME_BALL, "&aKnockback",
                    "&2Entities pulled by this fishing rod",
                    "&2will instead be pushed back");

            new SlimefunItem(AlchimiaUtils.ItemGroups.INFUSIONS, item, AlchimiaUtils.RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.TALISMAN_WHIRLWIND, new ItemStack(Material.STICKY_PISTON), AlchimiaItems.EXP_CRYSTAL,
                    SlimefunItems.GRANDPAS_WALKING_STICK, validInfuseRod, new ItemStack(Material.STICKY_PISTON),
                    new ItemStack(Material.SLIME_BALL), SlimefunItems.GRANDPAS_WALKING_STICK, SlimefunItems.TALISMAN_WHIRLWIND
            }, item).register(av);
        }
    }

    @Override
    protected void setup(@NotNull BlockMenuPreset blockMenuPreset) {
        // Input background
        for (int slot : IN_BG) {
            blockMenuPreset.addItem(slot, AlchimiaItems.IN_BG, ChestMenuUtils.getEmptyClickHandler());
        }

        // Input slots
        for (int slot : IN_SLOTS) {
            blockMenuPreset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 44);
        }

        // Craft button background
        for (int slot : CRAFT_BG) {
            blockMenuPreset.addItem(slot, AlchimiaItems.CRAFT_BG, ChestMenuUtils.getEmptyClickHandler());
        }

        // Craft button
        for (int slot : CRAFT_BUTTON) {
            blockMenuPreset.addItem(slot, AlchimiaItems.CRAFT_BTN);
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
    }

    @Override
    protected void onBreak(@NotNull BlockBreakEvent e, @NotNull BlockMenu menu) {
        Location l = menu.getLocation();
        menu.dropItems(l, IN_SLOTS);
    }

    @Override
    protected void craft(@NotNull Block b, @NotNull BlockMenu inv, @NotNull Player p) {
        // Get expected Infusion
        ItemStack[] input = new ItemStack[8];

        int index = 0;
        for (int i : IN_SLOTS_EXCLUDING_MID) {
            input[index] = inv.getItemInSlot(i);
            index++;
        }

        NamespacedKey infusion = recipes.get(input);

        // Invalid Infusion
        if (infusion == null) {
            p.sendMessage(AlchimiaUtils.format("<red>Invalid Infusion!"));
            return;
        }

        Material mat = inv.getItemInSlot(TOOL_SLOT).getType();

        // Check if item is valid
        if (mat.isItem()) {
            if (VALID_AXE.contains(mat) ||
                    (((VALID_BOW.contains(mat) ||
                    VALID_HOE.contains(mat) ||
                    VALID_CHESTPLATE.contains(mat)) ||
                    mat == Material.FISHING_ROD))) {
                // Valid item
            } else {
                // Invalid item
                p.sendMessage(AlchimiaUtils.format("<red>You cannot infuse that tool!"));
                return;
            }
        } else {
            // Invalid item
            p.sendMessage(AlchimiaUtils.format("<red>This item cannot be infused!"));
            return;
        }

        // Get the tool
        ItemStack tool = inv.getItemInSlot(TOOL_SLOT);
        if (tool == null || tool.getType().equals(Material.AIR)) {
            // No tool
            p.sendMessage(AlchimiaUtils.format("<red>There is nothing to infuse!"));
            return;
        }

        // ItemMeta
        ItemMeta meta = tool.getItemMeta();

        // Container
        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        // Check if tool is already infused
        if (pdc.has(DESTRUCTIVE_CRITS, PersistentDataType.BYTE) ||
                pdc.has(PHANTOM_CRITS, PersistentDataType.BYTE) ||
                pdc.has(TRUE_AIM, PersistentDataType.BYTE) ||
                pdc.has(FORCEFUL, PersistentDataType.BYTE) ||
                pdc.has(VOLATILITY, PersistentDataType.BYTE) ||
                pdc.has(HEALING, PersistentDataType.BYTE) ||
                pdc.has(TOTEM_BATTERY, PersistentDataType.INTEGER) ||
                pdc.has(AUTO_REPLANT, PersistentDataType.BYTE) ||
                pdc.has(KNOCKBACK, PersistentDataType.BYTE)) {
            // Tool is already infused
            p.sendMessage(AlchimiaUtils.format("<red>You have already applied an Infusion to this item!"));
            return;
        }

        // Check if the tool can be infused
        if (canBeInfused(tool, infusion) && !infusion.equals(TOTEM_BATTERY)) {
            // Tool can be infused and the Infusion is not the totem battery
            pdc.set(infusion, PersistentDataType.BYTE, (byte) 1);

            // Lore
            List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

            // Add lines to lore
            lore.add("");
            lore.add(AlchimiaUtils.format("<gray>Infusion:"));

            // Add infusion name to lore
            if (infusion.equals(DESTRUCTIVE_CRITS)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <red><bold>Destructive Criticals"));
            } else if (infusion.equals(PHANTOM_CRITS)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <aqua>Phantom Criticals"));
            } else if (infusion.equals(TRUE_AIM)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <light_purple>True Aim"));
            } else if (infusion.equals(FORCEFUL)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <dark_green>Forceful"));
            } else if (infusion.equals(VOLATILITY)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <dark_red><bold>Volatility"));
            } else if (infusion.equals(HEALING)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <red>Healing"));
            } else if (infusion.equals(AUTO_REPLANT)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <green>Automatic Re-plant"));
            } else if (infusion.equals(KNOCKBACK)) {
                lore.add(AlchimiaUtils.format("<dark_gray>› <green>Knockback"));
            }

            // Set lore and meta
            meta.setLore(lore);
            tool.setItemMeta(meta);
        } else if (canBeInfused(tool, infusion) && infusion.equals(TOTEM_BATTERY)) {
            // Tool can be infused and the Infusion is the totem battery
            pdc.set(infusion, PersistentDataType.INTEGER, 0);

            // Lore
            List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

            // Add lines to lore
            lore.add("");
            lore.add(AlchimiaUtils.format("<gray>Infusion:"));

            // Add infusion name to lore
            lore.add(AlchimiaUtils.format("<dark_gray>› <gold><bold>Battery of Totems"));

            // Set lore and meta
            meta.setLore(lore);
            tool.setItemMeta(meta);
        } else {
            // Tool cannot be infused
            p.sendMessage(AlchimiaUtils.format("<red>You cannot apply this infusion to this item!"));
            return;
        }

        // Consume items
        for (int slot : IN_SLOTS_EXCLUDING_MID) {
            inv.consumeItem(slot, 1);
        }

        // Pre-craft effects
        b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_PREPARE_MIRROR, 1, 1);
        b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BEACON_POWER_SELECT, 1.5F, 1);
        b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 2, 0.1, 0.1, 0.1);

        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_CAST_SPELL, 1, 1);
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_CONDUIT_ATTACK_TARGET, 0.5F, 1);
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_TOTEM_USE, 0.1F, 1);
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_LODESTONE_PLACE, 1.5F, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 2, 0.1, 0.1, 0.1);

            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_CONDUIT_ATTACK_TARGET, 1.5F, 1);
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BEACON_POWER_SELECT, 0.3F, 1);
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_TOTEM_USE, 0.3F, 1);
                b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 2, 0.1, 0.1, 0.1);

                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    // Post-craft effects
                    b.getWorld().strikeLightningEffect(b.getLocation().add(0.5, 1, 0.5));
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_TRIDENT_THUNDER, 0.5F, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_TOTEM_USE, 0.5F, 1);
                    b.getWorld().spawnParticle(Particle.END_ROD, b.getLocation().add(0.5, 0.5, 0.5), 5, 0, 8, 0);
                    b.getWorld().spawnParticle(Particle.PORTAL, b.getLocation().add(0.5, 0.5, 0.5), 300, 2, 2, 2);

                    // Send message
                    p.sendMessage(AlchimiaUtils.format("<gradient:#50fa75:#3dd2ff>Your item has been infused!</gradient>"));
                }, 30);
            }, 30);
        }, 30);
    }

    private boolean canBeInfused(@NotNull ItemStack tool, @NotNull NamespacedKey infusion) {
        // Get the material
        Material mat = tool.getType();

        // Check if the Infusion can be applied to the tool
        if (mat.isItem()) {
            if (VALID_AXE.contains(mat) &&
                    (infusion.equals(DESTRUCTIVE_CRITS) ||
                            infusion.equals(PHANTOM_CRITS))) {
                return true;
            } else if (VALID_BOW.contains(mat) &&
                    (infusion.equals(TRUE_AIM) ||
                            infusion.equals(FORCEFUL) ||
                            infusion.equals(VOLATILITY) ||
                            infusion.equals(HEALING))) {
                return true;
            } else if (VALID_HOE.contains(mat) && infusion.equals(AUTO_REPLANT)) {
                return true;
            } else if (VALID_CHESTPLATE.contains(mat) && infusion.equals(TOTEM_BATTERY)) {
                return true;
            } else if (mat == Material.FISHING_ROD && infusion.equals(KNOCKBACK)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public class Infusion {

        public boolean infusable(@NotNull ItemStack tool) {
            Material mat = tool.getType();

            return switch (mat) {
                case GOLDEN_AXE,
                     IRON_AXE,
                     DIAMOND_AXE,
                     NETHERITE_AXE -> true;

                case BOW, CROSSBOW -> true;

                case GOLDEN_HOE,
                     IRON_HOE,
                     DIAMOND_HOE,
                     NETHERITE_HOE -> true;

                case GOLDEN_CHESTPLATE,
                     IRON_CHESTPLATE,
                     DIAMOND_CHESTPLATE,
                     NETHERITE_CHESTPLATE -> true;

                case FISHING_ROD -> true;

                default -> false;
            };
        }

        public boolean infusable(@NotNull ItemStack tool, @NotNull NamespacedKey infusion) {
            Material mat = tool.getType();

            return switch (mat) {
                case GOLDEN_AXE,
                     IRON_AXE,
                     DIAMOND_AXE,
                     NETHERITE_AXE -> AlchimiaUtils.equalsAny(infusion, DESTRUCTIVE_CRITS, PHANTOM_CRITS);

                case BOW, CROSSBOW -> AlchimiaUtils.equalsAny(infusion, FORCEFUL, HEALING, TRUE_AIM, VOLATILITY);

                case GOLDEN_HOE,
                     IRON_HOE,
                     DIAMOND_HOE,
                     NETHERITE_HOE -> infusion.equals(AUTO_REPLANT);

                case GOLDEN_CHESTPLATE,
                     IRON_CHESTPLATE,
                     DIAMOND_CHESTPLATE,
                     NETHERITE_CHESTPLATE -> infusion.equals(TOTEM_BATTERY);

                case FISHING_ROD -> infusion.equals(KNOCKBACK);

                default -> false;
            };
        }

    }

}

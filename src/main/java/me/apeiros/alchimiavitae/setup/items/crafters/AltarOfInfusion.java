package me.apeiros.alchimiavitae.setup.items.crafters;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.machines.CraftingBlock;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Categories;
import me.apeiros.alchimiavitae.utils.ChestMenuItems;
import me.apeiros.alchimiavitae.utils.InfusionMap;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class AltarOfInfusion extends CraftingBlock {

    // Keys
    public static final NamespacedKey DESTRUCTIVE_CRITS = AbstractAddon.createKey("infusion_destructivecrits");
    public static final NamespacedKey PHANTOM_CRITS = AbstractAddon.createKey("infusion_phantomcrits");
    public static final NamespacedKey TRUE_AIM = AbstractAddon.createKey("infusion_trueaim");
    public static final NamespacedKey FORCEFUL = AbstractAddon.createKey("infusion_forceful");
    public static final NamespacedKey VOLATILE = AbstractAddon.createKey("infusion_volatile");
    public static final NamespacedKey HEALING = AbstractAddon.createKey("infusion_healing");
    public static final NamespacedKey REPLANT = AbstractAddon.createKey("infusion_autoreplant");
    public static final NamespacedKey TOTEM_STORAGE = AbstractAddon.createKey("infusion_totemstorage");
    public static final NamespacedKey KNOCKBACK = AbstractAddon.createKey("infusion_knockback");

    // Tool categories
    private static final List<Material> VALID_AXE = Arrays.asList(Material.GOLDEN_AXE, Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE);
    private static final List<Material> VALID_BOW = Arrays.asList(Material.BOW, Material.CROSSBOW);
    private static final List<Material> VALID_HOE = Arrays.asList(Material.GOLDEN_HOE, Material.IRON_HOE, Material.DIAMOND_HOE, Material.NETHERITE_HOE);
    private static final List<Material> VALID_CHESTPLATE = Arrays.asList(Material.GOLDEN_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE);
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

        super(c, Items.ALTAR_OF_INFUSION, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.WITHER_PROOF_GLASS, Items.EXP_CRYSTAL,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, Items.DIVINE_ALTAR, SlimefunItems.BLISTERING_INGOT_3
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
        /*
         **Useless atm**
         boolean shieldDisruptorEnabled = cfg.getBoolean("options.infusions.infusion-shielddisruptor");
         boolean spikedHookEnabled = cfg.getBoolean("options.infusions.infusion-spikedhook");
        */
        boolean knockbackEnabled = cfg.getBoolean("options.infusions.infusion-knockback");

        // ItemStacks
        CustomItemStack validInfuseAxe = new CustomItemStack(Material.DIAMOND_AXE, "&b&lA valid axe to infuse", "&aA gold, iron, diamond,", "&aor netherite axe will suffice");
        CustomItemStack validInfuseBow = new CustomItemStack(Material.BOW, "&b&lA valid bow to infuse", "&aA bow or crossbow will suffice");
        CustomItemStack validInfuseHoe = new CustomItemStack(Material.DIAMOND_HOE, "&b&lA valid hoe to infuse", "&aA gold, iron, diamond,", "&aor netherite hoe will suffice");
        CustomItemStack validInfuseChestplate = new CustomItemStack(Material.DIAMOND_CHESTPLATE, "&b&lA valid chestplate to infuse", "&aA gold, iron, diamond,", "&aor netherite chestplate will suffice");
        /*
         **Useless atm**
         CustomItemStack validInfuseSword = new CustomItemStack(Material.DIAMOND_SWORD, "&b&lA valid sword to infuse", "&aA gold, iron, diamond,", "&aor netherite sword will suffice");
        */
        CustomItemStack validInfuseRod = new CustomItemStack(Material.FISHING_ROD, "&b&lA valid fishing rod to infuse", "&aA normal fishing rod will suffice");
        SlimefunItemStack item;

        // Register Infusions
        if (destructiveCritsEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE, new ItemStack(Material.STONECUTTER),
                    Items.DARKSTEEL, SlimefunItems.WITHER_PROOF_OBSIDIAN,
                    new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN, new ItemStack(Material.TNT)
            }, DESTRUCTIVE_CRITS);

            item = new SlimefunItemStack("AV_DESTRUCTIVE_CRITS_INFUSION", Material.TNT, "&c&lDestructive Criticals",
                    "&41/20 chance to give opponent Mining Fatigue III for 8 seconds on crit",
                    "&41/5 chance to give opponent Slowness I for 15 seconds on crit",
                    "&41/5 chance to give opponent Weakness I for 15 seconds on crit",
                    "&4Deals 0-5 extra damage to opponent's armor on crit");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.TNT), SlimefunItems.EXPLOSIVE_PICKAXE, new ItemStack(Material.STONECUTTER),
                    Items.DARKSTEEL, validInfuseAxe, SlimefunItems.WITHER_PROOF_OBSIDIAN,
                    new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.WITHER_PROOF_OBSIDIAN, new ItemStack(Material.TNT)
            }, item).register(av);
        }

        if (phantomCritsEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.PHANTOM_MEMBRANE),
                    Items.DARKSTEEL, SlimefunItems.HARDENED_GLASS,
                    new ItemStack(Material.PHANTOM_MEMBRANE), Items.CONDENSED_SOUL, new ItemStack(Material.PHANTOM_MEMBRANE)
            }, PHANTOM_CRITS);

            item = new SlimefunItemStack("AV_PHANTOM_CRITS_INFUSION", Material.PHANTOM_MEMBRANE, "&bPhantom Criticals",
                    "&a1/4 chance to deal (your attack damage to the power of 1.15",
                    "&amultiplied by 5/8) extra damage on a crit, bypassing armor");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.PHANTOM_MEMBRANE), SlimefunItems.MAGICAL_GLASS, new ItemStack(Material.PHANTOM_MEMBRANE),
                    Items.DARKSTEEL, validInfuseAxe, SlimefunItems.HARDENED_GLASS,
                    new ItemStack(Material.PHANTOM_MEMBRANE), Items.CONDENSED_SOUL, new ItemStack(Material.PHANTOM_MEMBRANE)
            }, item).register(av);
        }

        if (trueAimEnabled) {
            recipes.put(new ItemStack[] {
                    SlimefunItems.SYNTHETIC_SHULKER_SHELL, SlimefunItems.INFUSED_MAGNET, SlimefunItems.STAFF_WIND,
                    Items.DARKSTEEL, Items.EXP_CRYSTAL,
                    new ItemStack(Material.SHULKER_BOX), SlimefunItems.INFUSED_ELYTRA, SlimefunItems.STEEL_THRUSTER
            }, TRUE_AIM);

            item = new SlimefunItemStack("AV_TRUE_AIM_INFUSION", Material.SHULKER_SHELL, "&dTrue Aim",
                    "&5Partially using the levitation charm", "&5Shulkers use to terminate their victims,",
                    "&5a bow infused with this magic can fire", "&5arrows that are not affected by gravity");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.SYNTHETIC_SHULKER_SHELL, SlimefunItems.INFUSED_MAGNET, SlimefunItems.STAFF_WIND,
                    Items.DARKSTEEL, validInfuseBow, Items.EXP_CRYSTAL,
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
                    "&aprojectiles to blistering speeds", "&aArrows will travel 2x farther and faster");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.STAFF_WIND,
                    SlimefunItems.INFUSED_MAGNET, validInfuseBow, SlimefunItems.STEEL_THRUSTER,
                    SlimefunItems.ELECTRO_MAGNET, new ItemStack(Material.PISTON), SlimefunItems.TALISMAN_TRAVELLER
            }, item).register(av);
        }

        if (volatileEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE, SlimefunItems.TALISMAN_FIRE,
                    Items.DARKSTEEL, SlimefunItems.LAVA_GENERATOR_2,
                    new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.LAVA_CRYSTAL
            }, VOLATILE);

            item = new SlimefunItemStack("AV_VOLATILE_INFUSION", Material.FIRE_CHARGE, "&4&lVolatility",
                    "&cThis extremely dangerous infusion creates", "&cspheres made of pure superheated lava,",
                    "&cdelivering a mini-inferno to the target", "&41/7 chance to fire a large fireball",
                    "&46/7 chance to fire a small fireball");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.BLAZE_ROD), SlimefunItems.STAFF_FIRE, SlimefunItems.TALISMAN_FIRE,
                    Items.DARKSTEEL, validInfuseBow, SlimefunItems.LAVA_GENERATOR_2,
                    new ItemStack(Material.TNT), SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.LAVA_CRYSTAL
            }, item).register(av);
        }

        if (healingEnabled) {
            recipes.put(new ItemStack[] {
                    Items.BENEVOLENT_BREW, SlimefunItems.MEDICINE, SlimefunItems.VITAMINS,
                    Items.ILLUMIUM, new ItemStack(Material.TOTEM_OF_UNDYING),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE, SlimefunItems.MAGIC_SUGAR
            }, HEALING);

            item = new SlimefunItemStack("AV_HEALING_INFUSION", Material.REDSTONE, "&cHealing",
                    "&cThis infusion will heal hit entities", " &cand recover their &4health", "" +
                    "&aHeals for the same amount that a bow shot would damage");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    Items.BENEVOLENT_BREW, SlimefunItems.MEDICINE, SlimefunItems.VITAMINS,
                    Items.ILLUMIUM, validInfuseBow, new ItemStack(Material.TOTEM_OF_UNDYING),
                    new ItemStack(Material.ENCHANTED_GOLDEN_APPLE), SlimefunItems.MEDICINE, SlimefunItems.MAGIC_SUGAR
            }, item).register(av);
        }

        if (autoReplantEnabled) {
            recipes.put(new ItemStack[] {
                    new ItemStack(Material.COMPOSTER), Items.GOOD_ESSENCE, new ItemStack(Material.WATER_BUCKET),
                    Items.ILLUMIUM, SlimefunItems.FLUID_PUMP,
                    new ItemStack(Material.BONE_BLOCK), Items.GOOD_MAGIC_PLANT, new ItemStack(Material.GRINDSTONE)
            }, REPLANT);

            item = new SlimefunItemStack("AV_AUTO_REPLANT_INFUSION", Material.WHEAT, "&aAutomatic Re-plant",
                    "&2Any fully-grown crops broken",
                    "&2with a hoe infused with this", "&2will &aautomatically &2be replanted");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.COMPOSTER), Items.GOOD_ESSENCE, new ItemStack(Material.WATER_BUCKET),
                    Items.ILLUMIUM, validInfuseHoe, SlimefunItems.FLUID_PUMP,
                    new ItemStack(Material.BONE_BLOCK), Items.GOOD_MAGIC_PLANT, new ItemStack(Material.GRINDSTONE)
            }, item).register(av);
        }

        if (totemStorageEnabled) {
            recipes.put(new ItemStack[] {
                    SlimefunItems.NECROTIC_SKULL, Items.CONDENSED_SOUL, Items.BENEVOLENT_BREW,
                    Items.ILLUMIUM, Items.EXP_CRYSTAL,
                    SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ESSENCE_OF_AFTERLIFE
            }, TOTEM_STORAGE);

            item = new SlimefunItemStack("AV_TOTEM_BATTERY_INFUSION", Material.TOTEM_OF_UNDYING, "&6&lTotem Battery",
                    "&eA built-in pocket dimension that holds the energy", "&eof up to 8 Totems of Undying",
                    "&6Store a Totem in this apparatus", "&6by &e&lShift-Right-Clicking &6with a Totem in the hand",
                    "&6while the infused chestplate is worn");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.NECROTIC_SKULL, Items.CONDENSED_SOUL, Items.BENEVOLENT_BREW,
                    Items.ILLUMIUM, validInfuseChestplate, Items.EXP_CRYSTAL,
                    SlimefunItems.ESSENCE_OF_AFTERLIFE, SlimefunItems.ENERGIZED_CAPACITOR, SlimefunItems.ESSENCE_OF_AFTERLIFE
            }, item).register(av);
        }

        if (knockbackEnabled) {
            recipes.put(new ItemStack[] {
                    SlimefunItems.TALISMAN_WHIRLWIND, new ItemStack(Material.STICKY_PISTON), Items.EXP_CRYSTAL,
                    SlimefunItems.GRANDPAS_WALKING_STICK, new ItemStack(Material.STICKY_PISTON),
                    new ItemStack(Material.SLIME_BALL), SlimefunItems.GRANDPAS_WALKING_STICK, SlimefunItems.TALISMAN_WHIRLWIND
            }, KNOCKBACK);

            item = new SlimefunItemStack("AV_KNOCKBACK_INFUSION", Material.SLIME_BALL, "&aKnockback",
                    "&2Entities pulled by this fishing rod",
                    "&2will instead be pushed back");

            new SlimefunItem(Categories.INFUSIONS, item, RecipeTypes.INFUSION_ALTAR_TYPE, new ItemStack[] {
                    SlimefunItems.TALISMAN_WHIRLWIND, new ItemStack(Material.STICKY_PISTON), Items.EXP_CRYSTAL,
                    SlimefunItems.GRANDPAS_WALKING_STICK, validInfuseRod, new ItemStack(Material.STICKY_PISTON),
                    new ItemStack(Material.SLIME_BALL), SlimefunItems.GRANDPAS_WALKING_STICK, SlimefunItems.TALISMAN_WHIRLWIND
            }, item).register(av);
        }
    }

    @Override
    protected void setup(@NotNull BlockMenuPreset blockMenuPreset) {
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
    }

    @Override
    protected void onBreak(BlockBreakEvent e, BlockMenu menu) {
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
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>Invalid Infusion!")));
            return;
        }

        Material mat = inv.getItemInSlot(TOOL_SLOT).getType();

        // Check if item is valid
        if (mat.isItem()) {
            if (VALID_AXE.contains(mat) ||
                    (((VALID_BOW.contains(mat) ||
                    VALID_HOE.contains(mat) ||
                    VALID_CHESTPLATE.contains(mat)) ||
                    VALID_FISHING_ROD.contains(mat)))) {
                // Valid item
            } else {
                // Invalid item
                p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>You cannot infuse that tool!")));
                return;
            }
        } else {
            // Invalid item
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>You cannot infuse blocks!")));
            return;
        }

        // Get the tool
        ItemStack tool = inv.getItemInSlot(TOOL_SLOT);
        if (tool == null || tool.getType().equals(Material.AIR)) {
            // No tool
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>You cannot infuse air!")));
            return;
        }

        // ItemMeta
        ItemMeta meta = tool.getItemMeta();

        // Container
        PersistentDataContainer container = meta.getPersistentDataContainer();

        // Check if tool is already infused
        if (container.has(DESTRUCTIVE_CRITS, PersistentDataType.BYTE) ||
                container.has(PHANTOM_CRITS, PersistentDataType.BYTE) ||
                container.has(TRUE_AIM, PersistentDataType.BYTE) ||
                container.has(FORCEFUL, PersistentDataType.BYTE) ||
                container.has(VOLATILE, PersistentDataType.BYTE) ||
                container.has(HEALING, PersistentDataType.BYTE) ||
                container.has(TOTEM_STORAGE, PersistentDataType.INTEGER) ||
                container.has(REPLANT, PersistentDataType.BYTE) ||
                container.has(KNOCKBACK, PersistentDataType.BYTE)) {
            // Tool is already infused
            p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse("<red>You have already applied an Infusion to this item!")));
            return;
        }

        // Check if the tool can be infused
        if (canBeInfused(tool, infusion) && !infusion.equals(TOTEM_STORAGE)) {
            // Tool can be infused and the Infusion is not the totem battery
            container.set(infusion, PersistentDataType.BYTE, (byte) 1);

            // Lore
            List<String> lore;

            // Assign lore
            lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

            // Add lines to lore
            lore.add("");
            lore.add(BukkitComponentSerializer.legacy().serialize(
                    MM.parse("<gray>Infusion:")));

            // Add infusion name to lore
            if (infusion.equals(DESTRUCTIVE_CRITS)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <red><bold>Destructive Criticals")));
            } else if (infusion.equals(PHANTOM_CRITS)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <aqua>Phantom Criticals")));
            } else if (infusion.equals(TRUE_AIM)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <light_purple>True Aim")));
            } else if (infusion.equals(FORCEFUL)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <dark_green>Forceful")));
            } else if (infusion.equals(VOLATILE)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <dark_red><bold>Volatility")));
            } else if (infusion.equals(HEALING)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <red>Healing")));
            } else if (infusion.equals(REPLANT)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <green>Automatic Re-plant")));
            } else if (infusion.equals(KNOCKBACK)) {
                lore.add(BukkitComponentSerializer.legacy().serialize(
                        MM.parse("<dark_gray>› <green>Knockback")));
            }

            // Set lore and meta
            meta.setLore(lore);
            tool.setItemMeta(meta);
        } else if (canBeInfused(tool, infusion) && infusion.equals(TOTEM_STORAGE)) {
            // Tool can be infused and the Infusion is the totem battery
            container.set(infusion, PersistentDataType.INTEGER, 0);

            // Lore
            List<String> lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();

            // Add lines to lore
            lore.add("");
            lore.add(BukkitComponentSerializer.legacy().serialize(
                    MM.parse("<gray>Infusion:")));

            // Add infusion name to lore
            lore.add(BukkitComponentSerializer.legacy().serialize(
                    MM.parse("<dark_gray>› <gold><bold>Battery of Totems")));

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
                    p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse(
                            "<gradient:#50fa75:#3dd2ff>Your item has been infused!</gradient>")));
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
                            infusion.equals(VOLATILE) ||
                            infusion.equals(HEALING))) {
                return true;
            } else if (VALID_HOE.contains(mat) &&
                    infusion.equals(REPLANT)) {
                return true;
            } else if (VALID_CHESTPLATE.contains(mat) &&
                    infusion.equals(TOTEM_STORAGE)) {
                return true;
            } else if (VALID_FISHING_ROD.contains(mat) &&
                    infusion.equals(KNOCKBACK)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
}
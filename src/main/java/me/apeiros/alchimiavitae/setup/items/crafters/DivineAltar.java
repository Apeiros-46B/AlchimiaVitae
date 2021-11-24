package me.apeiros.alchimiavitae.setup.items.crafters;

import io.github.mooy1.infinitylib.machines.CraftingBlock;
import io.github.mooy1.infinitylib.machines.CraftingBlockRecipe;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.services.CustomTextureService;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Categories;
import me.apeiros.alchimiavitae.utils.ChestMenuItems;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class DivineAltar extends CraftingBlock {

    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};

    private static final int[] CRAFT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};
    private static final int[] CRAFT_BUTTON = {15, 16, 24, 25, 33, 34};

    private static final int OUTPUT_SLOT = 20;

    public DivineAltar(ItemGroup c) {

        super(c, Items.DIVINE_ALTAR, RecipeType.ANCIENT_ALTAR, new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.ELECTRO_MAGNET, Items.EXP_CRYSTAL,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.ANCIENT_ALTAR, SlimefunItems.BLISTERING_INGOT_3,
                SlimefunItems.ANCIENT_PEDESTAL, SlimefunItems.HEATED_PRESSURE_CHAMBER, SlimefunItems.ANCIENT_PEDESTAL
        });

        // Get plugin and config
        AlchimiaVitae av = AlchimiaVitae.i();
        Configuration cfg = av.getConfig();

        // Get config values
        boolean reinforcedTransmutationEnabled = cfg.getBoolean("options.transmutations.reinforced-transmutation");
        boolean hardenedTransmutationEnabled = cfg.getBoolean("options.transmutations.hardened-transmutation");
        boolean steelTransmutationEnabled = cfg.getBoolean("options.transmutations.steel-transmutation");
        boolean damascusTransmutationEnabled = cfg.getBoolean("options.transmutations.damascus-transmutation");
        boolean compressedCarbonTransmutationEnabled = cfg.getBoolean("options.transmutations.compressed-carbon-transmutation");
        boolean useSlimefunItemCustomModelData = cfg.getBoolean("options.transmutations.use-same-custommodeldata");

        // ItemStack and custom texture service
        SlimefunItemStack item;
        CustomTextureService cts = Slimefun.getItemTextureService();

        // Add transmutations
        if (reinforcedTransmutationEnabled) {
            this.addRecipe(new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 2),
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null);

            item = new SlimefunItemStack("AV_REINFORCED_ALLOY_INGOT", Material.IRON_INGOT, "&b&lReinforced Alloy Ingot");

            if (useSlimefunItemCustomModelData) {
                ItemMeta meta = item.getItemMeta();
                meta.setCustomModelData(cts.getModelData("REINFORCED_ALLOY_INGOT"));
                item.setItemMeta(meta);
                cts.setTexture(item, "AV_REINFORCED_ALLOY_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
            }, new SlimefunItemStack(item, 2)).register(av);
        }

        if (hardenedTransmutationEnabled) {
            this.addRecipe(new SlimefunItemStack(SlimefunItems.HARDENED_METAL_INGOT, 2),
                    null, SlimefunItems.STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.STEEL_INGOT, null);

            item = new SlimefunItemStack("AV_HARDENED_METAL_INGOT", Material.IRON_INGOT, "&b&lHardened Metal");

            if (useSlimefunItemCustomModelData) {
                ItemMeta meta = item.getItemMeta();
                meta.setCustomModelData(cts.getModelData("HARDENED_METAL_INGOT"));
                item.setItemMeta(meta);
                cts.setTexture(item, "AV_HARDENED_METAL_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, SlimefunItems.STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.STEEL_INGOT, null
            }, new SlimefunItemStack(item, 2)).register(av);
        }

        if (steelTransmutationEnabled) {
            this.addRecipe(new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 8),
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.CARBON, null);

            item = new SlimefunItemStack("AV_STEEL_INGOT", Material.IRON_INGOT, "&bSteel Ingot");

            if (useSlimefunItemCustomModelData) {
                ItemMeta meta = item.getItemMeta();
                meta.setCustomModelData(cts.getModelData("STEEL_INGOT"));
                item.setItemMeta(meta);
                cts.setTexture(item, "AV_STEEL_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.CARBON, null
            }, new SlimefunItemStack(item, 8)).register(av);
        }

        if (damascusTransmutationEnabled) {
            this.addRecipe(new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 8),
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.COMPRESSED_CARBON, null);

            item = new SlimefunItemStack("AV_DAMASCUS_STEEL_INGOT", Material.IRON_INGOT, "&bDamascus Steel Ingot");

            if (useSlimefunItemCustomModelData) {
                ItemMeta meta = item.getItemMeta();
                meta.setCustomModelData(cts.getModelData("DAMASCUS_STEEL_INGOT"));
                item.setItemMeta(meta);
                cts.setTexture(item, "AV_DAMASCUS_STEEL_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.COMPRESSED_CARBON, null
            }, new SlimefunItemStack(item, 8)).register(av);
        }

        if (compressedCarbonTransmutationEnabled) {
            this.addRecipe(SlimefunItems.COMPRESSED_CARBON,
                    new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                    new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                    new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL));

            item = new SlimefunItemStack("AV_COMPRESSED_CARBON", HeadTexture.COMPRESSED_CARBON, "&cCompressed Carbon");

            if (useSlimefunItemCustomModelData) {
                ItemMeta meta = item.getItemMeta();
                meta.setCustomModelData(cts.getModelData("COMPRESSED_CARBON"));
                item.setItemMeta(meta);
                cts.setTexture(item, "AV_COMPRESSED_CARBON");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                    new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                    new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
            }, item).register(av);
        }

        // Add normal recipes to recipe map
        this.addRecipe(Items.MOLTEN_MYSTERY_METAL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL);

        this.addRecipe(Items.ORNATE_CAULDRON,
                Items.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, Items.DIVINE_ALTAR, Items.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3);

        this.addRecipe(Items.ALTAR_OF_INFUSION,
                Items.EXP_CRYSTAL, SlimefunItems.WITHER_PROOF_GLASS, Items.EXP_CRYSTAL,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, Items.DIVINE_ALTAR, SlimefunItems.BLISTERING_INGOT_3);


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
        // Spawn ender particles
        b.getWorld().spawnParticle(Particle.REVERSE_PORTAL, b.getLocation().add(0.5, 0.5, 0.5), 100, 0.5, 0.5, 0.5);

        // Sound effect
        b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BEACON_ACTIVATE, 1F, 1F);

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
        e.getBlock().getWorld().playSound(e.getBlock().getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BEACON_DEACTIVATE, 1F, 1F);
    }

    @Override
    protected void craft(@NotNull Block b, @NotNull BlockMenu inv, @NotNull Player p) {
        // Get expected output
        ItemStack[] input = new ItemStack[9];

        int index = 0;
        for (int i : IN_SLOTS) {
            input[index] = inv.getItemInSlot(i);
            index++;
        }

        CraftingBlockRecipe output = this.getOutput(input);
        ItemStack item = null;

        if (output != null) {
            item = output.output();
        }

        // Invalid recipe
        if (item == null) {
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

        p.sendMessage("material of the item:");
        p.sendMessage(item.getType().toString());

        // Pre-craft effects
        ItemStack finalItem = item;

        p.sendMessage("material of the item:");
        p.sendMessage(finalItem.getType().toString());
        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 2, 0.1, 0.1, 0.1);

            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 2, 0.1, 0.1, 0.1);

                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                    b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 2, 0.1, 0.1, 0.1);

                    Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                        // Post-craft effects
                        b.getWorld().strikeLightningEffect(b.getLocation().add(0.5, 1, 0.5));
                        b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_TRIDENT_THUNDER, 1, 1);
                        b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                        b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 5, 0.1, 0.1, 0.1);
                        b.getWorld().spawnParticle(Particle.REVERSE_PORTAL, b.getLocation().add(0.5, 0.5, 0.5), 300, 2, 2, 2);

                        // Send message
                        p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse(
                                "<gradient:#50fa75:#3dd2ff>Successful craft!</gradient>")));

                        p.sendMessage("material of the item:");
                        p.sendMessage(finalItem.getType().toString());

                        // Output the item
                        if (AlchimiaVitae.i().getConfig().getBoolean("options.divine-altar-drop")) {
                            b.getWorld().dropItemNaturally(b.getLocation().add(0.5, 2, 0.5), finalItem.clone()).setGlowing(true);
                        } else {
                            inv.addItem(OUTPUT_SLOT, finalItem);
                        }
                    }, 30);
                }, 30);
            }, 30);
        }, 30);
    }
}

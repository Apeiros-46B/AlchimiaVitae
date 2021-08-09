package me.apeiros.alchimiavitae.setup.items.crafters;

import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.recipes.RecipeMap;
import io.github.mooy1.infinitylib.recipes.RecipeOutput;
import io.github.mooy1.infinitylib.recipes.ShapedRecipe;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.core.services.CustomTextureService;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Categories;
import me.apeiros.alchimiavitae.utils.ChestMenuItems;
import me.apeiros.alchimiavitae.utils.RecipeTypes;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import net.kyori.adventure.text.serializer.craftbukkit.BukkitComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

import static me.apeiros.alchimiavitae.AlchimiaVitae.MM;

public class DivineAltar extends AbstractContainer {

    private static final int[] IN_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};

    private static final int[] IN_BG = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] CRAFT_BG = {5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44};

    private static final int[] CRAFT_BUTTON = {15, 16, 24, 25, 33, 34};

    public static final RecipeMap<ItemStack> RECIPES = new RecipeMap<>(ShapedRecipe::new);

    public DivineAltar(Category c) {

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
        CustomTextureService cts = SlimefunPlugin.getItemTextureService();

        // Add transmutations
        if (reinforcedTransmutationEnabled) {
            RECIPES.put(new ItemStack[] {
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
            }, new SlimefunItemStack(SlimefunItems.REINFORCED_ALLOY_INGOT, 2));

            item = new SlimefunItemStack("AV_REINFORCED_ALLOY_INGOT", Material.IRON_INGOT, "&b&lReinforced Alloy Ingot");

            if (useSlimefunItemCustomModelData) {
                item.setCustomModel(cts.getModelData("REINFORCED_ALLOY_INGOT"));
                cts.setTexture(item, "AV_REINFORCED_ALLOY_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.DAMASCUS_STEEL_INGOT, null
            }, new SlimefunItemStack(item, 2)).register(av);
        }

        if (hardenedTransmutationEnabled) {
            RECIPES.put(new ItemStack[] {
                    null, SlimefunItems.STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.STEEL_INGOT, null
            }, new SlimefunItemStack(SlimefunItems.HARDENED_METAL_INGOT, 2));

            item = new SlimefunItemStack("AV_HARDENED_METAL_INGOT", Material.IRON_INGOT, "&b&lHardened Metal");

            if (useSlimefunItemCustomModelData) {
                item.setCustomModel(cts.getModelData("HARDENED_METAL_INGOT"));
                cts.setTexture(item, "AV_HARDENED_METAL_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, SlimefunItems.STEEL_INGOT, null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.STEEL_INGOT, null
            }, new SlimefunItemStack(item, 2)).register(av);
        }

        if (steelTransmutationEnabled) {
            RECIPES.put(new ItemStack[] {
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.CARBON, null
            }, new SlimefunItemStack(SlimefunItems.STEEL_INGOT, 8));

            item = new SlimefunItemStack("AV_STEEL_INGOT", Material.IRON_INGOT, "&bSteel Ingot");

            if (useSlimefunItemCustomModelData) {
                item.setCustomModel(cts.getModelData("STEEL_INGOT"));
                cts.setTexture(item, "AV_STEEL_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.CARBON, null
            }, new SlimefunItemStack(item, 8)).register(av);
        }

        if (damascusTransmutationEnabled) {
            RECIPES.put(new ItemStack[] {
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.COMPRESSED_CARBON, null
            }, new SlimefunItemStack(SlimefunItems.DAMASCUS_STEEL_INGOT, 8));

            item = new SlimefunItemStack("AV_DAMASCUS_STEEL_INGOT", Material.IRON_INGOT, "&bDamascus Steel Ingot");

            if (useSlimefunItemCustomModelData) {
                item.setCustomModel(cts.getModelData("DAMASCUS_STEEL_INGOT"));
                cts.setTexture(item, "AV_DAMASCUS_STEEL_INGOT");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    null, new ItemStack(Material.IRON_BLOCK), null,
                    Items.DARKSTEEL, Items.MYSTERY_METAL, Items.ILLUMIUM,
                    null, SlimefunItems.COMPRESSED_CARBON, null
            }, new SlimefunItemStack(item, 8)).register(av);
        }

        if (compressedCarbonTransmutationEnabled) {
            RECIPES.put(new ItemStack[] {
                    new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                    new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                    new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
            }, SlimefunItems.COMPRESSED_CARBON);

            item = new SlimefunItemStack("AV_COMPRESSED_CARBON", HeadTexture.COMPRESSED_CARBON, "&cCompressed Carbon");

            if (useSlimefunItemCustomModelData) {
                item.setCustomModel(cts.getModelData("COMPRESSED_CARBON"));
                cts.setTexture(item, "AV_COMPRESSED_CARBON");
            }

            new SlimefunItem(Categories.ALTAR_RECIPES, item, RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[] {
                    new ItemStack(Material.COAL), new ItemStack(Material.COOKED_BEEF), new ItemStack(Material.COAL),
                    new ItemStack(Material.OAK_LEAVES), new ItemStack(Material.COAL_BLOCK), new ItemStack(Material.KELP),
                    new ItemStack(Material.COAL), new ItemStack(Material.ROTTEN_FLESH), new ItemStack(Material.COAL)
            }, item).register(av);
        }

        // Add normal recipes to recipe map
        RECIPES.put(new ItemStack[] {
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.DARKSTEEL,
                Items.EXP_CRYSTAL, Items.ILLUMIUM, Items.EXP_CRYSTAL
        }, Items.MOLTEN_MYSTERY_METAL);

        RECIPES.put(new ItemStack[] {
                Items.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, Items.DIVINE_ALTAR, Items.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        }, Items.ORNATE_CAULDRON);

        RECIPES.put(new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.WITHER_PROOF_GLASS, Items.EXP_CRYSTAL,
                SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.BEACON), SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.BLISTERING_INGOT_3, Items.DIVINE_ALTAR, SlimefunItems.BLISTERING_INGOT_3
        }, Items.ALTAR_OF_INFUSION);

    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@NotNull DirtyChestMenu dirtyChestMenu, @NotNull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
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

    private void craft(@NotNull Block b, @NotNull BlockMenu inv, @NotNull Player p) {
        // Get expected output
        RecipeOutput<ItemStack> output = RECIPES.get(StackUtils.arrayFrom(inv, IN_SLOTS));
        ItemStack item = null;

        if (output != null) {
            item = output.getOutput();
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

        // Pre-craft effects
        ItemStack finalItem = item;
        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
            b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
            b.getWorld().playSound(b.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
            b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);

            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                b.getWorld().playSound(b.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);

                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                    b.getWorld().playSound(b.getLocation(), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.5F, 1);
                    b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 2, 0.1, 0.1, 0.1);

                    Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                        // Post-craft effects
                        b.getWorld().strikeLightningEffect(b.getLocation().add(0, 1, 0));
                        b.getWorld().playSound(b.getLocation(), Sound.ITEM_TRIDENT_THUNDER, 1, 1);
                        b.getWorld().playSound(b.getLocation(), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 1, 1);
                        b.getWorld().spawnParticle(Particle.FLASH, b.getLocation(), 5, 0.1, 0.1, 0.1);
                        b.getWorld().spawnParticle(Particle.REVERSE_PORTAL, b.getLocation(), 300, 2, 2, 2);

                        // Drop item
                        b.getWorld().dropItemNaturally(b.getLocation().add(0, 2, 0), finalItem.clone()).setGlowing(true);

                        // Send message
                        p.sendMessage(BukkitComponentSerializer.legacy().serialize(MM.parse(
                                "<gradient:#50fa75:#3dd2ff>Successful craft!</gradient>")));
                    }, 30);
                }, 30);
            }, 30);
        }, 30);
    }
}

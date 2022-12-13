package me.apeiros.alchimiavitae.setup.items.crafters;

import io.github.mooy1.infinitylib.machines.CraftingBlock;
import io.github.mooy1.infinitylib.machines.CraftingBlockRecipe;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.apeiros.alchimiavitae.setup.Items;
import me.apeiros.alchimiavitae.utils.Utils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OrnateCauldron extends CraftingBlock {

    private static final int[] IN_SLOTS = {0, 1, 2, 9, 10, 11, 18, 19, 20};
    private static final int[] IN_BG = {3, 12, 21};

    private static final int[] CRAFT_BUTTON = {4, 13, 22};

    private static final int[] OUT_BG = {5, 14, 23};
    private static final int[] OUT_SLOTS = {6, 7, 8, 15, 16, 17, 24, 25, 26};

    public OrnateCauldron(ItemGroup c) {

        super(c, Items.COSMIC_CAULDRON, Utils.RecipeTypes.DIVINE_ALTAR_TYPE, new ItemStack[]{
                Items.EXP_CRYSTAL, SlimefunItems.AUTO_BREWER, Items.EXP_CRYSTAL,
                Items.DARKSTEEL, Items.DIVINE_ALTAR, Items.ILLUMIUM,
                SlimefunItems.BLISTERING_INGOT_3, SlimefunItems.FLUID_PUMP, SlimefunItems.BLISTERING_INGOT_3
        });

        // Add recipes to recipe map
        this.addRecipe(Items.POTION_OF_OSMOSIS,
                Items.EXP_CRYSTAL, new ItemStack(Material.NETHERITE_BLOCK), Items.EXP_CRYSTAL,
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), Items.GOOD_ESSENCE,
                Items.DARKSTEEL, new ItemStack(Material.LAVA_BUCKET), Items.ILLUMIUM);

        this.addRecipe(Items.BENEVOLENT_BREW,
                Items.EXP_CRYSTAL, new ItemStack(Material.LILAC), new ItemStack(Material.CORNFLOWER),
                Items.GOOD_ESSENCE, new ItemStack(Material.HONEY_BOTTLE), new ItemStack(Material.TOTEM_OF_UNDYING),
                Items.ILLUMIUM, new ItemStack(Material.LILY_OF_THE_VALLEY), new ItemStack(Material.POPPY));

        this.addRecipe(Items.MALEVOLENT_CONCOCTION,
                Items.EXP_CRYSTAL, new ItemStack(Material.FERMENTED_SPIDER_EYE), new ItemStack(Material.BONE_BLOCK),
                Items.EVIL_ESSENCE, new ItemStack(Material.DRAGON_BREATH), new ItemStack(Material.LAVA_BUCKET),
                Items.DARKSTEEL, Items.CONDENSED_SOUL, new ItemStack(Material.ROTTEN_FLESH));
    }

    @Override
    protected void setup(@NotNull BlockMenuPreset blockMenuPreset) {
        // Input background
        for (int slot : IN_BG) {
            blockMenuPreset.addItem(slot, Items.IN_BG, ChestMenuUtils.getEmptyClickHandler());
        }

        // Input slots
        for (int slot : IN_SLOTS) {
            blockMenuPreset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 26);
        }

        // Output background
        for (int slot : OUT_BG) {
            blockMenuPreset.addItem(slot, Items.OUT_BG, ChestMenuUtils.getEmptyClickHandler());
        }

        // Output slots
        for (int slot : OUT_SLOTS) {
            blockMenuPreset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 26);
        }

        // Craft button
        for (int slot : CRAFT_BUTTON) {
            blockMenuPreset.addItem(slot, Items.CRAFT_BTN);
        }
    }

    @Override
    protected void onNewInstance(@NotNull BlockMenu menu, @NotNull Block b) {
        // Spawn ender particles
        b.getWorld().spawnParticle(Particle.TOTEM, b.getLocation().add(0.5, 0.5, 0.5), 100, 3, 3, 3);

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
            p.sendMessage(Utils.format("<red>That recipe is invalid!"));
            p.sendMessage(Utils.format("<red>Please try again."));
            return;
        }

        // Check for space
        if (!inv.fits(item, OUT_SLOTS)) {
            p.sendMessage(Utils.format("<red>There is not enough space in the output slots!"));
            return;
        }

        // Consume items
        for (int slot : IN_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot, 1);
            }
        }

        // First pre-craft effect burst
        ItemStack finalItem = item;
        Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
            b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
            b.getWorld().spawnParticle(Particle.SPELL_WITCH, b.getLocation().add(0.5, 0.5, 0.5), 2, 1, 1, 1);

            // Pre-craft effects
            Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
                b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_LODESTONE_COMPASS_LOCK, 1, 1);
                b.getWorld().spawnParticle(Particle.CRIT_MAGIC, b.getLocation().add(0.5, 0.5, 0.5), 200, 1, 1, 1);

                Bukkit.getScheduler().runTaskLater(AlchimiaVitae.i(), () -> {
                    // Post-craft effects
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_BOTTLE_FILL, 1, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_MIRROR_MOVE, 0.5F, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, 1, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_BREWING_STAND_BREW, 1, 1);
                    b.getWorld().playSound(b.getLocation().add(0.5, 0.5, 0.5), Sound.ITEM_LODESTONE_COMPASS_LOCK, 2, 1);
                    b.getWorld().spawnParticle(Particle.FLASH, b.getLocation().add(0.5, 0.5, 0.5), 1, 0.1, 0.1, 0.1);
                    b.getWorld().spawnParticle(Particle.END_ROD, b.getLocation().add(0.5, 0.5, 0.5), 200, 0.1, 4, 0.1);

                    // Send message
                    p.sendMessage(Utils.format("<gradient:#50fa75:#3dd2ff>Successful brew!</gradient>"));

                    // Output the item
                    inv.pushItem(finalItem.clone(), OUT_SLOTS);
                }, 30);
            }, 30);
        }, 30);
    }
}

package me.apeiros.alchimiavitae.setup.items.crafters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.machines.CraftingBlock;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.apeiros.alchimiavitae.AlchimiaUtils;
import me.apeiros.alchimiavitae.AlchimiaVitae;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

/**
 * Shared superclass which {@link AlchimiaVitae}'s
 * custom crafters inherit from
 */
abstract class AbstractCrafter<T> extends CraftingBlock {

    public AbstractCrafter(ItemGroup ig, SlimefunItemStack item, RecipeType rt, ItemStack[] recipe) {
        super(ig, item, rt, recipe);
    }

    // Recipe map
    protected RecipeMap<T> recipes = new RecipeMap<T>();

    // {{{ Slot numbers
    protected final int[] IN_SLOTS = { 0, 1, 2, 9, 10, 11, 18, 19, 20 };
    protected final int[] IN_BG_SLOTS = { 3, 12, 21 };
    protected final int[] CRAFT_BTN_SLOTS = { 4, 13, 22 };
    protected final int[] OUT_BG_SLOTS = { 5, 14, 23 };
    protected final int[] OUT_SLOTS = { 6, 7, 8, 15, 16, 17, 24, 25, 26 };
    // }}}

    // {{{ Menu ItemStacks
    protected final ItemStack IN_BG_ITEM = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE,
            AlchimiaUtils.format("<gradient:#ff68fc:#ff9a5c>Input</gradient>"));

    protected final ItemStack CRAFT_BTN_ITEM = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE,
            AlchimiaUtils.format("<gradient:#39f792:#5c95ff>Craft</gradient>"), "&aClick to craft");

    protected final ItemStack OUT_BG_ITEM = new CustomItemStack(Material.CYAN_STAINED_GLASS_PANE,
            AlchimiaUtils.format("<gradient:#5cb8ff:#39f7e1>Output</gradient>"));
    // }}}

    // {{{ Abstract methods
    protected abstract void addDefaultRecipes();

    protected abstract void newInstanceEffects(@Nonnull World w, @Nonnull Location l);

    protected abstract void finish(
            @Nonnull World w,
            @Nonnull Location l,
            @Nonnull BlockMenu menu,
            @Nonnull T result);
    // }}}

    // {{{ Our own implemented methods
    /**
     * Add a new recipe
     *
     * @param output Crafting output
     * @param input Recipe used to craft the output
     */
    public void newRecipe(@Nonnull T output, @Nonnull ItemStack... input) {
        // Add the recipe to the map
        this.recipes.put(output, input);
    }

    /**
     * Add a new recipe and register it as a {@link SlimefunItem}
     *
     * @param ig {@link ItemGroup} to use for registration
     * @param rt {@link RecipeType} to use for registration
     * @param output Crafting output
     * @param input Recipe used to craft the output
     */
    public void newRecipe(@Nonnull ItemGroup ig, @Nonnull RecipeType rt, @Nonnull T output, @Nonnull ItemStack... input) {
        // Because this method is overriden by the Altar of Infusion, we can safely assume that the output is a SlimefunItemStack
        if (!(output instanceof SlimefunItemStack))
            return;

        SlimefunItemStack stack = (SlimefunItemStack) output;
        int amount = stack.getAmount();

        // Add the recipe to the map
        this.newRecipe(output, input);

        // If ItemGroup is null, don't register the item
        if (ig == null)
            return;

        // Modify the ID for double registration
        String id = "AV_" + stack.getItemId();
        stack = new SlimefunItemStack(id, stack);
        stack = new SlimefunItemStack(stack, 1);

        // Register item
        new SlimefunItem(ig, stack, rt, input, new SlimefunItemStack(stack, amount)).register(AlchimiaVitae.i());
    }
    // }}}

    // {{{ Overriding CraftingBlock methods
    // {{{ Block setup
    @Override
    protected void setup(@Nonnull BlockMenuPreset preset) {
        // Input background
        for (int slot : IN_BG_SLOTS) {
            preset.addItem(slot, IN_BG_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }

        // Input slots
        for (int slot : IN_SLOTS) {
            preset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 26);
        }

        // Output background
        for (int slot : OUT_BG_SLOTS) {
            preset.addItem(slot, OUT_BG_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }

        // Output slots
        for (int slot : OUT_SLOTS) {
            preset.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> i == slot || i > 26);
        }

        // Craft button
        for (int slot : CRAFT_BTN_SLOTS) {
            preset.addItem(slot, CRAFT_BTN_ITEM);
        }
    }
    // }}}

    // {{{ Called after a BlockMenu is created from BlockMenuPreset
    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        // Effects
        this.newInstanceEffects(b.getWorld(), b.getLocation().add(0.5, 0.5, 0.5));

        // Add click handler to craft button
        for (int slot : CRAFT_BTN_SLOTS) {
            menu.addMenuClickHandler(slot, (player, i, itemStack, clickAction) -> {
                // Craft item
                craft(b, menu, player);
                return false;
            });
        }
    }
    // }}}

    // {{{ Called when the block is broken
    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu) {
        Location l = menu.getLocation().add(0.5, 0.5, 0.5);

        // Drop menu items
        menu.dropItems(l, IN_SLOTS);
        menu.dropItems(l, OUT_SLOTS);

        // Play sound
        e.getBlock().getWorld().playSound(l, Sound.BLOCK_BEACON_DEACTIVATE, 1F, 1F);
    }
    // }}}

    // {{{ Craft
    @Override
    protected void craft(@Nonnull Block b, @Nonnull BlockMenu menu, @Nonnull Player p) {
        // Get expected output
        ItemStack[] input = new ItemStack[9];

        int index = 0;
        for (int i : IN_SLOTS) {
            input[index] = menu.getItemInSlot(i);
            index++;
        }

        T item = this.recipes.get(input);

        // Invalid recipe
        if (item == null) {
            p.sendMessage(AlchimiaUtils.format("<red>That recipe is invalid!"));
            return;
        }

        // Consume items
        for (int slot : IN_SLOTS) {
            if (menu.getItemInSlot(slot) != null) {
                menu.consumeItem(slot, 1);
            }
        }

        // Finish crafting
        this.finish(b.getWorld(), b.getLocation().add(0.5, 0.5, 0.5), menu, item);
    }
    // }}}

    // {{{ Register
    @Override
    public void register(SlimefunAddon instance) {
        super.register(instance);

        // Add default recipes
        this.addDefaultRecipes();
    }
    // }}}
    // }}}

}

// {{{ RecipeMap class
/**
 * Class to hold a map of {@link ItemStack} arrays
 * (recipes) to values for {@link AbstractCrafter} recipes
 */
class RecipeMap<T> {

    private final Map<Integer, T> baseMap = new HashMap<>();

    /**
     * Put a recipe into the map
     *
     * @param output Crafting output
     * @param input Recipe used to craft the output
     */
    public void put(@Nonnull T output, @Nonnull ItemStack... input) {
        baseMap.put(Arrays.hashCode(input), output);
    }

    /**
     * Get a recipe from the map
     *
     * @param input Recipe used to craft the output
     *
     * @return Crafting output
     */
    @Nullable
    public T get(ItemStack... input) {
        return baseMap.get(Arrays.hashCode(input));
    }

}
// }}}

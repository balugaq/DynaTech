package me.profelements.dynatech.fluids;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.balugaq.compatibility.MinecraftVersion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import com.google.common.base.Preconditions;

public class FluidTankAdapter {

    private FluidTankAdapter() {
    }

    public static @Nullable FluidStack getFluidFromItemStack(@Nonnull ItemStack itemStack) {
        switch (itemStack.getType()) {
            case WATER_BUCKET:
                return FluidStack.of(FluidStack.WATER_FLUID, FluidStack.BUCKET_AMOUNT);
            case MILK_BUCKET:
                return FluidStack.of(FluidStack.MILK_FLUID, FluidStack.BUCKET_AMOUNT);
            case LAVA_BUCKET:
                return FluidStack.of(FluidStack.LAVA_FLUID, FluidStack.BUCKET_AMOUNT);
            case POTION:
                if (itemStack.getItemMeta() instanceof PotionMeta pm) {
                    if (MinecraftVersion.AT_LEAST_1_20_5) {
                        if (pm.getBasePotionType() == PotionType.WATER) {
                            return FluidStack.of(FluidStack.WATER_FLUID, FluidStack.BOTTLE_AMOUNT);
                        } else {
                            return FluidStack.of(FluidStack.POTION_FLUID, FluidStack.BOTTLE_AMOUNT);
                        }
                    } else {
                        if (pm.getBasePotionData().getType() == PotionType.WATER) {
                            return FluidStack.of(FluidStack.WATER_FLUID, FluidStack.BOTTLE_AMOUNT);
                        } else {
                            return FluidStack.of(FluidStack.POTION_FLUID, FluidStack.BOTTLE_AMOUNT);
                        }
                    }
                }
            default:
                return null;
        }
    }

    public static @Nullable FluidStack getFluidStackFromBlock(@Nonnull Block block) {
        Preconditions.checkNotNull(block);
        switch (block.getType()) {
            case LAVA_CAULDRON:
                return FluidStack.of(FluidStack.LAVA_FLUID, FluidStack.BUCKET_AMOUNT);
            case WATER_CAULDRON:
            case WATER:
            case LAVA:
                return getFluidStackFromLevelled(block);
            default:
                return null;
        }
    }

    public static @Nullable FluidStack getFluidStackFromLevelled(@Nonnull Block block) {
        Preconditions.checkNotNull(block);

        if (!(block.getBlockData() instanceof Levelled) || block.getType() == Material.COMPOSTER) {
            return null;
        }

        Levelled lvl = (Levelled) block.getBlockData();

        // Level 0 is special cased as a source block of water or lava.
        // Cauldron level starts at 1;
        // Max cauldron level = 3. so this is a Levelled source we can't use
        if (lvl.getLevel() > 3) {
            return null;
        }

        // This is either Material.LAVA, or Material.WATER
        if (lvl.getLevel() == 0) {
            switch (block.getType()) {
                case WATER:
                    return FluidStack.of(FluidStack.WATER_FLUID, FluidStack.BUCKET_AMOUNT);
                case LAVA:
                    return FluidStack.of(FluidStack.LAVA_FLUID, FluidStack.BUCKET_AMOUNT);
                default:
                    return null;
            }
        }

        if (block.getType() == Material.WATER_CAULDRON) {
            return FluidStack.of(FluidStack.WATER_FLUID, (lvl.getLevel() / lvl.getMaximumLevel()) * 1000);
        }

        return null;
    }
}

package com.aranaira.magichem.events;

import com.aranaira.magichem.block.entity.ext.AbstractBlockEntityWithEfficiency;
import com.aranaira.magichem.registry.ItemRegistry;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CommonEventHelper {
    public static void generateWasteFromCleanedApparatus(Level level, AbstractBlockEntityWithEfficiency bewe, @Nullable ItemStack stackToDamage) {
        int wasteCount = bewe.clean();
        if(wasteCount > 0 && stackToDamage != null) {
            stackToDamage.setDamageValue(stackToDamage.getDamageValue() + 1);
        }

        SimpleContainer wasteItems = new SimpleContainer(wasteCount / 64 + 1);
        for (int i = 0; i < wasteCount / 64 + 1; i++) {
            int thisAmount = Math.min(wasteCount, 64);
            wasteItems.setItem(i, new ItemStack(ItemRegistry.ALCHEMICAL_WASTE.get(), thisAmount));
            wasteCount -= thisAmount;
            if(wasteCount <= 0)
                break;
        }

        Containers.dropContents(level, bewe.getBlockPos(), wasteItems);
    }
}

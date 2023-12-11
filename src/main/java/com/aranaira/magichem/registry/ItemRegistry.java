package com.aranaira.magichem.registry;

import com.aranaira.magichem.MagiChemMod;
import com.aranaira.magichem.item.EssentiaItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.Collectors;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MagiChemMod.MODID);
    public static final DeferredRegister<Item> ESSENTIA = DeferredRegister.create(ForgeRegistries.ITEMS, MagiChemMod.MODID);

    public static final RegistryObject<Item> SILVER_DUST = ITEMS.register("silver_dust",
            () -> new Item(new Item.Properties().durability(64).defaultDurability(64).setNoRepair().tab(CreativeModeTabs.MAGICHEM_TAB))
    );

    public static final RegistryObject<Item> TARNISHED_SILVER_LUMP = ITEMS.register("tarnished_silver_lump",
            () -> new Item(new Item.Properties().tab(CreativeModeTabs.MAGICHEM_TAB))
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        ESSENTIA.register(eventBus);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static RegistryObject<Item> getRegistryObject(DeferredRegister<Item> register, String name) {
        return register.getEntries().stream().filter(item -> item.getId().getPath().equals(name)).findFirst().get();
    }

    public static List<EssentiaItem> getEssentia() {
        return ESSENTIA.getEntries().stream().map(RegistryObject::get).map(item -> (EssentiaItem) item).collect(Collectors.toList());
    }
}

package com.aranaira.magichem.gui;

import com.aranaira.magichem.block.entity.ActuatorAirBlockEntity;
import com.aranaira.magichem.networking.ActuatorSyncPowerLevelC2SPacket;
import com.aranaira.magichem.registry.BlockRegistry;
import com.aranaira.magichem.registry.MenuRegistry;
import com.aranaira.magichem.registry.PacketRegistry;
import com.aranaira.magichem.util.InventoryHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.joml.Vector2i;

public class ActuatorAirMenu extends AbstractContainerMenu {

    public final ActuatorAirBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    private static final int
        SLOT_FUEL_X = 70, SLOT_FUEL_Y = 36;

    public ActuatorAirMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(ActuatorAirBlockEntity.DATA_COUNT));
    }

    public ActuatorAirMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(MenuRegistry.ACTUATOR_AIR_MENU.get(), id);
        blockEntity = (ActuatorAirBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, SLOT_FUEL_X, SLOT_FUEL_Y));
        });

        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, BlockRegistry.ACTUATOR_AIR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for(int i=0; i<3; i++) {
            for(int l=0; l<9; l++) {
                this.addSlot((new Slot(playerInventory, l + i*9 + 9, 8 + l*18, 81 + i*18)));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for(int i=0; i<9; i++) {
            this.addSlot((new Slot(playerInventory, i, 8 + i*18, 139 )));
        }
    }

    public int getPowerLevel() {
        return data.get(ActuatorAirBlockEntity.DATA_POWER_LEVEL);
    }

    public int getFlags() {
        return data.get(ActuatorAirBlockEntity.DATA_FLAGS);
    }

    public void incrementPowerLevel() {
        int previous = getPowerLevel();
        int current = Math.min(3, getPowerLevel() + 1);
        if(previous != current) {
            PacketRegistry.sendToServer(new ActuatorSyncPowerLevelC2SPacket(
                    blockEntity.getBlockPos(), true
            ));
        }
    }

    public void decrementPowerLevel() {
        int previous = getPowerLevel();
        int current = Math.max(1, getPowerLevel() - 1);
        if(previous != current) {
            PacketRegistry.sendToServer(new ActuatorSyncPowerLevelC2SPacket(
                    blockEntity.getBlockPos(), false
            ));
        }
    }

    public int getRemainingEldrinTime() {
        return data.get(ActuatorAirBlockEntity.DATA_REMAINING_ELDRIN_TIME);
    }

    public int getSmokeInTank() { return data.get(ActuatorAirBlockEntity.DATA_SMOKE); }

    public int getSteamInTank() { return data.get(ActuatorAirBlockEntity.DATA_STEAM); }

    private static final int
            SLOT_INVENTORY_BEGIN = 0,
            SLOT_INVENTORY_COUNT = 36;
    Pair<Item, Integer>[] DIRSPEC = new Pair[]{};
    Vector2i[] SPEC_FROM_INVENTORY = new Vector2i[] {
            new Vector2i(SLOT_INVENTORY_BEGIN, SLOT_INVENTORY_COUNT)
    };
    Vector2i[] SPEC_TO_INVENTORY = new Vector2i[] {
            new Vector2i(SLOT_INVENTORY_BEGIN, SLOT_INVENTORY_COUNT)
    };
    Pair<Integer, Vector2i> SPEC_CONTAINER = null;

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack result = InventoryHelper.quickMoveStackHandler(pIndex, slots, DIRSPEC, new Vector2i(SLOT_INVENTORY_BEGIN, SLOT_INVENTORY_COUNT), SPEC_FROM_INVENTORY, SPEC_TO_INVENTORY, SPEC_CONTAINER);

        slots.get(pIndex).set(result);

        return ItemStack.EMPTY;
    }
}

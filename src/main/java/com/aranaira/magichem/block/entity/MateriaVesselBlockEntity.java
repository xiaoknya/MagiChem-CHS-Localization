package com.aranaira.magichem.block.entity;

import com.aranaira.magichem.Config;
import com.aranaira.magichem.item.EssentiaItem;
import com.aranaira.magichem.item.MateriaItem;
import com.aranaira.magichem.registry.BlockEntitiesRegistry;
import com.aranaira.magichem.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MateriaVesselBlockEntity extends BlockEntity {

    public MateriaVesselBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntitiesRegistry.MATERIA_VESSEL_BE.get(), pos, state);
    }

    private int currentStock;
    private MateriaItem currentMateriaType;

    public int getCurrentStock() {
        return currentStock;
    }

    public float getCurrentStockPercent() {
        return (float) currentStock / (float) getStorageLimit();
    }

    public MateriaItem getMateriaType() {
        return currentMateriaType;
    }

    public void setContents(MateriaItem item, int count) {
        syncAndSave();
        currentMateriaType = item;
        currentStock = count;
    }

    public int fill(int amount, boolean voidExcess) {
        syncAndSave();
        int test = currentStock + amount;
        int actual = amount;
        if(test > getStorageLimit()) {
            if(!voidExcess) {
                actual = getStorageLimit() - currentStock;
            }
            currentStock = getStorageLimit();
        } else {
            currentStock = test;
        }
        return actual;
    }

    public int drain(int amount) {
        syncAndSave();
        int test = currentStock - amount;
        int actual = amount;
        if(test < 0) {
            actual = currentStock;
            currentStock = 0;
            currentMateriaType = null;
        } else {
            currentStock = test;
        }
        return actual;
    }

    @Override
    public void load(CompoundTag nbt) {
        String regName = nbt.getString("type");
        currentStock = nbt.getInt("amount");
        if(!regName.equals("empty")) {
            currentMateriaType = ItemRegistry.getMateriaMap(false, false).get(regName);
        }
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putString("type",
                this.currentMateriaType == null ? "empty" : this.currentMateriaType.getMateriaName());
        nbt.putInt("amount", this.currentStock);
        super.saveAdditional(nbt);
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        String regName = nbt.getString("type");
        currentStock = nbt.getInt("amount");
        if(!regName.equals("empty")) {
            currentMateriaType = ItemRegistry.getMateriaMap(false, false).get(regName);
        }
        super.handleUpdateTag(nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(pkt.getTag());
        super.onDataPacket(net, pkt);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag data = new CompoundTag();
        data.putString("type",
                this.currentMateriaType == null ? "empty" : this.currentMateriaType.getMateriaName());
        data.putInt("amount", currentStock);
        return data;
    }

    public final void syncAndSave() {
        if (!this.getLevel().isClientSide()) {
            this.setChanged();
            this.getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Try to insert a stack into the materia vessel.
     * @param stack The materia to insert. Must be some kind of MateriaItem.
     * @return The number of materia items inserted. Use this to create bottles.
     */
    public int insertMateria(ItemStack stack) {
        if(currentMateriaType == null) {
            currentMateriaType = (MateriaItem) stack.getItem();
            currentStock = stack.getCount();
            this.syncAndSave();
            return stack.getCount();
        } else if(stack.getItem() != currentMateriaType) {
            return 0;
        } else if(currentStock >= getStorageLimit()) {
            return 0;
        } else {
            int tryStock = currentStock + stack.getCount();

            int increase = 0;
            if(getStorageLimit() - tryStock >= 0) {
                increase = stack.getCount();
            } else {
                increase = stack.getCount() - (tryStock - getStorageLimit());
            }
            currentStock += increase;
            this.syncAndSave();
            return increase;
        }
    }

    public ItemStack extractMateria(int amount) {
        if(currentMateriaType == null)
            return ItemStack.EMPTY;

        int extractedAmount = Math.min(currentStock, amount);
        ItemStack extractedMateria = new ItemStack(currentMateriaType, extractedAmount);

        if(currentStock - extractedAmount <= 0) {
            currentMateriaType = null;
            currentStock = 0;
        } else {
            currentStock -= extractedAmount;
        }

        this.syncAndSave();
        return extractedMateria;
    }

    public int getStorageLimit() {
        if(currentMateriaType instanceof EssentiaItem) {
            return Config.materiaVesselEssentiaCapacity;
        }
        return Config.materiaVesselAdmixtureCapacity;
    }
}

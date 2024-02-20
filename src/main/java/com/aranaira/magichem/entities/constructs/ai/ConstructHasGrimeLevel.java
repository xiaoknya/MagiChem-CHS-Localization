package com.aranaira.magichem.entities.constructs.ai;

import com.aranaira.magichem.block.entity.AlembicBlockEntity;
import com.aranaira.magichem.block.entity.ext.BlockEntityWithEfficiency;
import com.aranaira.magichem.registry.ConstructTasksRegistry;
import com.mna.api.ManaAndArtificeMod;
import com.mna.api.entities.construct.IConstruct;
import com.mna.api.entities.construct.ai.ConstructAITask;
import com.mna.api.entities.construct.ai.parameter.ConstructAITaskParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskIntegerParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskPointParameter;
import com.mna.entities.constructs.ai.conditionals.ConstructConditional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public class ConstructHasGrimeLevel extends ConstructConditional<ConstructHasGrimeLevel> {
    private BlockEntityWithEfficiency targetApparatus = null;
    private float targetPercentage = 1;

    public ConstructHasGrimeLevel(IConstruct<?> construct, ResourceLocation guiIcon) {
        super(construct, guiIcon);
    }

    @Override
    protected boolean evaluate() {
        if(targetApparatus == null)
            return false;

        float a = (float)((AlembicBlockEntity)targetApparatus).getGrime();
        float a1 = (float)targetApparatus.getGrimeFromData();
        float b = (float)targetApparatus.getMaximumGrime();
        float c = a/b;
        boolean d = c >= targetPercentage;

        return ((float)targetApparatus.getGrimeFromData() / (float)targetApparatus.getMaximumGrime()) >= targetPercentage;
    }

    @Override
    public ResourceLocation getType() {
        return ManaAndArtificeMod.getConstructTaskRegistry().getKey(ConstructTasksRegistry.QUERY_HAS_GRIME_LEVEL);
    }

    @Override
    protected List<ConstructAITaskParameter> instantiateParameters() {
        List<ConstructAITaskParameter> parameters = super.instantiateParameters();
        parameters.add(new ConstructTaskPointParameter("query_has_grime_level.point"));
        parameters.add(new ConstructTaskIntegerParameter("query_has_grime_level.int", 1, 100));
        return parameters;
    }

    @Override
    public void inflateParameters() {
        this.getParameter("query_has_grime_level.point").ifPresent((param) -> {
            if (param instanceof ConstructTaskPointParameter pointParam) {
                BlockPos targetVesselPos = pointParam.getPosition();
                if(targetVesselPos != null) {
                    BlockEntity be = Minecraft.getInstance().level.getBlockEntity(targetVesselPos);
                    if (be != null) {
                        if (be instanceof BlockEntityWithEfficiency bewe) {
                            targetApparatus = bewe;
                        }
                    }
                }
            }
        });

        this.getParameter("query_has_grime_level.int").ifPresent((param) -> {
            if (param instanceof ConstructTaskIntegerParameter integerParam) {
                targetPercentage = (float)integerParam.getValue() / 100.0f;
            }
        });
    }

    @Override
    public boolean isFullyConfigured() {
        return targetApparatus != null;
    }

    @Override
    public ConstructHasGrimeLevel copyFrom(ConstructAITask<?> other) {
        if(other instanceof ConstructHasGrimeLevel task) {
            this.targetApparatus = task.targetApparatus;
            this.targetPercentage = task.targetPercentage;
        }

        return this;
    }

    @Override
    public ConstructHasGrimeLevel duplicate() {
        return new ConstructHasGrimeLevel(this.construct, this.guiIcon).copyFrom(this);
    }
}

package com.hypothete.diffuser.entities;

import java.util.List;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.FluidFX;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;

import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;

public class DiffuserBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

  public static final int FILLING_TIME = 20;
  SmartFluidTankBehaviour tank;
  public int processingTicks;

  public DiffuserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    if (cap == ForgeCapabilities.FLUID_HANDLER) {
      if (side != Direction.DOWN) {
        return null;
      }
      return tank.getCapability().cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void invalidateCaps() {
    super.invalidateCaps();
  }

  @Override
  public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    behaviours.add(tank = SmartFluidTankBehaviour.single(this, 250)
        .allowInsertion()
        .forbidExtraction());
  }

  @Override
  public void tick() {
    super.tick();

    if (processingTicks >= 0) {
      processingTicks--;
    }

    if (processingTicks == -1) {
      processingTicks = FILLING_TIME;
    }

    if (processingTicks >= 8 && level.isClientSide) {
      spawnProcessingParticles(tank.getPrimaryTank()
          .getRenderedFluid());
    }
  }

  protected void spawnProcessingParticles(FluidStack fluid) {
    if (isVirtual() || fluid.isEmpty())
      return;
    Vec3 vec = VecHelper.getCenterOf(worldPosition);
    vec = vec.add(0, 8 / 16f, 0);
    ParticleOptions particle = FluidFX.getFluidParticle(fluid);
    level.addAlwaysVisibleParticle(particle, vec.x, vec.y, vec.z, 0, .1f, 0);
  }
}

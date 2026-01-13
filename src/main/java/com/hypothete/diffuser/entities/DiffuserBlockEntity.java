package com.hypothete.diffuser.entities;

import java.util.List;

import com.simibubi.create.api.effect.OpenPipeEffectHandler;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.FluidFX;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidHelper;

import net.createmod.catnip.math.VecHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;

public class DiffuserBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

  public static final int FILLING_TIME = 25;
  public static final int HALF_AREA_SIZE = 4;
  SmartFluidTankBehaviour tank;
  static RandomSource r = RandomSource.create();
  public int processingTicks;

  public DiffuserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    processingTicks = r.nextIntBetweenInclusive(0, FILLING_TIME);
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    if (cap == ForgeCapabilities.FLUID_HANDLER && side == Direction.DOWN) {
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
    behaviours.add(tank = SmartFluidTankBehaviour.single(this, 50)
        .allowInsertion()
        .forbidExtraction());
  }

  private FluidStack getCurrentFluidInTank() {
    return tank.getPrimaryHandler()
        .getFluid();
  }

  @Override
  public void tick() {
    super.tick();
    if (processingTicks >= 0) {
      var currentFluidInTank = getCurrentFluidInTank();
      var currentAmountInTank = currentFluidInTank.getAmount();
      if (currentAmountInTank > 0) {
        tank.getPrimaryHandler()
            .setFluid(FluidHelper.copyStackWithAmount(currentFluidInTank, currentAmountInTank - 1));
      }
      processingTicks--;
    }

    if (processingTicks == -1) {
      processingTicks = FILLING_TIME;
      OpenPipeEffectHandler effectHandler = OpenPipeEffectHandler.REGISTRY.get(getCurrentFluidInTank().getFluid());
      if (effectHandler == null) {
        return;
      }
      Vec3 center = VecHelper.getCenterOf(worldPosition);
      Vec3 aa = center.subtract(HALF_AREA_SIZE, HALF_AREA_SIZE, HALF_AREA_SIZE);
      Vec3 bb = center.add(HALF_AREA_SIZE, HALF_AREA_SIZE, HALF_AREA_SIZE);
      effectHandler.apply(level, new AABB(aa.x, aa.y, aa.z, bb.x, bb.y, bb.z), getCurrentFluidInTank());
    }

    if (processingTicks < 3 && level.isClientSide && !tank.isEmpty()) {
      spawnProcessingParticles(tank.getPrimaryTank().getRenderedFluid());
    }
  }

  protected void spawnProcessingParticles(FluidStack fluid) {
    if (isVirtual() || fluid.isEmpty())
      return;
    Vec3 vec = VecHelper.getCenterOf(worldPosition);
    Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, r, 1).normalize();
    vec = vec.add(offset.x, 12 / 16f, offset.z);
    ParticleOptions particle = ParticleTypes.POOF;
    if (r.nextFloat() < 1 / 10f) {
      particle = FluidFX.getFluidParticle(fluid);
    }
    level.addAlwaysVisibleParticle(particle, vec.x, vec.y, vec.z, 0, .01f, 0);
  }

  @Override
  public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
    return containedFluidTooltip(tooltip, isPlayerSneaking, tank.getCapability().cast());
  }
}

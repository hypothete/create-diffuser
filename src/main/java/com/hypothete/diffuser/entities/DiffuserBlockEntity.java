package com.hypothete.diffuser.entities;

import java.util.List;
import java.util.Optional;

import com.hypothete.diffuser.blocks.DiffuserBlock;
import com.hypothete.diffuser.data.FluidEffect;
import com.hypothete.diffuser.data.FluidEffectManager;
import com.hypothete.diffuser.effects.CustomFluidEffectHandler;
import com.simibubi.create.api.effect.OpenPipeEffectHandler;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.FluidFX;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidHelper;

import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.outliner.Outliner;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
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
  private static CustomFluidEffectHandler customEffectHandler = new CustomFluidEffectHandler();

  public DiffuserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    processingTicks = r.nextIntBetweenInclusive(0, FILLING_TIME);
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    var facingOut = getBlockState().getValue(DiffuserBlock.FACING).getOpposite();
    if (cap == ForgeCapabilities.FLUID_HANDLER && (side == facingOut || side == null)) {
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
    behaviours.add(tank = SmartFluidTankBehaviour.single(this, 25)
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

    if (processingTicks < 3 && level.isClientSide && !tank.isEmpty()) {
      spawnDiffusingParticles(tank.getPrimaryTank().getRenderedFluid());
    }

    if (processingTicks == -1) {
      processingTicks = FILLING_TIME;
      var currentFluid = getCurrentFluidInTank().getFluid();
      var aoe = getAreaOfEffect();

      // if we have a custom fluidEffect, apply it
      var foundFluidEffect = getFluidEffect(currentFluid);
      if (foundFluidEffect.isPresent()) {
        customEffectHandler.apply(level, aoe, foundFluidEffect.get());
        return;
      }

      // fall back on open pipe behavior
      OpenPipeEffectHandler effectHandler = OpenPipeEffectHandler.REGISTRY.get(currentFluid);
      if (effectHandler == null) {
        return;
      }

      effectHandler.apply(level, aoe, getCurrentFluidInTank());
    }
  }

  public Optional<FluidEffect> getFluidEffect(Fluid fluid) {
    return level.registryAccess()
        .lookupOrThrow(FluidEffectManager.FLUID_EFFECTS_KEY)
        .listElements()
        .filter(ref -> ref.value().fluid().equals(fluid))
        .findFirst().map(ref -> ref.value());
  }

  protected AABB getAreaOfEffect() {
    Direction facing = getBlockState().getValue(DiffuserBlock.FACING);
    var scaledFacing = facing.step().mul(HALF_AREA_SIZE);
    return new AABB(worldPosition)
        .move(scaledFacing.x, scaledFacing.y, scaledFacing.z)
        .inflate(HALF_AREA_SIZE);
  }

  protected void spawnDiffusingParticles(FluidStack fluid) {
    if (isVirtual() || fluid.isEmpty())
      return;
    
    Vec3 startPos = VecHelper.getCenterOf(worldPosition);
    var facing = new Vec3(getBlockState().getValue(DiffuserBlock.FACING).step());
    startPos = startPos.add(facing.scale(12 /16f)); // block model is 3/4 of a block tall
    Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, r, 1).normalize();
    var dot = offset.dot(facing);
    ParticleOptions particle = ParticleTypes.POOF;
    if (r.nextFloat() < 1 / 3f) {
      particle = FluidFX.getFluidParticle(fluid);
    }
    Vec3 particleMotion = offset.scale(dot * 0.5); // slow it down a titch
    level.addAlwaysVisibleParticle(particle, startPos.x, startPos.y, startPos.z, particleMotion.x, particleMotion.y, particleMotion.z);

  }

  @Override
  public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

    // show area of effect
    if (isPlayerSneaking) {
      var aoe = getAreaOfEffect();
      Outliner.getInstance().showAABB(this, aoe);
    }

    return containedFluidTooltip(tooltip, isPlayerSneaking, tank.getCapability().cast());
  }
}

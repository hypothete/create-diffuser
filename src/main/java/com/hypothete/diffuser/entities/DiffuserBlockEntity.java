package com.hypothete.diffuser.entities;

import java.lang.ref.WeakReference;
import java.util.List;

import com.hypothete.diffuser.blocks.DiffuserBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DiffuserBlockEntity extends SmartBlockEntity {
  public WeakReference<FluidTankBlockEntity> source;
  static RandomSource r = RandomSource.create();

  public DiffuserBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
    source = new WeakReference<>(null);
  }

  @Override
  public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    return;
  }

  @Override
  public void tick() {
    super.tick();
    FluidTankBlockEntity tank = getTank();
    if (tank == null) {
      return;
    }
  }

  public FluidTankBlockEntity getTank() {
    FluidTankBlockEntity tank = source.get();
    if (tank == null || tank.isRemoved()) {
      if (tank != null)
        source = new WeakReference<>(null);
      Direction facing = DiffuserBlock.getAttachedDirection(getBlockState());
      BlockEntity be = level.getBlockEntity(worldPosition.relative(facing));
      if (be instanceof FluidTankBlockEntity tankBe)
        source = new WeakReference<>(tank = tankBe);
    }
    if (tank == null)
      return null;
    return tank.getControllerBE();
  }

}

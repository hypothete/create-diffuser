package com.hypothete.diffuser.blocks;

import com.hypothete.diffuser.entities.DiffuserBlockEntity;
import com.hypothete.diffuser.entities.ModBlockEntities;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DiffuserBlock extends Block implements IWrenchable, IBE<DiffuserBlockEntity> {
  public DiffuserBlock(Properties properties) {
    super(properties);
  }

  public static final VoxelShape SHAPE = Block.box(1, 0, 1, 14, 12, 14);

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  @Override
  public Class<DiffuserBlockEntity> getBlockEntityClass() {
    return DiffuserBlockEntity.class;
  }

  @Override
  public BlockEntityType<? extends DiffuserBlockEntity> getBlockEntityType() {
    return ModBlockEntities.DIFFUSER_BE.get();
  }

  @Override
  public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
    IBE.onRemove(state, level, pos, newState);
  }
}

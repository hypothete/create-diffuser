package com.hypothete.diffuser.blocks;

import com.hypothete.diffuser.entities.DiffuserBlockEntity;
import com.hypothete.diffuser.entities.ModBlockEntities;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.WrenchableDirectionalBlock;

import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DiffuserBlock extends WrenchableDirectionalBlock implements IBE<DiffuserBlockEntity> {
  public static final VoxelShape baseShape = Block.box(1, 0, 1, 15, 12, 15);
  public static final VoxelShaper shaper = VoxelShaper.forDirectional(baseShape, Direction.UP);
  
  public DiffuserBlock(Properties properties) {
    super(properties);
    registerDefaultState(defaultBlockState());
  }

  @Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return shaper.get(state.getValue(FACING));
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    BlockState placed = super.getStateForPlacement(context);
    return placed = placed.setValue(FACING, context.getClickedFace());
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

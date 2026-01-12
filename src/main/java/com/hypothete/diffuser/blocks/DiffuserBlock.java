package com.hypothete.diffuser.blocks;

import com.simibubi.create.content.equipment.wrench.IWrenchable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

public class DiffuserBlock extends Block implements IWrenchable {
  public DiffuserBlock(Properties properties) {
    super(properties);
  }

  public static final VoxelShape SHAPE = Block.box(1, 0, 1, 14, 12, 14);
  public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    // public static final RegistryObject<Block> DIFFUSER_BLOCK = registerBlock("diffuser", () -> new DiffuserBlock(
  //     BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.5F).sound(SoundType.LANTERN).noOcclusion()));

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  public static Direction getAttachedDirection(BlockState state) {
    return state.getValue(FACING);
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
    super.createBlockStateDefinition(pBuilder.add(FACING));
  }
}

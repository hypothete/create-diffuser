package com.hypothete.diffuser.blocks;

import com.hypothete.diffuser.Diffuser;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;

public class ModBlocks {

  public static final BlockEntry<DiffuserBlock> DIFFUSER_BLOCK = Diffuser.REGISTRATE
      .block("diffuser", DiffuserBlock::new).initialProperties(() -> Blocks.IRON_BLOCK).properties(p -> p.sound(SoundType.LANTERN)).simpleItem().register();

  public static void register() {
  }
}

package com.hypothete.diffuser.blocks;

import com.hypothete.diffuser.Diffuser;
import com.tterrag.registrate.util.entry.BlockEntry;

public class ModBlocks {

  public static final BlockEntry<DiffuserBlock> DIFFUSER_BLOCK = Diffuser.REGISTRATE.block("diffuser", DiffuserBlock::new).simpleItem().register();

  public static void register() {
  }
}

package com.hypothete.diffuser.entities;

import com.hypothete.diffuser.Diffuser;
import com.hypothete.diffuser.blocks.ModBlocks;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {

  public static final BlockEntityEntry<DiffuserBlockEntity> DIFFUSER_BE = Diffuser.REGISTRATE.blockEntity("diffuser", DiffuserBlockEntity::new)
  .validBlocks(ModBlocks.DIFFUSER_BLOCK)
  .register();
  
  public static void register() {
  }
}

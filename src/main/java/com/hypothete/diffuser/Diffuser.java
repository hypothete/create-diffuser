package com.hypothete.diffuser;

import com.hypothete.diffuser.blocks.DiffuserBlock;
import com.hypothete.diffuser.entities.DiffuserBlockEntity;
import com.hypothete.diffuser.items.CreativeModeTabs;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("diffuser")
public class Diffuser {

        public static final String MOD_ID = "diffuser";
        public static final Logger LOGGER = LogManager.getLogger();
        public static final Registrate REGISTRATE = Registrate.create(MOD_ID);

        public static final BlockEntry<DiffuserBlock> DIFFUSER_BLOCK = REGISTRATE
                        .block("diffuser", DiffuserBlock::new)
                        .initialProperties(() -> Blocks.IRON_BLOCK)
                        .properties(p -> p.noOcclusion())
                        .properties(p -> p.sound(SoundType.LANTERN))
                        .blockstate(
                                        (c, p) -> p.directionalBlock(c.get(),
                                                        p.models().getExistingFile(p.modLoc("block/diffuser.json"))))
                        .simpleItem()
                        .register();

        public static final BlockEntityEntry<DiffuserBlockEntity> DIFFUSER_BE = Diffuser.REGISTRATE
                        .blockEntity("diffuser", DiffuserBlockEntity::new)
                        .validBlocks(DIFFUSER_BLOCK)
                        .register();

        public Diffuser() {

                IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
                CreativeModeTabs.register(MOD_BUS);
                MinecraftForge.EVENT_BUS.register(this);
        }
}

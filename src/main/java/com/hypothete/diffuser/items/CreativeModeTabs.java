package com.hypothete.diffuser.items;

import com.hypothete.diffuser.Diffuser;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabs {
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
      .create(Registries.CREATIVE_MODE_TAB, Diffuser.MOD_ID);

  public static final RegistryObject<CreativeModeTab> DIFFUSER_TAB = CREATIVE_MODE_TABS.register("diffuser_tab",
      () -> CreativeModeTab.builder()
          .title(Component.translatable("creativetab.diffuser.diffuser_tab"))
          .icon(() -> new ItemStack(Diffuser.DIFFUSER_BLOCK.get()))
          .displayItems((parameters, output) -> {
            output.accept(Diffuser.DIFFUSER_BLOCK.get());
          }).build());

  public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TABS.register(eventBus);
  }
}
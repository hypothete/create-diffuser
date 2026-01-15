package com.hypothete.diffuser.data;

import com.hypothete.diffuser.Diffuser;
import com.simibubi.create.api.equipment.potatoCannon.PotatoCannonProjectileType;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class FluidEffectManager {

  public static final ResourceKey<Registry<FluidEffect>> FLUID_EFFECTS_KEY = ResourceKey.createRegistryKey(new ResourceLocation("fluid_effect"));
  public static final DeferredRegister<FluidEffect> FLUID_EFFECTS = DeferredRegister
      .create(FLUID_EFFECTS_KEY, Diffuser.MOD_ID);

  public static void register(IEventBus eventBus) {
    FLUID_EFFECTS.register(eventBus);
  }

  public static void bootstrap(BootstapContext<FluidEffect> ctx) {
    // add defaults here as necessary
  }

}

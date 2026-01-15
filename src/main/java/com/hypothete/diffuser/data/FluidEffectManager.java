package com.hypothete.diffuser.data;

import com.hypothete.diffuser.Diffuser;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
public class FluidEffectManager {

  public static final ResourceKey<Registry<FluidEffect>> FLUID_EFFECTS_KEY = ResourceKey
      .createRegistryKey(ResourceLocation.fromNamespaceAndPath(Diffuser.MOD_ID, "fluid_effect"));

  public static void bootstrap(BootstapContext<FluidEffect> ctx) {
    Diffuser.LOGGER.info("bootstrap called, biyotch");
    // ctx.register(ResourceKey.create(FLUID_EFFECTS_KEY, new ResourceLocation(Diffuser.MOD_ID, "milk")),
    //     new FluidEffect.Builder().fluid(ForgeMod.MILK.get()).effect(MobEffects.BLINDNESS).build());
  }

}

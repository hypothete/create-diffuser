package com.hypothete.diffuser.data;

import com.hypothete.diffuser.Diffuser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public record FluidEffect (Fluid fluid, MobEffect effect) {

  public static final ResourceKey<Registry<FluidEffect>> FLUID_EFFECTS_KEY = ResourceKey
                        .createRegistryKey(ResourceLocation.fromNamespaceAndPath(Diffuser.MOD_ID, "fluid_effect"));
  
  public static final Codec<FluidEffect> CODEC = RecordCodecBuilder.create(i -> i.group(
    ForgeRegistries.FLUIDS.getCodec().fieldOf("fluid").forGetter(FluidEffect::fluid),
    ForgeRegistries.MOB_EFFECTS.getCodec().fieldOf("effect").forGetter(FluidEffect::effect)
  ).apply(i, FluidEffect::new));

  public static class Builder {
    private Fluid fluid = Fluids.EMPTY;
    private MobEffect effect;

    public Builder fluid(Fluid fluid) {
      this.fluid = fluid;
      return this;
    }

    public Builder effect(MobEffect effect) {
      this.effect = effect;
      return this;
    }

    public FluidEffect build() {
      return new FluidEffect(this.fluid, this.effect);
    }

  }

}
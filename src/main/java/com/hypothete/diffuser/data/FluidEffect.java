package com.hypothete.diffuser.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.material.Fluid;

public record FluidEffect (Holder<Fluid> fluid, Holder<MobEffect> effect) {
  
  public static final Codec<FluidEffect> CODEC = RecordCodecBuilder.create(i -> i.group(
    BuiltInRegistries.FLUID.holderByNameCodec().fieldOf("fluid").forGetter(FluidEffect::fluid),
    BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("effect").forGetter(FluidEffect::effect)
  ).apply(i, FluidEffect::new));

}
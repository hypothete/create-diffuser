package com.hypothete.diffuser.data;

import org.jetbrains.annotations.ApiStatus.Internal;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class DatapackRegistry {
  @Internal
	@SubscribeEvent
	public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
		event.dataPackRegistry(
			FluidEffect.FLUID_EFFECTS_KEY,
			FluidEffect.CODEC,
			FluidEffect.CODEC
		);
	}
}

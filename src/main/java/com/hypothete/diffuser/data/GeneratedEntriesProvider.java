package com.hypothete.diffuser.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.hypothete.diffuser.Diffuser;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class GeneratedEntriesProvider extends DatapackBuiltinEntriesProvider {

  private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
    .add(FluidEffectManager.FLUID_EFFECTS_KEY, FluidEffectManager::bootstrap);

  public GeneratedEntriesProvider(PackOutput output, CompletableFuture<Provider> registries) {
    super(output, registries, BUILDER, Set.of(Diffuser.MOD_ID));
  }

  @Override
	public String getName() {
		return "Diffuser's Registry Entries";
	}

}

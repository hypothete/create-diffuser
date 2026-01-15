package com.hypothete.diffuser.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;

public class Datagen {
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
    GeneratedEntriesProvider generatedEntriesProvider = new GeneratedEntriesProvider(output, lookupProvider);
		lookupProvider = generatedEntriesProvider.getRegistryProvider();
		generator.addProvider(event.includeServer(), generatedEntriesProvider);
  }
}

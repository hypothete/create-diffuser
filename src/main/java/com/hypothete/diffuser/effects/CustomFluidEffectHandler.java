package com.hypothete.diffuser.effects;

import java.util.List;

import com.hypothete.diffuser.data.FluidEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class CustomFluidEffectHandler {

	public static final int TICKS = 50;
	public static final int LEVEL = 0;

	public void apply(Level level, AABB area, FluidEffect fluidEffect) {
		List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);
		MobEffectInstance effectInstance = new MobEffectInstance(fluidEffect.effect(), TICKS, LEVEL);
		for (LivingEntity entity : entities) {
			MobEffect effect = effectInstance.getEffect();
			if (effect.isInstantenous()) {
				effect.applyInstantenousEffect(null, null, entity, effectInstance.getAmplifier(), 0.5D);
			} else {
				entity.addEffect(new MobEffectInstance(effectInstance));
			}
		}
	}
}

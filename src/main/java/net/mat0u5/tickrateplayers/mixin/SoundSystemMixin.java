package net.mat0u5.tickrateplayers.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.world.tick.TickManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SoundSystem.class)
public class SoundSystemMixin {
    @Inject(method = "getAdjustedPitch", at = @At("RETURN"), cancellable = true)
    private void getAdjustedPitch(SoundInstance sound, CallbackInfoReturnable<Float> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            TickManager tickManager = client.world.getTickManager();
            if (tickManager.getTickRate() != 20) {
                Float original = cir.getReturnValue();
                if (original == null) return;
                cir.setReturnValue(original * (tickManager.getTickRate() / 20.0f));
            }
        }
    }
}

package net.mat0u5.tickrateplayers.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.tick.TickManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "getTargetMillisPerTick", at = @At("HEAD"), cancellable = true)
    private void getTargetMillisPerTick(float millis, CallbackInfoReturnable<Float> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            TickManager tickManager = client.world.getTickManager();
            if (tickManager.shouldTick()) {
                cir.setReturnValue(tickManager.getMillisPerTick());
                return;
            }
        }
        cir.setReturnValue(millis);
    }
}

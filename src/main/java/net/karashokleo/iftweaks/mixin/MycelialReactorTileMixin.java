package net.karashokleo.iftweaks.mixin;

import com.buuz135.industrial.block.generator.tile.MycelialReactorTile;
import net.karashokleo.iftweaks.IFTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MycelialReactorTile.class, remap = false)
public abstract class MycelialReactorTileMixin
{
    @Inject(method = "getEnergyProducedEveryTick", at = @At("RETURN"), cancellable = true)
    private void getEnergyProducedEveryTick(CallbackInfoReturnable<Integer> cir) {
        if(!IFTweaks.EnableTweaks_Reactor.get())return;
        cir.setReturnValue(IFTweaks.EnergyProducedEveryTick_Reactor.get());
    }

    @Inject(method = "getEnergyCapacity", at = @At("RETURN"), cancellable = true)
    private void getEnergyCapacity(CallbackInfoReturnable<Integer> cir) {
        if(!IFTweaks.EnableTweaks_Reactor.get())return;
        cir.setReturnValue(IFTweaks.EnergyCapacity_Reactor.get());
    }
}

package net.karashokleo.iftweaks.mixin;

import com.buuz135.industrial.entity.InfinityLauncherProjectileEntity;
import net.karashokleo.iftweaks.IFTweaks;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = InfinityLauncherProjectileEntity.class, remap = false)
public class InfinityLauncherProjectileEntityMixin
{
    @Final
    @Shadow
    private static EntityDataAccessor<Integer> TIER;

    @ModifyVariable(method = "onHitEntity", at = @At("STORE"), ordinal = 0)
    private int ModifyDamage(int x)
    {
        if(!IFTweaks.EnableTweaks_Launcher.get())return x;
        InfinityLauncherProjectileEntity entity = ((InfinityLauncherProjectileEntity)(Object)this);
        float f = (float)entity.getDeltaMovement().length();
        return Mth.ceil(Mth.clamp((double) f + (double)(Integer) entity.getEntityData().get(TIER) * IFTweaks.DamagePerTier_Launcher.get(), 0.0D, 2.147483647E9D));
    }
}

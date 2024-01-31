package net.karashokleo.iftweaks.mixin;

import com.buuz135.industrial.entity.InfinityTridentEntity;
import net.karashokleo.iftweaks.IFTweaks;
import net.minecraft.network.syncher.EntityDataAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = InfinityTridentEntity.class, remap = false)
public class InfinityTridentEntityMixin
{
    @Final
    @Shadow
    private static EntityDataAccessor<Integer> TIER;

    @Shadow
    public static int DAMAGE;

    @ModifyVariable(method = "onHitEntity", at = @At("STORE"), ordinal = 0)
    private float ModifyDamage(float x)
    {
        if(!IFTweaks.EnableTweaks_Trident.get())return x;
        return (float)((double)DAMAGE + ((InfinityTridentEntity) (Object) this).getEntityData().get(TIER)) * IFTweaks.DamagePerTier_Trident.get();
    }
}

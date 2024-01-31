package net.karashokleo.iftweaks.mixin;

import com.buuz135.industrial.item.infinity.InfinityTier;
import net.karashokleo.iftweaks.IFTweaks;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = InfinityTier.class, remap = false)
public class InfinityTierMixin
{
    InfinityTierMixin(String enumName, int ordinal, String name, long powerNeeded, int radius, ChatFormatting color, int textureColor){}

    @Final
    @Shadow
    @Mutable
    @SuppressWarnings("target")
    private static InfinityTier[] $VALUES;

    static
    {
        if(IFTweaks.EnableTweaks_Tiers.get())
        {
            $VALUES[0] = (InfinityTier) (Object) new InfinityTierMixin("POOR", 0, "poor", IFTweaks.PowerNeeded_Poor.get(), IFTweaks.Radius_Poor.get(), ChatFormatting.GRAY, 0x7c7c7a);
            $VALUES[1] = (InfinityTier) (Object) new InfinityTierMixin("COMMON", 1, "common", IFTweaks.PowerNeeded_Common.get(), IFTweaks.Radius_Common.get(), ChatFormatting.WHITE, 0xFFFFFF);
            $VALUES[2] = (InfinityTier) (Object) new InfinityTierMixin("UNCOMMON", 2, "uncommon", IFTweaks.PowerNeeded_Uncommon.get(), IFTweaks.Radius_Uncommon.get(), ChatFormatting.GREEN, 0x1ce819);
            $VALUES[3] = (InfinityTier) (Object) new InfinityTierMixin("RARE", 3, "rare", IFTweaks.PowerNeeded_Rare.get(), IFTweaks.Radius_Rare.get(), ChatFormatting.BLUE, 0x0087ff);
            $VALUES[4] = (InfinityTier) (Object) new InfinityTierMixin("EPIC", 4, "epic", IFTweaks.PowerNeeded_Epic.get(), IFTweaks.Radius_Epic.get(), ChatFormatting.DARK_PURPLE, 0xe100ff);
            $VALUES[5] = (InfinityTier) (Object) new InfinityTierMixin("LEGENDARY", 5, "legendary", IFTweaks.PowerNeeded_Legendary.get(), IFTweaks.Radius_Legendary.get(), ChatFormatting.GOLD, 0xffaa00);
            $VALUES[6] = (InfinityTier) (Object) new InfinityTierMixin("ARTIFACT", 6, "artifact", IFTweaks.PowerNeeded_Artifact.get(), IFTweaks.Radius_Artifact.get(), ChatFormatting.YELLOW, 0xfff887);
        }
    }
}

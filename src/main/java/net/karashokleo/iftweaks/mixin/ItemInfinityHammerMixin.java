package net.karashokleo.iftweaks.mixin;

import com.buuz135.industrial.item.infinity.InfinityTier;
import com.buuz135.industrial.item.infinity.ItemInfinity;
import com.buuz135.industrial.item.infinity.item.ItemInfinityHammer;
import net.karashokleo.iftweaks.IFTweaks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Mixin(value = ItemInfinityHammer.class, remap = false)
public class ItemInfinityHammerMixin
{
    @Shadow
    public static HashMap<Class<?>, Function<Entity, ItemStack>> HEADS;

    @Shadow
    public static int DAMAGE;

//    @Redirect(method = "hurtEnemy", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V", ordinal = 0))
//    private void InjectHurtEnemy(List<Mob> mobs, Consumer consumer){
//        mobs.forEach((mobEntity) -> {
//            ItemInfinityHammer item = (ItemInfinityHammer) (Object) this;
//            if (item.enoughFuel(stack)) {
//                mobEntity.hurt(DamageSource.playerAttack((Player)attacker), (float)((double)DAMAGE + Math.pow(2.0D, (double)infinityTier.getRadius())) * 0.8F);
//                item.consumeFuel(stack);
//                if (mobEntity.getHealth() <= 0.0F && attacker.getCommandSenderWorld().random.nextDouble() <= (double)item.getCurrentBeheading(stack) * 0.15D) {
//                    ItemStack head = (ItemStack)((Function)HEADS.getOrDefault(mobEntity.getClass(), (entity) -> {
//                        return ItemStack.EMPTY;
//                    })).apply(mobEntity);
//                    Block.popResource(attacker.level, attacker.blockPosition(), head);
//                }
//            }
//        });
//    }

    /**
     * @author Karashok_Leo
     * @reason ......
     */
    @Overwrite
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ItemInfinityHammer item = (ItemInfinityHammer) (Object) this;
        InfinityTier infinityTier = ItemInfinity.getSelectedTier(stack);
        if (infinityTier.getRadius() > 1 && attacker instanceof Player) {
            AABB area = (new AABB(target.getX(), target.getY(), target.getZ(), target.getX(), target.getY(), target.getZ())).inflate((double)infinityTier.getRadius());
            List<Mob> mobs = attacker.getCommandSenderWorld().getEntitiesOfClass(Mob.class, (new AABB(target.getX(), target.getY(), target.getZ(), target.getX(), target.getY(), target.getZ())).inflate((double)infinityTier.getRadius()));
            mobs.forEach((mobEntity) -> {
                if (item.enoughFuel(stack)) {
                    mobEntity.hurt(DamageSource.playerAttack((Player)attacker), ModifyDamage(infinityTier.getRadius()));
                    item.consumeFuel(stack);
                    if (mobEntity.getHealth() <= 0.0F && attacker.getCommandSenderWorld().random.nextDouble() <= (double)item.getCurrentBeheading(stack) * 0.15D) {
                        ItemStack head = (ItemStack)((Function)HEADS.getOrDefault(mobEntity.getClass(), (entity) -> {
                            return ItemStack.EMPTY;
                        })).apply(mobEntity);
                        Block.popResource(attacker.level, attacker.blockPosition(), head);
                    }
                }

            });
            attacker.getCommandSenderWorld().getEntitiesOfClass(ItemEntity.class, area.inflate(1.0D)).forEach((itemEntity) -> {
                itemEntity.setNoPickUpDelay();
                itemEntity.teleportTo((double)attacker.blockPosition().getX(), (double)(attacker.blockPosition().getY() + 1), (double)attacker.blockPosition().getZ());
            });
            attacker.getCommandSenderWorld().getEntitiesOfClass(ExperienceOrb.class, area.inflate(1.0D)).forEach((entityXPOrb) -> {
                entityXPOrb.teleportTo((double)attacker.blockPosition().getX(), (double)attacker.blockPosition().getY(), (double)attacker.blockPosition().getZ());
            });
        }

        if (target.getHealth() <= 0.0F && target instanceof Player) {
            Block.popResource(attacker.level, attacker.blockPosition(), ItemInfinityHammer.createHead(target.getDisplayName().getString()));
        }

        return true;
    }

    private float ModifyDamage(int radius)
    {
        if(!IFTweaks.EnableTweaks_Hammer.get())
            return (float)((double)DAMAGE + Math.pow(2.0D, (double)radius)) * 0.8F;
        return (float)((double)DAMAGE + radius * IFTweaks.DamagePerTier_Hammer.get());
    }
}

package net.karashokleo.iftweaks;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.nio.file.Path;

@Mod(IFTweaks.MODID)
public class IFTweaks
{
    public static final String MODID = "iftweaks";

    public static ForgeConfigSpec config;

    public static ForgeConfigSpec.BooleanValue EnableTweaks_Tiers;

    public static ForgeConfigSpec.LongValue PowerNeeded_Poor;
    public static ForgeConfigSpec.IntValue Radius_Poor;

    public static ForgeConfigSpec.LongValue PowerNeeded_Common;
    public static ForgeConfigSpec.IntValue Radius_Common;

    public static ForgeConfigSpec.LongValue PowerNeeded_Uncommon;
    public static ForgeConfigSpec.IntValue Radius_Uncommon;

    public static ForgeConfigSpec.LongValue PowerNeeded_Rare;
    public static ForgeConfigSpec.IntValue Radius_Rare;

    public static ForgeConfigSpec.LongValue PowerNeeded_Epic;
    public static ForgeConfigSpec.IntValue Radius_Epic;

    public static ForgeConfigSpec.LongValue PowerNeeded_Legendary;
    public static ForgeConfigSpec.IntValue Radius_Legendary;

    public static ForgeConfigSpec.LongValue PowerNeeded_Artifact;
    public static ForgeConfigSpec.IntValue Radius_Artifact;

    public static ForgeConfigSpec.BooleanValue EnableTweaks_Reactor;
    public static ForgeConfigSpec.IntValue EnergyProducedEveryTick_Reactor;
    public static ForgeConfigSpec.IntValue EnergyCapacity_Reactor;

    public static ForgeConfigSpec.BooleanValue EnableTweaks_Hammer;
    public static ForgeConfigSpec.IntValue DamagePerTier_Hammer;

    public static ForgeConfigSpec.BooleanValue EnableTweaks_Trident;
    public static ForgeConfigSpec.IntValue DamagePerTier_Trident;

    public static ForgeConfigSpec.BooleanValue EnableTweaks_Launcher;
    public static ForgeConfigSpec.IntValue DamagePerTier_Launcher;


    public IFTweaks()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        registerConfig();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void registerConfig(){
        String path = "IFTweaks.toml";

        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        {
            BUILDER.push("Infinity Tier");
            {
                EnableTweaks_Tiers = BUILDER
                        .define("EnableTweaks", false);
                BUILDER.push("Poor");
                PowerNeeded_Poor = BUILDER
                        .defineInRange("PowerNeeded", 0, 0, Long.MAX_VALUE);
                Radius_Poor = BUILDER
                        .defineInRange("Radius", 0, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            }
            {
                BUILDER.push("Common");
                PowerNeeded_Common = BUILDER
                        .defineInRange("PowerNeeded", 4_000_000, 0, Long.MAX_VALUE);
                Radius_Common = BUILDER
                        .defineInRange("Radius", 1, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            }
            {
                BUILDER.push("Uncommon");
                PowerNeeded_Uncommon = BUILDER
                        .defineInRange("PowerNeeded", 16_000_000, 0, Long.MAX_VALUE);
                Radius_Uncommon = BUILDER
                        .defineInRange("Radius", 2, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            }
            {
                BUILDER.push("Rare");
                PowerNeeded_Rare = BUILDER
                        .defineInRange("PowerNeeded", 80_000_000, 0, Long.MAX_VALUE);
                Radius_Rare = BUILDER
                        .defineInRange("Radius", 3, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            }
            {
                BUILDER.push("Epic");
                PowerNeeded_Epic = BUILDER
                        .defineInRange("PowerNeeded", 480_000_000, 0, Long.MAX_VALUE);
                Radius_Epic = BUILDER
                        .defineInRange("Radius", 4, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            }
            {
                BUILDER.push("Legendary");
                PowerNeeded_Legendary = BUILDER
                        .defineInRange("PowerNeeded", 3_360_000_000L, 0, Long.MAX_VALUE);
                Radius_Legendary = BUILDER
                        .defineInRange("Radius", 5, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            }
            {
                BUILDER.push("Artifact");
                PowerNeeded_Artifact = BUILDER
                        .defineInRange("PowerNeeded", Long.MAX_VALUE, 0, Long.MAX_VALUE);
                Radius_Artifact = BUILDER
                        .defineInRange("Radius", 6, 0, Integer.MAX_VALUE);
                BUILDER.pop();
            }
            BUILDER.pop();
        }
        {
            BUILDER.push("Mycelial Reactor");
            EnableTweaks_Reactor = BUILDER
                    .define("EnableTweaks", false);
            EnergyProducedEveryTick_Reactor = BUILDER
                    .defineInRange("EnergyProducedEveryTick", 25000000, 0, Integer.MAX_VALUE);
            EnergyCapacity_Reactor = BUILDER
                    .defineInRange("EnergyCapacity", 100000000, 0, Integer.MAX_VALUE);
            BUILDER.pop();
        }
        {
            BUILDER.push("Infinity Hammer");
            EnableTweaks_Hammer = BUILDER
                    .define("EnableTweaks", false);
            DamagePerTier_Hammer = BUILDER
                    .defineInRange("DamagePerTier", 12, 0, Integer.MAX_VALUE);
            BUILDER.pop();
        }
        {
            BUILDER.push("Infinity Trident");
            EnableTweaks_Trident = BUILDER
                    .define("EnableTweaks", false);
            DamagePerTier_Trident = BUILDER
                    .defineInRange("DamagePerTier", 12, 0, Integer.MAX_VALUE);
            BUILDER.pop();
        }
        {
            BUILDER.push("Infinity Launcher");
            EnableTweaks_Launcher = BUILDER
                    .define("EnableTweaks", false);
            DamagePerTier_Launcher = BUILDER
                    .defineInRange("DamagePerTier", 12, 0, Integer.MAX_VALUE);
            BUILDER.pop();
        }
        config = BUILDER.build();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config, path);
//        loadConfig(config, path);
    }
    private static void loadConfig(ForgeConfigSpec config, String location) {
        Path path = FMLPaths.CONFIGDIR.get().resolve(location);
        File configFolder = path.getParent().toFile();
        if (!configFolder.exists()) {
            configFolder.mkdirs();
        }

        CommentedFileConfig data = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        data.load();
        config.setConfig(data);
    }
}

package net.lpcamors.mohard.block;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.block.essence_altar.EssenceAltarBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MohardBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MohardMain.MODID);
    public static final DeferredRegister<Item> ITEM_BLOCKS = DeferredRegister.create(ForgeRegistries.ITEMS, MohardMain.MODID);

    public static void initiate(IEventBus bus){
        BLOCKS.register(bus);
        ITEM_BLOCKS.register(bus);
    }

    public static final RegistryObject<Block> ESSENCE_ALTAR = register("essence_altar", () ->
            new EssenceAltarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                    .strength(22.5F,600)
                    .lightLevel(value -> 5)
                    .requiresCorrectToolForDrops()
            ));
    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block){
        RegistryObject<T> ret = BLOCKS.register(name, block);
        Item.Properties prop = new Item.Properties();
        ITEM_BLOCKS.register(name, ()->
                new BlockItem(ret.get(), prop));

        return ret;
    }
}

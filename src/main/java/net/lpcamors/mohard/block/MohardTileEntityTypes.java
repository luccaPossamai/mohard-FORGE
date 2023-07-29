package net.lpcamors.mohard.block;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.block.essence_altar.EssenceAltarTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MohardTileEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MohardMain.MODID);

    public static void initiate(IEventBus bus){
        TILE_ENTITY_TYPES.register(bus);
    }

    public static final RegistryObject<BlockEntityType<EssenceAltarTileEntity>> ENDER_ALTAR = TILE_ENTITY_TYPES.register("ender_altar", () ->
            BlockEntityType.Builder.of(EssenceAltarTileEntity::new, MohardBlocks.ESSENCE_ALTAR.get()).build(null)
    );
}

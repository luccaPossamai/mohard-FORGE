package net.lpcamors.mohard.data.providers;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.block.MohardBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class MohardBlockStateProvider extends BlockStateProvider {
    public MohardBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MohardMain.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(MohardBlocks.ESSENCE_ALTAR.get(), models().cubeBottomTop("essence_altar",
                mcLoc("mohard:block/essence_altar_side"),
                mcLoc("mohard:block/essence_altar_bottom"),
                mcLoc("mohard:block/essence_altar_top")));
    }
}

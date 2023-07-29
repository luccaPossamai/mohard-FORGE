package net.lpcamors.mohard.item.essences;

import com.mojang.blaze3d.platform.InputConstants;
import net.lpcamors.mohard.item.MohardAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EssenceItem  extends Item {


    private final @Nonnull EssenceData essenceData;
    private final int color1;
    private final int color2;
    private final EssencePredicate<LivingEntity> condition;
    private final boolean alternative;
    private boolean secret = false;
    private boolean hasOwnTexture = false;

    public EssenceItem(Properties properties, @NotNull EssenceData essenceData, int color1, int color2, EssencePredicate<LivingEntity> condition){
        super(properties);
        this.essenceData = essenceData;
        this.color1 = color1;
        this.color2 = color2;
        this.condition = condition;
        this.alternative = !condition.test(null);
    }

    public EssenceItem withOwnTexture(boolean bool){
        this.hasOwnTexture = bool;
        return this;
    }

    public EssenceItem secret(){
        this.secret = true;
        return this;
    }

    public boolean isSecret() {
        return secret;
    }

    public boolean hasOwnTexture() {
        return this.hasOwnTexture;
    }

    public boolean isAlternative() {
        return this.alternative;
    }

    @OnlyIn(Dist.CLIENT)
    public int getColor(int p_195983_1_) {
        return p_195983_1_ == 0 ? this.color1 : this.color2;
    }
    public EntityType<?> getType() {
        return this.getEssenceData().entityType();
    }

    public boolean testCondition(LivingEntity livingEntity){
        return this.condition.test(livingEntity);
    }

    public @NotNull EssenceData getEssenceData() {
        return this.getEssenceData(ItemStack.EMPTY);
    }

    public @NotNull EssenceData getEssenceData(ItemStack itemStack) {
        EssenceStats essenceStats = containsEssenceStatsTag(itemStack);
        return essenceStats != null ? new EssenceData(this.essenceData.type(), essenceStats) : this.essenceData;
    }

    public @Nullable EssenceStats containsEssenceStatsTag(ItemStack stack){
        EssenceStats essenceStats = null;
        if(stack != ItemStack.EMPTY && stack.hasTag() && stack.getTag().contains("EssenceStats")){
            ListTag listTag = stack.getTag().getList("EssenceStats", 10);
            try {
                List<Double> doubleList = new ArrayList<>();
                for(int i = 0; i < listTag.size(); i++){
                    doubleList.add(listTag.getCompound(i).getDouble("lvl"));
                }
                essenceStats = new EssenceStats(doubleList);
            } catch (Exception e){
                System.out.println("Unable to read the EssenceStats tag");
            }
        }
        return essenceStats;
    }

    public boolean isValid(){
        return !(EssenceData.Helper.isBoss(this) || this.isAlternative());
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack item, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(item, world, tooltip, flagIn);
        if(Minecraft.getInstance().player != null) {
            boolean shiftPressed = InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340);
            if (item.getItem() instanceof EssenceItem essence) {
                boolean unactive = EssenceData.Helper.isUnactive(item);
                if (!unactive) {
                    int upgradeLevel = EssenceData.Helper.getEssenceLevel(item);
                    double essenceLevel = this.getEssenceData(item).essenceStats().getEssenceLevel(upgradeLevel);
                    if(!shiftPressed) {
                        List<MutableComponent> lista = EssenceData.Helper.ESSENCE_DESCRIPTION;
                        EssenceData data = essence.getEssenceData(item);
                        Map<Attribute, Double> doubleList = data.essenceStats().getStats(upgradeLevel);
                        Map<Attribute, Double> rawDoubleList = data.essenceStats().getBaseStats();
                        List<Attribute> attributes = MohardAttributes.Helper.getAttributes();
                        tooltip.add(Component.translatable("essence.stats.description").withStyle(ChatFormatting.GRAY));
                        for (Attribute attribute : attributes) {
                            int index = attributes.indexOf(attribute);
                            double value = doubleList.get(attribute);
                            double rawValue = rawDoubleList.get(attribute);
                            ChatFormatting color = rawValue >= 0 ? ChatFormatting.BLUE : ChatFormatting.RED;
                            if (value != 0 || value != rawValue) {
                                String signal = value > 0 ? "+" : "";
                                MutableComponent component = Component.literal(" ").withStyle(color);
                                MutableComponent component1 = Component.literal(signal + value);
                                tooltip.add(component.append(component1).append(" ").append(lista.get(index)));
                            }
                        }
                    } else {
                        MutableComponent component = Component.translatable("essence.description.essence_level").withStyle(ChatFormatting.GRAY);
                        tooltip.add(component.append(Component.literal(essenceLevel+"").withStyle(ChatFormatting.GREEN)));
                        component = Component.translatable("essence.description.essence_upgrade_level").withStyle(ChatFormatting.GRAY);
                        tooltip.add(component.append(Component.literal("["+upgradeLevel+"/5]").withStyle(ChatFormatting.AQUA)));
                    }
                } else {
                    tooltip.add(Component.translatable("essence.description.unactive").withStyle(ChatFormatting.WHITE));
                }
            }
        }
    }
}

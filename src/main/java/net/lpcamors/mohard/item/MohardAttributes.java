package net.lpcamors.mohard.item;

import net.lpcamors.mohard.MohardMain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MohardAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MohardMain.MODID);

    protected static final List<Attribute> PERCENTAGE_ATTRIBUTES = new ArrayList<>();

    public static void initiate(IEventBus bus){
        ATTRIBUTES.register(bus);
    }

    public static final Attribute DAMAGE = registerAttribute("additional.damage", new RangedAttribute("attribute.name.additional.physical_damage", 1.0, -2000.0, 2000.0).setSyncable(true));
    public static final Attribute RAW_ARMOR = registerAttribute("additional.raw_armor", new RangedAttribute("attribute.name.additional.raw_armor", 0.0, -2048.0, 2048.0).setSyncable(true));
    public static final Attribute ARMOR_PENETRATION = registerPercentageAttribute("additional.armor_penetration", new RangedAttribute("attribute.name.additional.armor_penetration", 0.0, -100.0, 100.0).setSyncable(true));
    public static final Attribute AGILITY = registerPercentageAttribute("additional.agility", new RangedAttribute("attribute.name.additional.agility", 0.0, -100.0, 100.0).setSyncable(true));

    private static Attribute registerAttribute(String name, Attribute att){
        ATTRIBUTES.register(name, () -> att);
        return att;
    }
    private static Attribute registerPercentageAttribute(String name, Attribute att){
        ATTRIBUTES.register(name, () -> att);
        PERCENTAGE_ATTRIBUTES.add(att);
        return att;
    }

    public static class Helper {
        public static boolean isPercentageAttribute(@Nonnull Attribute att){
            return PERCENTAGE_ATTRIBUTES.contains(att);
        }

        public static List<Attribute> getAttributes(){
            return Arrays.asList(
                    Attributes.MAX_HEALTH,
                    MohardAttributes.RAW_ARMOR,
                    MohardAttributes.DAMAGE,
                    MohardAttributes.ARMOR_PENETRATION,
                    MohardAttributes.AGILITY
            );
        }

    }



}

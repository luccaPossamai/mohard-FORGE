package net.lpcamors.mohard.item.essences;

import net.lpcamors.mohard.item.MohardAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EssenceStats extends ArrayList<Double> {

    public static EssenceStats ZERO = new EssenceStats(0,0,0,0,0);

    public final int CAPACITY = 5;
    public EssenceStats(double vida, double contactArmor, double damage, double penetration, double agility){
        this();
        this.add(vida);
        this.add(contactArmor);
        this.add(damage);
        this.add(penetration);
        this.add(agility);
    }


    // <essence item id(mohard:bat_essence)>{EssenceStats:[{lvl:0D}, {lvl:0D},{lvl:0D},{lvl:0D},{lvl:0D}]}
    public EssenceStats(List<Double> list){
        this(list.get(0), list.get(1),list.get(2),list.get(3),list.get(4));
    }

    private EssenceStats(){
        super(5);
    }

    public Map<Attribute, Double> getBaseStats(){
        return this.getStats(0);
    }
    public Map<Attribute, Double> getStats(int upgrade) {
        List<Attribute> attributes = MohardAttributes.Helper.getAttributes();
        Map<Attribute, Double> leveledStats = new HashMap<>();
        for (int i = 0; i < attributes.size(); i++) {
            double stat = this.get(i);
            Attribute attribute = attributes.get(i);
            stat = EssenceData.Helper.calculateUpgrades(stat, upgrade);
            leveledStats.put(attribute, stat);
        }
        return leveledStats;
    }

    public double getEssenceLevel(int upgrade){
        Map<Attribute, Double> map = this.getStats(upgrade);
        return ((MohardAttributes.Helper.getAttributes().stream().map(attribute -> {
            if(!map.containsKey(attribute)) return 0D;
            if(MohardAttributes.Helper.isPercentageAttribute(attribute)) return map.get(attribute) * 1;
            return map.get(attribute);
        }).mapToDouble(Double::doubleValue).sum()));
    }
}

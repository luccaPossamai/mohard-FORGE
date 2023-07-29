package net.lpcamors.mohard.capability.level_mechanic;

import net.lpcamors.mohard.MohardMain;
import net.lpcamors.mohard.events.PlayerEvolutionEvents;
import net.lpcamors.mohard.item.essences.EssenceData;
import net.lpcamors.mohard.item.essences.EssenceItem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class LevelMechanicCapability {

    protected double level = 0;
    protected double standardDeviation = 0;

    //DON'T USE THIS
    protected double variance = 0;


    public LevelMechanicCapability(){

    }

    public void updateMeasures(ServerLevel level){
        double average = Helper.getServerLevel(level);
        double var = Helper.getServerLevelVariance(level, average);
        this.level = average;
        this.variance = var;
        this.standardDeviation = Math.sqrt(var);
        printUpdateMeasures();
    }

    private void printUpdateMeasures(){
        MohardMain.LOGGER.info("[UPDATING SERVER LEVEL] Current Level is "+this.level);
        MohardMain.LOGGER.info("[UPDATING SERVER LEVEL] The Variance and StandardDeviation are "+this.variance+" ;"+this.standardDeviation);
    }
    public double getAbsoluteLevel() {
        return this.level;
    }

    public double getRangeLevel(){
        double randVar = (new Random().nextFloat(2) - 1) * this.getStandardDeviation();
        return this.getAbsoluteLevel() + (int) Math.round(randVar);
    }

    public double getStandardDeviation() {
        return this.standardDeviation;
    }

    public static class Helper {
        public static double getServerLevel(ServerLevel level){
            return level.players().stream().mapToDouble(Helper::getPlayerLevel).sum() / level.players().size();
        }
        public static double getServerLevelVariance(Level level, double average){
            return level.players().stream().mapToDouble(Helper::getPlayerLevel).map(operand -> Math.pow(operand - average, 2)).sum() / level.players().size();
        }
        public static double getPlayerLevel(Player player){
            return PlayerEvolutionEvents.getItems(player).stream().filter(itemStack -> itemStack.getItem() instanceof EssenceItem).mapToDouble(itemStack -> {
                int upgradeLevel = EssenceData.Helper.getEssenceLevel(itemStack);
                return getEssenceLevel(((EssenceItem)itemStack.getItem()).getEssenceData(itemStack), upgradeLevel);
            }).sum();
        }

        public static double getEssenceBaseLevel(EssenceData essenceData){
            return getEssenceLevel(essenceData, 0);
        }
        public static double getEssenceLevel(EssenceData essenceData, int upgrade){
            return essenceData.essenceStats().getEssenceLevel(upgrade);
        }
    }

}

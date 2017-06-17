package de.alphahelix.skillranksaddon.instances;

import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.skillranksaddon.SkillRanksAddon;
import de.alphahelix.skillranksaddon.utils.TimeUtil;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.temporal.ChronoUnit;

public class SkillRank {
    private String prefix, command;
    private long minWins, minKills, minPoints, minGames;
    private Cooldown cooldown;
    private ItemStack icon;

    public SkillRank(String prefix, String command, long minWins, long minKills, long minPoints, long minGames, Cooldown cooldown, ItemStack icon) {
        this.prefix = prefix;
        this.command = command;
        this.minWins = minWins;
        this.minKills = minKills;
        this.minPoints = minPoints;
        this.minGames = minGames;
        this.cooldown = cooldown;
        this.icon = icon;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getCommand() {
        return command;
    }

    public long getMinWins() {
        return minWins;
    }

    public long getMinKills() {
        return minKills;
    }

    public long getMinPoints() {
        return minPoints;
    }

    public long getMinGames() {
        return minGames;
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public ItemStack getIcon() {
        return new ItemBuilder(icon).setName(getPrefix()).build();
    }

    public void reward(Player p) {
        Reward r = SkillRanksAddon.getReward(p);

        if (TimeUtil.isDateReached(r.getNextReward())) {
            if (getCommand().equalsIgnoreCase("-") || getCommand().isEmpty()) {
                p.sendMessage(UHC.getGameOptions().getChatPrefix() + SkillRanksAddon.getMessages().getNoReward());
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), getCommand().replace("[player]", p.getName()));

            PlayerStatistic ps = StatsUtil.getStatistics(p);

            ps.removeCustomStatistic(r);
            r.setNextReward(TimeUtil.increaseDate(getCooldown().getUnit(), getCooldown().getDura()));
            ps.addCustomStatistic(r);
        } else {
            p.sendMessage(UHC.getGameOptions().getChatPrefix() + SkillRanksAddon.getMessages().getCooldown(TimeUtil.getRemainingTime(r)));
        }
    }

    public static class Cooldown {
        private ChronoUnit unit;
        private int dura;

        public Cooldown(ChronoUnit unit, int dura) {
            this.unit = unit;
            this.dura = dura;
        }

        public ChronoUnit getUnit() {
            return unit;
        }

        public int getDura() {
            return dura;
        }
    }
}

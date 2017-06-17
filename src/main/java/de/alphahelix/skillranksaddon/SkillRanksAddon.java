package de.alphahelix.skillranksaddon;

import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.alphalibary.uuid.UUIDFetcher;
import de.alphahelix.skillranksaddon.files.MessageFile;
import de.alphahelix.skillranksaddon.files.RanksFile;
import de.alphahelix.skillranksaddon.instances.Messages;
import de.alphahelix.skillranksaddon.instances.RankOptions;
import de.alphahelix.skillranksaddon.instances.Reward;
import de.alphahelix.skillranksaddon.instances.SkillRank;
import de.alphahelix.skillranksaddon.inventories.SkillInventory;
import de.alphahelix.skillranksaddon.listeners.SkillListener;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.addons.core.Addon;
import de.alphahelix.uhcremastered.enums.GState;
import de.alphahelix.uhcremastered.instances.PlayerStatistic;
import de.alphahelix.uhcremastered.utils.StatsUtil;
import de.alphahelix.uhcremastered.utils.TabUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.WeakHashMap;

public class SkillRanksAddon extends Addon {

    private static SkillRanksAddon instance = new SkillRanksAddon();
    private static ArrayList<SkillRank> skillRanks = new ArrayList<>();
    private static WeakHashMap<UUID, SkillRank> playerSkills = new WeakHashMap<>();

    private static Messages messages;
    private static RankOptions rankOptions;

    public static SkillRanksAddon getInstance() {
        return instance;
    }

    public static ArrayList<SkillRank> getSkillRanks() {
        return skillRanks;
    }

    public static Reward getReward(OfflinePlayer p) {
        if (StatsUtil.getStatistics(p).getCustomStatistics(Reward.class).size() == 0) {
            StatsUtil.getStatistics(p).addCustomStatistic(new Reward(LocalDateTime.now()));
        }
        return StatsUtil.getStatistics(p).getCustomStatistics(Reward.class).get(0);
    }

    public static Messages getMessages() {
        return messages;
    }

    public static void setMessages(Messages messages) {
        SkillRanksAddon.messages = messages;
    }

    public static RankOptions getRankOptions() {
        return rankOptions;
    }

    public static void setRankOptions(RankOptions rankOptions) {
        SkillRanksAddon.rankOptions = rankOptions;
    }

    private static void sortTablist(Player p) {
        p.setDisplayName(playerSkills.get(UUIDFetcher.getUUID(p)).getPrefix() + p.getDisplayName());
        p.setPlayerListName(p.getDisplayName());
    }

    public static SkillRank getSkillRank(Player p) {
        if (playerSkills.containsKey(UUIDFetcher.getUUID(p)))
            return playerSkills.get(UUIDFetcher.getUUID(p));
        return null;
    }

    private void initRank(Player p) {
        PlayerStatistic stats = StatsUtil.getStatistics(p);

        long kills = stats.getKills();
        long wins = stats.getWins();
        long points = stats.getPoints();
        long games = stats.getGames();

        SkillRank playerRank = null;

        for (SkillRank sk : getSkillRanks()) {
            if (kills >= sk.getMinKills() && wins >= sk.getMinWins() && points >= sk.getMinPoints() && games >= sk.getMinGames())
                playerRank = sk;
        }

        playerSkills.put(UUIDFetcher.getUUID(p), playerRank);

        if (GState.is(GState.LOBBY))
            sortTablist(p);
    }

    @Override
    public void onEnable() {
        instance = this;

        new MessageFile();
        new RanksFile();
        new SkillListener();
        new SkillInventory();

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent e) {
                Util.runLater(5, false, () -> {
                    initRank(e.getPlayer());
                    TabUtil.sendTablist();

                    if (GState.is(GState.LOBBY))
                        e.getPlayer().getInventory().setItem(getRankOptions().getIcon().getSlot(), getRankOptions().getIcon().getItemStack());
                });
            }
        }, UHC.getInstance());
    }
}

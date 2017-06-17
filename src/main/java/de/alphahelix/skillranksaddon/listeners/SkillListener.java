package de.alphahelix.skillranksaddon.listeners;

import de.alphahelix.alphalibary.fakeapi.events.FakePlayerClickEvent;
import de.alphahelix.alphalibary.listener.SimpleListener;
import de.alphahelix.alphalibary.nms.REnumHand;
import de.alphahelix.skillranksaddon.SkillRanksAddon;
import de.alphahelix.uhcremastered.addons.csv.CrossSystemManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class SkillListener extends SimpleListener {
    @EventHandler
    public void onRewardClick(FakePlayerClickEvent e) {
        if (e.getHand() != REnumHand.MAIN_HAND) return;

        if (!ChatColor.stripColor(e.getFakePlayer().getName()).equals(CrossSystemManager.getVar("UHC:NPCAddon:RewardNPC")))
            return;

        if (SkillRanksAddon.getSkillRank(e.getPlayer()) != null)
            SkillRanksAddon.getSkillRank(e.getPlayer()).reward(e.getPlayer());
    }
}

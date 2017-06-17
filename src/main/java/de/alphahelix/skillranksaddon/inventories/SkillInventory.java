package de.alphahelix.skillranksaddon.inventories;

import de.alphahelix.alphalibary.inventorys.InventoryBuilder;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.alphalibary.utils.Util;
import de.alphahelix.skillranksaddon.SkillRanksAddon;
import de.alphahelix.uhcremastered.UHC;
import de.alphahelix.uhcremastered.enums.GState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SkillInventory {
    public SkillInventory() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onRewardClick(PlayerInteractEvent e) {
                if (!GState.is(GState.LOBBY)) return;
                if (e.getItem() == null) return;
                if (!Util.isSame(e.getItem(), SkillRanksAddon.getRankOptions().getIcon().getItemStack())) return;

                openInventory(e.getPlayer());
            }
        }, UHC.getInstance());
    }

    private void openInventory(Player p) {
        InventoryBuilder ib = new InventoryBuilder(p, SkillRanksAddon.getRankOptions().getMainGUI(), 27);

        for (int i = 0; i < 27; i++) {
            ib.addItem(ib.new SimpleItem(new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((short) 7).setName(" ").build(), i) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    e.setCancelled(true);
                }
            });
        }

        ib.removeItem(SkillRanksAddon.getRankOptions().getAllRanks().getSlot());
        ib.removeItem(SkillRanksAddon.getRankOptions().getClaimRewards().getSlot());

        ib.addItem(ib.new SimpleItem(SkillRanksAddon.getRankOptions().getAllRanks().getItemStack(), SkillRanksAddon.getRankOptions().getAllRanks().getSlot()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                p.closeInventory();
                AllRankInventory.openInventory(p);
            }
        });

        ib.addItem(ib.new SimpleItem(SkillRanksAddon.getRankOptions().getClaimRewards().getItemStack(), SkillRanksAddon.getRankOptions().getClaimRewards().getSlot()) {
            @Override
            public void onClick(InventoryClickEvent e) {
                p.closeInventory();
                if (SkillRanksAddon.getSkillRank(p) != null)
                    SkillRanksAddon.getSkillRank(p).reward(p);
            }
        });

        p.openInventory(ib.build());
    }
}

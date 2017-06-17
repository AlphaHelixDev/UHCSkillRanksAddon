package de.alphahelix.skillranksaddon.inventories;

import de.alphahelix.alphalibary.inventorys.InventoryBuilder;
import de.alphahelix.skillranksaddon.SkillRanksAddon;
import de.alphahelix.skillranksaddon.instances.SkillRank;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

class AllRankInventory {
    static void openInventory(Player p) {
        InventoryBuilder ib = new InventoryBuilder(p, SkillRanksAddon.getRankOptions().getAllSkillRanksGUIName(), ((SkillRanksAddon.getSkillRanks().size() / 9) + 1) * 9);

        for (int i = 0; i < SkillRanksAddon.getSkillRanks().size(); i++) {
            SkillRank sk = SkillRanksAddon.getSkillRanks().get(i);
            ib.addItem(ib.new SimpleItem(sk.getIcon(), i) {
                @Override
                public void onClick(InventoryClickEvent e) {
                    e.setCancelled(true);
                }
            });
        }

        p.openInventory(ib.build());
    }
}

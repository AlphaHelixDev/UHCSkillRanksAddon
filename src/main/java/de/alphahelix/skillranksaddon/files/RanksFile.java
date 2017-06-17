package de.alphahelix.skillranksaddon.files;

import de.alphahelix.alphalibary.file.SimpleFile;
import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.alphalibary.item.ItemBuilder;
import de.alphahelix.skillranksaddon.SkillRanksAddon;
import de.alphahelix.skillranksaddon.instances.RankOptions;
import de.alphahelix.skillranksaddon.instances.SkillRank;
import org.bukkit.Material;

import java.time.temporal.ChronoUnit;

public class RanksFile extends SimpleJSONFile {
    public RanksFile() {
        super(SkillRanksAddon.getInstance().getDataFolder().getAbsolutePath(), "ranks.json");
        initRanks();

        SkillRanksAddon.setRankOptions(getValue("Options", RankOptions.class));

        for (SkillRank sk : getValue("Skill ranks", SkillRank[].class)) {
            SkillRanksAddon.getSkillRanks().add(sk);
        }
    }

    private void initRanks() {
        if (jsonContains("Skill ranks")) return;

        setValue("Options", new RankOptions(
                "§3Skill ranks",
                "§3All ranks",
                new SimpleFile.InventoryItem(new ItemBuilder(Material.END_ROD).setName("§3Skill ranks").build(), 3),
                new SimpleFile.InventoryItem(new ItemBuilder(Material.BLAZE_POWDER).setGlow().setName("§3Claim Reward").build(), 12),
                new SimpleFile.InventoryItem(new ItemBuilder(Material.EMERALD).setName("§3All ranks").build(), 14)
        ));

        setValue("Skill ranks", new SkillRank[]{
                new SkillRank(
                        "§7(§8Rookie§7) ",
                        "",
                        0, 0, 0, 0,
                        new SkillRank.Cooldown(ChronoUnit.DAYS, 1),
                        new ItemBuilder(Material.COAL).setLore(" ", "§7Min. kills§8: 0", "etc").build()
                ),
                new SkillRank(
                        "§7(Semi-pro§7) ",
                        "",
                        0, 5, 0, 3,
                        new SkillRank.Cooldown(ChronoUnit.DAYS, 1),
                        new ItemBuilder(Material.IRON_INGOT).setLore(" ", "§7Min. kills§8: 5", "etc").build()
                ),
                new SkillRank(
                        "§7(§0Pro§7) ",
                        "",
                        1, 50, 0, 15,
                        new SkillRank.Cooldown(ChronoUnit.DAYS, 1),
                        new ItemBuilder(Material.GOLD_INGOT).setLore(" ", "§7Min. kills§8: 50", "etc").build()
                ),
                new SkillRank(
                        "§7(§6Veteran§7) ",
                        "",
                        5, 50, 0, 50,
                        new SkillRank.Cooldown(ChronoUnit.DAYS, 1),
                        new ItemBuilder(Material.DIAMOND).setLore(" ", "§7Min. kills§8: 50", "etc").build()
                ),
                new SkillRank(
                        "§7(§5Expert§7) ",
                        "",
                        10, 75, 500, 100,
                        new SkillRank.Cooldown(ChronoUnit.DAYS, 1),
                        new ItemBuilder(Material.EMERALD).setLore(" ", "§7Min. kills§8: 75", "etc").build()
                )
        });
    }
}

package de.alphahelix.skillranksaddon.instances;

import de.alphahelix.alphalibary.file.SimpleFile;

public class RankOptions {

    private String mainGUI, allSkillRanksGUIName;
    private SimpleFile.InventoryItem icon, claimRewards, allRanks;

    public RankOptions(String mainGUI, String allSkillRanksGUIName, SimpleFile.InventoryItem icon, SimpleFile.InventoryItem claimRewards, SimpleFile.InventoryItem allRanks) {
        this.mainGUI = mainGUI;
        this.allSkillRanksGUIName = allSkillRanksGUIName;
        this.icon = icon;
        this.claimRewards = claimRewards;
        this.allRanks = allRanks;
    }

    public SimpleFile.InventoryItem getIcon() {
        return icon;
    }

    public String getAllSkillRanksGUIName() {
        return allSkillRanksGUIName;
    }

    public String getMainGUI() {
        return mainGUI;
    }

    public SimpleFile.InventoryItem getClaimRewards() {
        return claimRewards;
    }

    public SimpleFile.InventoryItem getAllRanks() {
        return allRanks;
    }
}

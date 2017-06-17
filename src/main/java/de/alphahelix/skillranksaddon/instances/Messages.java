package de.alphahelix.skillranksaddon.instances;

public class Messages {

    private String noReward, cooldown;

    public Messages(String noReward, String cooldown) {
        this.noReward = noReward;
        this.cooldown = cooldown;
    }

    public String getNoReward() {
        return noReward;
    }

    public String getCooldown(String timeString) {
        return cooldown.replace("[time]", timeString);
    }
}

package de.alphahelix.skillranksaddon.instances;

import com.google.gson.JsonElement;
import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.uhcremastered.instances.CustomStatistic;

import java.time.LocalDateTime;

public class Reward extends CustomStatistic {

    private LocalDateTime nextReward;

    public Reward(LocalDateTime nextReward) {
        this.nextReward = nextReward;
    }

    public LocalDateTime getNextReward() {
        return nextReward;
    }

    public Reward setNextReward(LocalDateTime nextReward) {
        this.nextReward = nextReward;
        return this;
    }

    @Override
    public JsonElement toFileValue() {
        return SimpleJSONFile.gson.toJsonTree(this);
    }
}

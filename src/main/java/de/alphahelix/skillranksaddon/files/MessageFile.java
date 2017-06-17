package de.alphahelix.skillranksaddon.files;

import de.alphahelix.alphalibary.file.SimpleJSONFile;
import de.alphahelix.skillranksaddon.SkillRanksAddon;
import de.alphahelix.skillranksaddon.instances.Messages;

public class MessageFile extends SimpleJSONFile {
    public MessageFile() {
        super(SkillRanksAddon.getInstance().getDataFolder().getAbsolutePath(), "messages.json");
        init();

        SkillRanksAddon.setMessages(getValue("Messages", Messages.class));
    }

    private void init() {
        if(jsonContains("Messages")) return;

        setValue("Messages", new Messages(
                "§7Your current rank doesn't have any rewards.",
                "§7You have to wait §c[time] §7longer to get the §anext §7reward!"));
    }
}

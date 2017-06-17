package de.alphahelix.skillranksaddon.utils;

import de.alphahelix.skillranksaddon.instances.Reward;
import de.alphahelix.uhcremastered.UHC;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static String getRemainingTime(Reward reward) {

        LocalDateTime fromTemp = LocalDateTime.now();

        long months = fromTemp.until(reward.getNextReward(), ChronoUnit.MONTHS);
        fromTemp = fromTemp.plusMonths(months);

        long days = fromTemp.until(reward.getNextReward(), ChronoUnit.DAYS);
        fromTemp = fromTemp.plusDays(days);

        long hours = fromTemp.until(reward.getNextReward(), ChronoUnit.HOURS);
        fromTemp = fromTemp.plusHours(hours);

        long minutes = fromTemp.until(reward.getNextReward(), ChronoUnit.MINUTES);

        String date = String.format("§6%02d §7%s, §6%02d §7%s, §6%02d §7%s",

                days,
                UHC.getMessages().getUnits().getDay(days > 1),

                hours,
                UHC.getMessages().getUnits().getHour(hours > 1),

                minutes,
                UHC.getMessages().getUnits().getMinute(minutes > 1));

        date = date.replace("§", "&");

        if (days <= 0) {
            date = date.replace("00 &7" + UHC.getMessages().getUnits().getDay(false) + ", ", "");
        } else if (days < 10) {
            date = date.replaceFirst("0", "");
        }
        if (hours <= 0) {
            date = date.replace("00 &7" + UHC.getMessages().getUnits().getHour(false) + ", ", "");
        } else if (hours < 10) {
            date = date.replaceFirst("0", "");
        }
        if (minutes <= 0) {
            date = date.replace("00 &7" + UHC.getMessages().getUnits().getMinute(false) + " ", "");
        } else if (minutes < 10) {
            date = date.replaceFirst("0", "");
        }

        return date.replace("&", "§");
    }

    public static boolean isDateReached(LocalDateTime time) {
        return LocalDateTime.now().until(time, ChronoUnit.MILLIS) <= 0;
    }

    public static LocalDateTime increaseDate(ChronoUnit unit, int by) {
        switch (unit) {
            case WEEKS:
                return LocalDateTime.now().plusWeeks(by);
            case DAYS:
                return LocalDateTime.now().plusDays(by);
            case HOURS:
                return LocalDateTime.now().plusHours(by);
            case MINUTES:
                return LocalDateTime.now().plusMinutes(by);
            default:
                return LocalDateTime.now().plusMonths(by);
        }
    }
}

package net.rodrigocarvalho.lucy.utils;

import lombok.var;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");

    public static String format(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static long getTime(OffsetDateTime offset) {
        return offset.atZoneSameInstant(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static String formatTime(long millis) {
        var timeArray = getTimeArray(millis);
        if (timeArray[0] == 0) {
            return String.format("%s hora(s), %s minuto(s) e %s segundo(s)", timeArray[1], timeArray[2], timeArray[3]);
        } else {
            return String.format("%s dia(s), %s hora(s), %s minuto(s) e %s segundos", timeArray[0], timeArray[1], timeArray[2],timeArray[3]);
        }
    }

    public static String formatTime(int[] timeArray) {
        if (timeArray[0] == 0) {
            return String.format("%s hora(s), %s minuto(s) e %s segundo(s)", timeArray[1], timeArray[2], timeArray[3]);
        } else {
            return String.format("%s dia(s), %s hora(s), %s minuto(s) e %s segundos", timeArray[0], timeArray[1], timeArray[2],timeArray[3]);
        }
    }

    public static int[] getTimeArray(long millis) {
        var days  = (int) TimeUnit.MILLISECONDS.toDays(millis);
        var hours = (int) (TimeUnit.MILLISECONDS.toHours(millis)
                - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)));
        var minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
        var seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return new int[]{days, hours, minutes, seconds};
    }
}
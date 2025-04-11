package oort.cloud.openmarket.auth.utils;

import java.util.concurrent.TimeUnit;

public class TimeConvertUtil {

    public static long parseToMillis(String timeString) {
        return convert(timeString) * 1000L;
    }

    /*
        초단위 시간은 int형으로 반환
     */
    public static int parseToSeconds(String timeString) {
        long seconds = convert(timeString);
        if(seconds > Integer.MAX_VALUE)
            throw new ArithmeticException("long value exceed int range");
        return (int) seconds;
    }

    /*
        초단위로 계산된 값을 리턴한다.
     */
    private static long convert(String timeString) {
        if (timeString == null || timeString.isBlank())
            throw new IllegalArgumentException("timeString is Null or Empty");

        timeString = timeString.trim().toLowerCase();

        long value = Long.parseLong(timeString.substring(0, timeString.length() - 1));
        long multiplier;

        if (timeString.endsWith("d")) {
            multiplier = 24L * 60 * 60;
        } else if (timeString.endsWith("h")) {
            multiplier = 60L * 60;
        } else if (timeString.endsWith("m")) {
            multiplier = 60L;
        } else if (timeString.endsWith("s")) {
            multiplier = 1L;
        } else {
            throw new IllegalArgumentException("Is Not Supported TimeUnit, timeString data : " + timeString);
        }

        return value * multiplier;
    }

}



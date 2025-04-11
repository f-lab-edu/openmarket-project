package oort.cloud.openmarket.auth.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeConvertUtilTest {
    @Test
    @DisplayName("밀리초는 long 타입으로 초단위는 int 타입으로 변환한다.")
    void parseValidTimeStrings() {
        long millis = TimeConvertUtil.parseToMillis("30m");
        Assertions.assertEquals(30L * 60L * 1000L, millis);

        int seconds = TimeConvertUtil.parseToSeconds("30m");
        Assertions.assertEquals(30 * 60, seconds);
    }

}
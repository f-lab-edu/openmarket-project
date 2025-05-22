package oort.cloud.openmarket.utils;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class PasswordEncoderTest {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("평문을 암호화하고 암호화된 값과 비교하면 True를 리턴한다")
    void encodingTest(){
        //given
        String raw = "testpassword";

        //when
        String encodePassword = passwordEncoder.encode(raw);

        //then
        Assertions.assertThat(passwordEncoder.matches(raw, encodePassword)).isTrue();
    }

    @Test
    @DisplayName("암호화한 다른 raw 데이터를 비교하면 False를 리턴한다.")
    void encodingTestFail(){
        //given
        String raw = "testpassword";
        String diffRaw = "TESTPASSWORD";

        //when
        String encodePassword = passwordEncoder.encode(raw);

        //then
        Assertions.assertThat(passwordEncoder.matches(diffRaw, encodePassword)).isFalse();
    }
}

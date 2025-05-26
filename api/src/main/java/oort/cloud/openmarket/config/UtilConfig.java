package oort.cloud.openmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Clock;

@Configuration
public class UtilConfig {

    /*
        패스워드 암호화 처리를 위한 유틸 클래스 빈으로 등록
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}

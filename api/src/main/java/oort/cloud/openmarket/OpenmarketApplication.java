package oort.cloud.openmarket;

import oort.cloud.openmarket.auth.utils.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableJpaAuditing
@EnableRetry
public class OpenmarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenmarketApplication.class, args);
    }
}

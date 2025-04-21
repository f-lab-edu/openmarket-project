package oort.cloud.openmarket;

import oort.cloud.openmarket.auth.utils.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableJpaAuditing
public class OpenmarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenmarketApplication.class, args);
    }
}

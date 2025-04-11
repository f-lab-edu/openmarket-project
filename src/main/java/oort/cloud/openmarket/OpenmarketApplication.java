package oort.cloud.openmarket;

import oort.cloud.openmarket.auth.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class OpenmarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenmarketApplication.class, args);
    }
}

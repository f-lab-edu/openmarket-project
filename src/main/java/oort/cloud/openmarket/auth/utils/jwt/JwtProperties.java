package oort.cloud.openmarket.auth.utils.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.Key;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private static final int MAX_ACCESS_MINUTES = 30;
    private static final int MAX_REFRESH_DAYS = 60;
    private final int refreshTokenExpiredDay;
    private final int accessTokenExpiredMinutes;
    private final SignatureAlgorithm algorithm;
    private final Key secretKey;

    public JwtProperties(int refreshTokenExpiredDay, int accessTokenExpiredMinutes, SignatureAlgorithm algorithm, Key secretKey) {
        this.refreshTokenExpiredDay = Math.min(refreshTokenExpiredDay, MAX_REFRESH_DAYS);
        this.accessTokenExpiredMinutes = Math.min(accessTokenExpiredMinutes, MAX_ACCESS_MINUTES);
        this.algorithm = algorithm == null ? SignatureAlgorithm.HS256 : algorithm;
        this.secretKey = secretKey == null ? Keys.secretKeyFor(this.algorithm) : secretKey;
    }

    public int refreshTokenExpiredDay() { return refreshTokenExpiredDay; }
    public int accessTokenExpiredMinutes() { return accessTokenExpiredMinutes; }
    public SignatureAlgorithm getAlgorithm() { return algorithm; }
    public Key getSecretKey(){ return secretKey; }
}


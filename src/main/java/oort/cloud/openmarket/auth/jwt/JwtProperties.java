package oort.cloud.openmarket.auth.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import oort.cloud.openmarket.auth.utils.TimeConvertUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.Key;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private final String refreshTokenExpiredDay;
    private final String accessTokenExpiredMinutes;
    private final SignatureAlgorithm algorithm;
    private final Key secretKey;

    public JwtProperties(String refreshTokenExpiredDay, String accessTokenExpiredMinutes, SignatureAlgorithm algorithm, Key secretKey) {
        this.refreshTokenExpiredDay = refreshTokenExpiredDay;
        this.accessTokenExpiredMinutes = accessTokenExpiredMinutes;
        this.algorithm = algorithm == null ? SignatureAlgorithm.HS256 : algorithm;
        this.secretKey = secretKey == null ? Keys.secretKeyFor(this.algorithm) : secretKey;
    }

    public String getRefreshTokenExpiredTime() { return refreshTokenExpiredDay; }
    public String getAccessTokenExpiredTime() { return accessTokenExpiredMinutes; }
    public SignatureAlgorithm getAlgorithm() { return algorithm; }
    public Key getSecretKey(){ return secretKey; }
}


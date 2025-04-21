package oort.cloud.openmarket.auth.utils.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.auth.data.RefreshTokenPayload;
import oort.cloud.openmarket.exception.auth.ExpiredTokenException;
import oort.cloud.openmarket.exception.auth.InvalidTokenException;
import oort.cloud.openmarket.exception.business.InvalidUserStateException;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.enums.UserRole;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtManager {
    private final String CLAIM_USER_ROLE_VALUE = "userRole";
    private final Key secretKey;
    private final Clock clock;


    public JwtManager(JwtProperties properties, Clock clock) {
        this.secretKey = properties.getSecretKey();
        this.clock = clock;
    }

    public String getRefreshToken(UserDto user, Duration duration) {
        validateUser(user, false);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(this.secretKey)
                .setSubject(String.valueOf(user.getUserId()))
                .setExpiration(getExpireDate(duration))
                .compact();
    }

    private void validateUser(UserDto user, boolean checkRole) {
        if (user.getUserId() == null) {
            throw new InvalidUserStateException();
        }
        if (checkRole && user.getUserRole() == null) {
            throw new InvalidUserStateException();
        }
    }

    public String getAccessToken(UserDto user, Duration duration) {
        validateUser(user, true);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(this.secretKey)
                .setSubject(String.valueOf(user.getUserId()))
                .claim(CLAIM_USER_ROLE_VALUE, user.getUserRole())
                .setExpiration(getExpireDate(duration))
                .compact();
    }

    /*
         access_token에서 사용자 정보 추출
         유효하지 않거나 만료된 토큰일 경우 예외 던짐
     */
    public AccessTokenPayload getAccessTokenPayload(String accessToken) {
        try {
            Claims claims = getClaims(accessToken);
            Long userId = Long.parseLong(claims.getSubject());
            String roleString = claims.get(CLAIM_USER_ROLE_VALUE, String.class);
            UserRole userRole = UserRole.valueOf(roleString);
            return AccessTokenPayload.of(userId, userRole);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    public RefreshTokenPayload getRefreshTokenPayload(String refreshToken) {
        try {
            Claims claims = getClaims(refreshToken);
            Long userId = Long.parseLong(claims.getSubject());
            return RefreshTokenPayload.of(userId);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private Date getExpireDate(Duration duration) {
        Instant expireAt = clock.instant().plus(duration);
        return Date.from(expireAt);
    }

}

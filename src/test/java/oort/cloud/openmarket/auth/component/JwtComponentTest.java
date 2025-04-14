package oort.cloud.openmarket.auth.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import oort.cloud.openmarket.auth.controller.response.AuthTokenResponse;
import oort.cloud.openmarket.auth.jwt.JwtComponent;
import oort.cloud.openmarket.auth.jwt.JwtProperties;
import oort.cloud.openmarket.exception.AuthServiceException;
import oort.cloud.openmarket.exception.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.enums.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtComponentTest {

    @Mock
    private JwtProperties jwtProperties;
    private JwtComponent jwtComponent;
    private Clock clock;
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 테스트용 키

    @BeforeEach
    void setup() {
        when(jwtProperties.getSecretKey()).thenReturn(secretKey);
        when(jwtProperties.getAccessTokenExpiredTime()).thenReturn("30m"); // 30분
        when(jwtProperties.getRefreshTokenExpiredTime()).thenReturn("7d"); // 7일
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        jwtComponent = new JwtComponent(jwtProperties, clock, new ObjectMapper());
    }

    @Test
    @DisplayName("JWT Access 토큰 생성이 성공한다.")
    void success_create_jwt(){
        //given
        UserDto userDto = new UserDto(1L, "test", "test", "1231412", UserRole.BUYER, UserStatus.ACTIVE);

        //when
        AuthTokenResponse authToken = jwtComponent.createAuthToken(userDto);
        String accessToken = authToken.getAccessToken();
        Jws<Claims> claimsJws = jwtComponent.validateToken(accessToken);

        //then
        String sub = claimsJws.getBody().getSubject();
        assertEquals(sub, String.valueOf(userDto.getUserId()));
    }

    @Test
    @DisplayName("JWT 토큰 검증이 실패할 경우 AuthServiceException 예외를 던진다.")
    void fail_validate_token(){
        //given
        UserDto userDto = new UserDto(1L,
                                    "test",
                                    "test",
                                    "1231412",
                                    UserRole.BUYER,
                                    UserStatus.ACTIVE);

        //when
        AuthTokenResponse authToken = jwtComponent.createAuthToken(userDto);
        String accessToken = authToken.getAccessToken();
        String diffToken = accessToken.substring(0, 3) + "X" + accessToken.substring(4);

        //then
        String message =
                Assertions.assertThrows(AuthServiceException.class, () -> jwtComponent.validateToken(diffToken)).getMessage();
        assertEquals(message, ErrorType.INVALID_TOKEN.getMessage());
    }

    @Test
    @DisplayName("Access Token의 만료 시간이 정확히 30분 뒤인지 확인")
    void accessToken_expiration_is_valid() {
        UserDto user = getUserDto();
        String accessToken = jwtComponent.createAuthToken(user).getAccessToken();

        Claims claims = extractClaims(accessToken);
        Date expiration = claims.getExpiration();

        Instant expectedExpiration = this.clock.instant()
                .plus(30, ChronoUnit.MINUTES)
                .truncatedTo(ChronoUnit.SECONDS);

        assertEquals(expectedExpiration, expiration.toInstant());
    }


    @Test
    @DisplayName("Refresh Token의 만료시간이 정확히 7일 이후")
    void refreshToken_has_longer_expiration() {
        UserDto user = getUserDto();
        AuthTokenResponse token = jwtComponent.createAuthToken(user);

        Claims refreshClaims = extractClaims(token.getRefreshToken());
        Date expiration = refreshClaims.getExpiration();

        Instant expectedExpiration = this.clock.instant()
                .plus(7, ChronoUnit.DAYS)
                .truncatedTo(ChronoUnit.SECONDS);

        assertEquals(expectedExpiration, expiration.toInstant());
    }

    private UserDto getUserDto() {
        UserDto user = new UserDto(1L,
                "test@example.com", "test", "1234", UserRole.BUYER, UserStatus.ACTIVE);
        return user;
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
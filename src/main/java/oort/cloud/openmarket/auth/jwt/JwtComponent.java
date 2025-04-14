package oort.cloud.openmarket.auth.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import oort.cloud.openmarket.auth.controller.response.AuthTokenResponse;
import oort.cloud.openmarket.auth.utils.TimeConvertUtil;
import oort.cloud.openmarket.exception.AuthServiceException;
import oort.cloud.openmarket.exception.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.time.Clock;
import java.util.Date;


@Component
@Slf4j
public class JwtComponent {

    private final JwtProperties properties;
    private final Key secreteKey;
    private final Clock clock;
    private final ObjectMapper objectMapper;


    public JwtComponent(JwtProperties properties, Clock clock, ObjectMapper objectMapper) {
        this.properties = properties;
        this.secreteKey = properties.getSecretKey();
        this.clock = clock;
        this.objectMapper = objectMapper;
    }

    private String getRefreshToken(UserDto user){
        return Jwts.builder()
                .signWith(this.secreteKey)
                .setSubject(String.valueOf(user.getUserId()))
                .claim("user", user)
                .setExpiration(getExpireDate(properties.getRefreshTokenExpiredTime()))
                .compact();
    }

    public String getAccessToken(UserDto user){
        return Jwts.builder()
                .signWith(this.secreteKey)
                .setSubject(String.valueOf(user.getUserId()))
                .claim("email", user.getEmail())
                .claim("userName", user.getUserName())
                .claim("userRole", user.getUserRole())
                .setExpiration(getExpireDate(properties.getAccessTokenExpiredTime()))
                .compact();
    }


    public AuthTokenResponse createAuthToken(UserDto userDto){
        return AuthTokenResponse.of(
                getAccessToken(userDto),
                getRefreshToken(userDto)
        );
    }

    /*
         Token 검증 로직
         유효하지 않은 토큰, 만료된 토큰일 경우 AuthServiceException 발생
         토큰 검증을 통과하면 Jws<Claims> 타입 반환
     */
    public Jws<Claims> validateToken(String token) throws AuthServiceException{
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(this.secreteKey)
                .build();
        try{
            return parser.parseClaimsJws(token);
        }catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            log.error("[JWT] Class : {} ExceptionClass : {} Message : {}", this.getClass().getSimpleName(), e.getClass(), e.getMessage());
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }catch (ExpiredJwtException e){
            throw new AuthServiceException(ErrorType.EXPIRED_TOKEN);
        }
    }

    public UserDto parseUserInfo(String accessToken){
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(this.secreteKey)
                            .build()
                            .parseClaimsJws(accessToken)
                            .getBody();
        return objectMapper.convertValue(claims, UserDto.class);
    }

    private Date getExpireDate(String expireTime){
        return new Date(clock.millis() + TimeConvertUtil.parseToMillis(expireTime));
    }
}

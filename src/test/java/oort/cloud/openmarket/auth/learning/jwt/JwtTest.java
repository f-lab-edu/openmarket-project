package oort.cloud.openmarket.auth.learning.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.buf.Utf8Encoder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    @DisplayName("JWT 생성시 비밀키 생성 테스트")
    void create_secret_key(){
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String strSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("key : " + strSecretKey);

        byte[] decoded = Base64.getDecoder().decode(strSecretKey);
        SecretKeySpec secretKeySpec = new SecretKeySpec(decoded, 0, decoded.length, SignatureAlgorithm.HS256.getValue());
        System.out.println("key : " + Base64.getEncoder().encodeToString(secretKeySpec.getEncoded()));

        Assertions.assertThat(secretKeySpec.getEncoded()).isEqualTo(secretKey.getEncoded());
    }


    @Test
    @DisplayName("JWT 생성 및 검증 테스트")
    void createJWT() throws JsonProcessingException {
        // 비밀키 생성
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        //생성한 비밀키로 JWT 생성
        String compact = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("test")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        //JWT 파서 생성 및 비밀키 세팅
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        //JWT 서명 확인
        Assertions.assertThat(parser.isSigned(compact)).isTrue();

        //JWT payload 값 비교
        Claims body = parser.parseClaimsJws(compact).getBody();
        Assertions.assertThat(body.getSubject()).isEqualTo("test");

    }

    @Test
    @DisplayName("전달 받은 JWT가 변경되면 예외를 던진다.")
    void jwt_valid_fail() throws JsonProcessingException {
        // 비밀키 생성
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // JWT 생성
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("valid")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // 3. 토큰 위조
        String diffToken = token.substring(0, 3) + "X" + token.substring(4);

        // 4. 파서 생성
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        // 서명 검증은 실패
        Assertions.assertThatThrownBy(() -> parser.parseClaimsJws(diffToken))
                .isInstanceOf(JwtException.class);

    }
}

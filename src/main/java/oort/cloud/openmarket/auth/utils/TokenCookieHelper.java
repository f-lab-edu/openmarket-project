package oort.cloud.openmarket.auth.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import oort.cloud.openmarket.auth.utils.jwt.JwtProperties;
import oort.cloud.openmarket.exception.auth.AuthenticationException;
import oort.cloud.openmarket.exception.auth.InvalidTokenException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;

@Component
public class TokenCookieHelper {
    private static final String REFRESH_TOKEN_COOKIE_NAME = "OMID";
    private final JwtProperties jwtProperties;

    public TokenCookieHelper(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String tokenValue) {
        long maxAge = Duration.ofMinutes(jwtProperties.refreshTokenExpiredDay()).getSeconds();
        Cookie cookie = buildCookie(tokenValue, (int) maxAge);
        response.addCookie(cookie);
    }

    public void removeRefreshTokenCookie(HttpServletResponse response) {
        Cookie cookie = buildCookie(null, 0);
        response.addCookie(cookie);
    }

    public String extractRefreshTokenFromCookies(Cookie[] cookies){
        if(cookies == null || cookies.length == 0) throw new AuthenticationException(ErrorType.UNAUTHORIZED_ACCESS);

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME))
                .findAny()
                .orElseThrow(() -> new InvalidTokenException())
                .getValue();
    }

    private Cookie buildCookie(String value, int maxAge) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}

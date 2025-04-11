package oort.cloud.openmarket.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import oort.cloud.openmarket.auth.controller.request.LoginRequest;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.auth.controller.response.AuthTokenResponse;
import oort.cloud.openmarket.auth.jwt.JwtComponent;
import oort.cloud.openmarket.auth.jwt.JwtProperties;
import oort.cloud.openmarket.auth.service.AuthService;
import oort.cloud.openmarket.auth.utils.TimeConvertUtil;
import oort.cloud.openmarket.exception.AuthServiceException;
import oort.cloud.openmarket.exception.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class AuthController {
    private final AuthService authService;
    private final JwtProperties jwtProperties;

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh-token";

    public AuthController(AuthService authService, JwtProperties jwtProperties) {
        this.authService = authService;
        this.jwtProperties = jwtProperties;
    }

    @PostMapping("/v1/auth/sign-up")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignUpRequest signUprequest){
        authService.signUp(signUprequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/v1/auth/login")
    public ResponseEntity<AuthTokenResponse> login(@RequestBody @Valid LoginRequest loginRequest
    , HttpServletResponse response){
        AuthTokenResponse token = authService.login(loginRequest);
        setTokenCookie(token, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

    @PostMapping("/v1/auth/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response){
        removeTokenCookie(response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/v1/auth/refresh-token")
    public ResponseEntity<AuthTokenResponse> refreshToken(HttpServletRequest request){
        Cookie tokenCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME))
                .findAny()
                .orElseThrow(() -> new AuthServiceException(ErrorType.INVALID_TOKEN));
        String accessToken = authService.refreshAccessToken(tokenCookie.getAttribute(REFRESH_TOKEN_COOKIE_NAME));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthTokenResponse.ofAccessTokenOnly(accessToken));
    }

    private void removeTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private void setTokenCookie(AuthTokenResponse token, HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, token.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(
                TimeConvertUtil.parseToSeconds(jwtProperties.getRefreshTokenExpiredTime())
        );
        response.addCookie(cookie);
    }
}

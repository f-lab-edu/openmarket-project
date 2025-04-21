package oort.cloud.openmarket.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import oort.cloud.openmarket.auth.controller.request.LoginRequest;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.auth.data.AuthToken;
import oort.cloud.openmarket.auth.service.AuthService;
import oort.cloud.openmarket.auth.service.TokenService;
import oort.cloud.openmarket.auth.utils.TokenCookieHelper;
import oort.cloud.openmarket.user.data.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final TokenCookieHelper tokenCookieHelper;
    public AuthController(AuthService authService, TokenService tokenService, TokenCookieHelper tokenCookieHelper) {
        this.authService = authService;
        this.tokenService = tokenService;
        this.tokenCookieHelper = tokenCookieHelper;
    }

    @PostMapping("/v1/auth/sign-up")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignUpRequest signUprequest){
        authService.signUp(signUprequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/v1/auth/login")
    public ResponseEntity<AuthToken> login(@RequestBody @Valid LoginRequest loginRequest){
        UserDto user = authService.login(loginRequest);
        AuthToken authToken = tokenService.createAuthToken(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authToken);
    }

    @PostMapping("/v1/auth/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request,
                                                   HttpServletResponse response){
        String refreshToken = tokenCookieHelper.extractRefreshTokenFromCookies(request.getCookies());
        tokenCookieHelper.removeRefreshTokenCookie(response);
        tokenService.logout(refreshToken);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/v1/auth/refresh-token")
    public ResponseEntity<AuthToken> refreshToken(HttpServletRequest request){
        String refreshToken = tokenCookieHelper.extractRefreshTokenFromCookies(request.getCookies());
        String accessToken = tokenService.refreshAccessToken(refreshToken);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(AuthToken.of(accessToken, refreshToken));
    }


}

package oort.cloud.openmarket.auth.service;

import oort.cloud.openmarket.auth.data.AuthToken;
import oort.cloud.openmarket.auth.data.RefreshTokenPayload;
import oort.cloud.openmarket.auth.entity.RefreshToken;
import oort.cloud.openmarket.auth.repository.RefreshTokenRepository;
import oort.cloud.openmarket.auth.utils.jwt.JwtManager;
import oort.cloud.openmarket.auth.utils.jwt.JwtProperties;
import oort.cloud.openmarket.exception.auth.ExpiredTokenException;
import oort.cloud.openmarket.exception.auth.InvalidTokenException;
import oort.cloud.openmarket.exception.auth.NotFoundRefreshTokenException;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.enums.UserStatus;
import oort.cloud.openmarket.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class TokenServiceTest {
    private RefreshTokenRepository refreshTokenRepository;
    private JwtProperties jwtProperties;
    private JwtManager jwtManager;
    private UserService userService;

    private TokenService tokenService;

    @BeforeEach
    void setup() {
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        jwtProperties = mock(JwtProperties.class);
        jwtManager = mock(JwtManager.class);
        userService = mock(UserService.class);

        tokenService = new TokenService(refreshTokenRepository, jwtProperties, jwtManager, userService);
    }

    @Test
    @DisplayName("AccessToken과 RefreshToken을 포함한 AuthToken을 반환하며 RefreshToken은 토큰 테이블 저장된다.")
    void create_authToken() {
        UserDto user = UserDto.of(1L, "email@test.com", "tester", "01012345678", UserRole.BUYER, UserStatus.ACTIVE);

        when(jwtProperties.accessTokenExpiredMinutes()).thenReturn(30);
        when(jwtProperties.refreshTokenExpiredDay()).thenReturn(7);

        when(jwtManager.getAccessToken(eq(user), any())).thenReturn("access-token");
        when(jwtManager.getRefreshToken(eq(user), any())).thenReturn("refresh-token");

        AuthToken token = tokenService.createAuthToken(user);

        verify(refreshTokenRepository).save(any());
        assertThat(token.getAccessToken()).isEqualTo("access-token");
        assertThat(token.getRefreshToken()).isEqualTo("refresh-token");
    }

    @Test
    @DisplayName("DB에 저장되어 있는 Refresh Token과 전달 받은 Refresh Token을 검증 후 Access Token을 생성하여 반환한다.")
    void refresh_accessToken() {
        Long tokenId = 1L;
        String tokenValue = "valid-refresh-token";
        Long userId = 99L;

        RefreshToken token = RefreshToken.createRefreshToken(userId, tokenValue, LocalDateTime.now().plusDays(1));
        RefreshTokenPayload payload = RefreshTokenPayload.of(userId);

        when(jwtManager.getRefreshTokenPayload(tokenValue)).thenReturn(payload);
        when(refreshTokenRepository.findByUserId(anyLong())).thenReturn(Optional.of(token));
        when(userService.findUserById(userId)).thenReturn(UserDto.of(userId, "email", "name", "010", UserRole.BUYER, UserStatus.ACTIVE));
        when(jwtManager.getAccessToken(any(), any())).thenReturn("new-access-token");

        String result = tokenService.refreshAccessToken(tokenValue);
        assertThat(result).isEqualTo("new-access-token");
    }

    @Test
    @DisplayName("Refresh Token 정보가 일치하지 않으면 InvalidTokenException이 발생한다.")
    void refresh_access_token() {
        String inputToken = "token-input";
        String savedToken = "different-token";

        RefreshToken token = RefreshToken.createRefreshToken(1L, savedToken, LocalDateTime.now().plusDays(1));
        RefreshTokenPayload payload = RefreshTokenPayload.of(token.getUserId());
        when(jwtManager.getRefreshTokenPayload(inputToken)).thenReturn(payload);
        when(refreshTokenRepository.findByUserId(anyLong())).thenReturn(Optional.of(token));

        assertThatThrownBy(() -> tokenService.refreshAccessToken(inputToken))
                .isInstanceOf(InvalidTokenException.class);
    }

    @Test
    @DisplayName("refresh toke의 만료 되었다면 ExpiredTokenException 예외 발생")
    void refreshAccessToken_토큰만료_예외() {
        String tokenValue = "expired-token";

        RefreshToken token = RefreshToken.createRefreshToken(1L, tokenValue, LocalDateTime.now().minusMinutes(1));
        RefreshTokenPayload payload = RefreshTokenPayload.of(token.getUserId());
        when(jwtManager.getRefreshTokenPayload(tokenValue)).thenReturn(payload);
        when(refreshTokenRepository.findByUserId(1L)).thenReturn(Optional.of(token));

        assertThatThrownBy(() -> tokenService.refreshAccessToken(tokenValue))
                .isInstanceOf(ExpiredTokenException.class);
    }

    @Test
    @DisplayName("클라이언트가 전달한 refresh token이 DB에 없을 경우 NotFoundRefreshTokenException 예외 발생")
    void refresh_accessToken_not_exist_DB() {
        RefreshTokenPayload payload = RefreshTokenPayload.of(99L);
        when(jwtManager.getRefreshTokenPayload("some-token")).thenReturn(payload);
        when(refreshTokenRepository.findByUserId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tokenService.refreshAccessToken("some-token"))
                .isInstanceOf(NotFoundRefreshTokenException.class);
    }

    @Test
    @DisplayName("기존 토근이 없을 경우 토큰을 DB에 저장한다")
    void create_authToken_insert(){
        //given
        UserDto user = UserDto.of(1L, "test@email.com", "test", "010", UserRole.BUYER, UserStatus.ACTIVE);
        when(jwtProperties.accessTokenExpiredMinutes()).thenReturn(30);
        when(jwtProperties.refreshTokenExpiredDay()).thenReturn(7);
        when(jwtManager.getAccessToken(eq(user), any())).thenReturn("access_token");
        when(jwtManager.getRefreshToken(eq(user), any())).thenReturn("refresh_token");
        when(refreshTokenRepository.findByUserId(user.getUserId())).thenReturn(Optional.empty());

        //when
        AuthToken authToken = tokenService.createAuthToken(user);

        //then
        assertThat(authToken.getAccessToken()).isEqualTo("access_token");
        assertThat(authToken.getRefreshToken()).isEqualTo("refresh_token");
    }

    @Test
    @DisplayName("기존 토근이 있는 경우 토큰을 DB에 갱신한다")
    void create_authToken_update(){
        //given
        UserDto user = UserDto.of(1L, "test@email.com", "test", "010", UserRole.BUYER, UserStatus.ACTIVE);
        RefreshToken oldToken = RefreshToken.createRefreshToken(user.getUserId(), "old-token", LocalDateTime.now().plusDays(7));

        when(jwtProperties.accessTokenExpiredMinutes()).thenReturn(30);
        when(jwtProperties.refreshTokenExpiredDay()).thenReturn(7);
        when(jwtManager.getAccessToken(eq(user), any())).thenReturn("access_token");
        when(jwtManager.getRefreshToken(eq(user), any())).thenReturn("new_refresh_token");
        when(refreshTokenRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(oldToken));

        //when
        AuthToken authToken = tokenService.createAuthToken(user);

        //then
        assertThat(oldToken.getToken()).isEqualTo("new_refresh_token");
        assertThat(authToken.getRefreshToken()).isEqualTo("new_refresh_token");
        verify(refreshTokenRepository).save(oldToken);
    }


}
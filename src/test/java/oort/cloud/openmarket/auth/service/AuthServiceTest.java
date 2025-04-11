package oort.cloud.openmarket.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import oort.cloud.openmarket.auth.controller.request.LoginRequest;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.auth.controller.response.AuthTokenResponse;
import oort.cloud.openmarket.auth.jwt.JwtComponent;
import oort.cloud.openmarket.auth.request.LoginRequestTest;
import oort.cloud.openmarket.auth.request.SignUpRequestTest;
import oort.cloud.openmarket.exception.AuthServiceException;
import oort.cloud.openmarket.exception.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private JwtComponent jwtComponent;

    @BeforeEach
    void init(){
        userRepository = mock(UserRepository.class);
        encoder = mock(BCryptPasswordEncoder.class);
        jwtComponent = mock(JwtComponent.class);
        authService = new AuthService(userRepository, encoder, jwtComponent);
    }

    @Test
    @DisplayName("회원가입이 성공한다.")
    void success_sign_up() {
        //given
        SignUpRequest request = getSignUpRequest();

        //when
        when(userRepository.countByEmail(request.getEmail())).thenReturn(0);
        when(userRepository.save(any(Users.class)))
                .thenReturn(Users.createUser(
                        "test@email.com",
                        "test123",
                        "test",
                        "12312341234",
                        UserRole.BUYER
                ));
        UserDto userDto = authService.signUp(request);

        //then
        verify(userRepository, times(1)).save(any(Users.class));
        Assertions.assertThat(userDto.getEmail()).isEqualTo(request.getEmail());
    }


    @Test
    @DisplayName("이메일이 중복되면 예외가 발생한다")
    void duplicate_email() {
        // given
        SignUpRequestTest request = getSignUpRequest();

        when(userRepository.countByEmail(request.getEmail())).thenReturn(1);

        // when & then
        assertThatThrownBy(() -> authService.signUp(request))
                .isInstanceOf(AuthServiceException.class)
                .hasMessageContaining(ErrorType.DUPLICATE_EMAIL.getMessage());

        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("정상 로그인 시 토큰을 반환한다")
    void loginSuccess() {
        //given
        LoginRequest request = getLoginRequest();
        AuthTokenResponse mockToken = AuthTokenResponse.of("accessToken", "refreshToken");
        Users user = Users.createUser("test@example.com", "1234", "test", "2134", UserRole.BUYER);

        //when
        when(userRepository.findByEmail(request.getEmail())).thenReturn(user);
        when(encoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtComponent.createAuthToken(any(UserDto.class))).thenReturn(mockToken);

        AuthTokenResponse response = authService.login(request);

        //then
        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
    }

    private LoginRequest getLoginRequest() {
        return new LoginRequestTest("test@email.com", "1234");
    }

    private SignUpRequestTest getSignUpRequest() {
        return new SignUpRequestTest(
                "test@email.com",
                "1234",
                "test",
                "12312341234",
                UserRole.BUYER);
    }


}
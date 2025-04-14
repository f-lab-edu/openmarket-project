package oort.cloud.openmarket.auth.service;

import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.data.SignUpRequestTest;
import oort.cloud.openmarket.exception.BusinessException;
import oort.cloud.openmarket.exception.auth.DuplicateEmailException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.enums.UserStatus;
import oort.cloud.openmarket.user.repository.UserRepository;
import oort.cloud.openmarket.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private UserService userService;

    @BeforeEach
    void init(){
        userRepository = mock(UserRepository.class);
        encoder = mock(BCryptPasswordEncoder.class);
        userService = mock(UserService.class);
        authService = new AuthService(userService);
    }

    @Test
    @DisplayName("회원가입이 성공한다.")
    void success_sign_up() {
        // given
        SignUpRequest request = mock(SignUpRequest.class);

        UserDto expectedDto = UserDto.of("test@email.com",
                "tester", "01012345678", UserRole.BUYER, UserStatus.ACTIVE);

        when(userService.save(request)).thenReturn(expectedDto);

        // when
        UserDto result = authService.signUp(request);

        // then
        assertEquals(expectedDto, result);
        verify(userService).save(request);
    }


    @Test
    @DisplayName("이메일이 중복되면 예외가 발생한다")
    void duplicate_email() {
        SignUpRequest request = new SignUpRequestTest(
                "test@email.com", "1234", "test", "12312341234", UserRole.BUYER);

        when(userService.save(request)).thenThrow(new DuplicateEmailException(ErrorType.DUPLICATE_EMAIL));

        // when & then
        assertThatThrownBy(() -> authService.signUp(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorType.DUPLICATE_EMAIL.getMessage());

    }

}
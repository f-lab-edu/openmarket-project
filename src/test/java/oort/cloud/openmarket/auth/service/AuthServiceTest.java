package oort.cloud.openmarket.auth.service;

import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void init(){
        userRepository = mock(UserRepository.class);
        encoder = mock(BCryptPasswordEncoder.class);
        authService = new AuthService(userRepository, encoder);
    }

    @Test
    @DisplayName("회원가입이 성공한다.")
    void success_sign_up() {
        //given
        SignUpRequest request = getRequest();

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
        SignUpRequest request = getRequest();
        when(userRepository.countByEmail(request.getEmail())).thenReturn(1);

        // when & then
        assertThatThrownBy(() -> authService.signUp(request))
                .isInstanceOf(AuthServiceException.class)
                .hasMessageContaining(ErrorType.DUPLICATE_EMAIL.getMessage());

        verify(userRepository, never()).save(any(Users.class));
    }



    private SignUpRequest getRequest() {
        return new SignUpRequest(
                "test@email.com",
                "test123",
                "test",
                "12312341234",
                UserRole.BUYER
        );
    }

}
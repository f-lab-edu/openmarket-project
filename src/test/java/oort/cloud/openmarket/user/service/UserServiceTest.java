package oort.cloud.openmarket.user.service;

import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.data.SignUpRequestTest;
import oort.cloud.openmarket.exception.auth.DuplicateEmailException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("이메일이 중복되지 않으면 회원가입에 성공하고 UserDto가 반환된다")
    void success_save() {
        // given
        SignUpRequest request = new SignUpRequestTest(
                "test@email.com", "1234", "tester", "01012345678", UserRole.BUYER
        );

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded_pw");

        Users savedUser = Users.createUser(
                request.getEmail(), "encoded_pw", request.getUserName(), request.getPhone(), request.getUserRole()
        );

        when(userRepository.save(any(Users.class))).thenReturn(savedUser);

        // when
        UserDto result = userService.save(request);

        // then
        assertEquals(request.getEmail(), result.getEmail());
        assertEquals(request.getUserName(), result.getUserName());

        verify(userRepository).existsByEmail(request.getEmail());
        verify(userRepository).save(any(Users.class));
    }

    @Test
    @DisplayName("이메일이 중복되면 DuplicateEmailException이 발생한다")
    void fail_save_duplicate_email() {
        // given
        SignUpRequest request = new SignUpRequestTest(
                "test@email.com", "1234", "tester", "01012345678", UserRole.BUYER
        );

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.save(request))
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessageContaining(ErrorType.DUPLICATE_EMAIL.getMessage());

        verify(userRepository, never()).save(any(Users.class));
    }
}
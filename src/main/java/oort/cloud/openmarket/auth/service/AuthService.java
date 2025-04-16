package oort.cloud.openmarket.auth.service;

import oort.cloud.openmarket.auth.controller.request.LoginRequest;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.auth.data.AuthToken;
import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.exception.business.InvalidPasswordException;
import oort.cloud.openmarket.exception.business.UserNotFoundException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(
            UserService userService, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public UserDto signUp(SignUpRequest request){
        return userService.save(request);
    }

    public UserDto login(LoginRequest loginRequest){
        Users user = userService.findUserByEmail(loginRequest.getEmail());
        validateUser(loginRequest, user);
        return UserDto.of(
                user.getUserId(),
                user.getEmail(),
                user.getUserName(),
                user.getPhone(),
                user.getUserRole(),
                user.getUserStatus()
        );
    }

    private void validateUser(LoginRequest loginRequest, Users user) {
        if(user == null)
            throw new UserNotFoundException(ErrorType.USER_NOT_FOUND);
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new InvalidPasswordException(ErrorType.INVALID_PASSWORD);
    }




}

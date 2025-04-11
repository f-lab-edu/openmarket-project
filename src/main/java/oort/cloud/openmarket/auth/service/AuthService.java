package oort.cloud.openmarket.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import oort.cloud.openmarket.auth.controller.request.LoginRequest;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.auth.controller.response.AuthTokenResponse;
import oort.cloud.openmarket.auth.jwt.JwtComponent;
import oort.cloud.openmarket.exception.AuthServiceException;
import oort.cloud.openmarket.exception.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtComponent jwtComponent;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtComponent jwtComponent) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtComponent = jwtComponent;
    }

    public UserDto signUp(SignUpRequest request){
        if(duplicateEmail(request.getEmail()))
            throw new AuthServiceException(ErrorType.DUPLICATE_EMAIL);

        Users savedUser = userRepository.save(
                Users.createUser(
                        request.getEmail(),
                        passwordEncoder.encode(request.getPassword()),
                        request.getUserName(),
                        request.getPhone(),
                        request.getUserRole()
                )
        );

        return savedUser.toDto();
    }

    public boolean duplicateEmail(String email){
        return userRepository.countByEmail(email) > 0;
    }

    public AuthTokenResponse login(LoginRequest loginRequest){
        Users user = userRepository.findByEmail(loginRequest.getEmail());
        validateUser(loginRequest, user);
        return jwtComponent.createAuthToken(user.toDto());
    }

    private void validateUser(LoginRequest loginRequest, Users user) {
        if(user == null)
            throw new AuthServiceException(ErrorType.USER_NOT_FOUND);
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new AuthServiceException(ErrorType.INVALID_PASSWORD);
    }

    public String refreshAccessToken(String refreshToken) {
        Jws<Claims> claimsJws = jwtComponent.validateToken(refreshToken);
        UserDto user = claimsJws.getBody().get("user", UserDto.class);
        return jwtComponent.getAccessToken(user);
    }
}

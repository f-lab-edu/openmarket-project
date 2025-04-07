package oort.cloud.openmarket.auth.service;

import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
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

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}

package oort.cloud.openmarket.user.service;

import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.exception.business.DuplicateEmailException;
import oort.cloud.openmarket.exception.business.UserNotFoundException;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto save(SignUpRequest request){
        if(duplicateEmail(request.getEmail()))
            throw new DuplicateEmailException(ErrorType.DUPLICATE_EMAIL);

        Users savedUser = userRepository.save(
                Users.createUser(
                        request.getEmail(),
                        passwordEncoder.encode(request.getPassword()),
                        request.getUserName(),
                        request.getPhone(),
                        request.getUserRole()
                )
        );

        return UserDto.of(
                savedUser.getUserId(),
                savedUser.getEmail(),
                savedUser.getUserName(),
                savedUser.getPhone(),
                savedUser.getUserRole(),
                savedUser.getUserStatus()
        );
    }

    public Users findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserDto findUserById(Long userId){
        return userRepository.findById(userId)
                .map(UserDto::from)
                .orElseThrow(() -> new UserNotFoundException(ErrorType.USER_NOT_FOUND));
    }

    public Users findUserEntityById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorType.USER_NOT_FOUND));
    }

    public boolean duplicateEmail(String email){
        return userRepository.existsByEmail(email);
    }
}

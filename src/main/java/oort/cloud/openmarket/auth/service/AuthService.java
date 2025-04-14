package oort.cloud.openmarket.auth.service;

import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public UserDto signUp(SignUpRequest request){
        return userService.save(request);
    }

}

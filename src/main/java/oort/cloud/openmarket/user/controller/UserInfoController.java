package oort.cloud.openmarket.user.controller;

import oort.cloud.openmarket.auth.data.AccessTokenPayload;
import oort.cloud.openmarket.auth.annotations.AccessToken;
import oort.cloud.openmarket.user.controller.response.UserInfoResponse;
import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {
    private final UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    /*
          필터 테스트 용도
     */
    @GetMapping("/v1/user/me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AccessToken AccessTokenPayload user){
        UserDto userDto = userService.findUserById(user.getUserId());
        return ResponseEntity.ok()
                .body(UserInfoResponse.from(userDto));
    }
}

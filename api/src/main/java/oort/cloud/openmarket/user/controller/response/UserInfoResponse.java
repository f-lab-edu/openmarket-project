package oort.cloud.openmarket.user.controller.response;

import oort.cloud.openmarket.user.data.UserDto;
import oort.cloud.openmarket.user.enums.UserRole;

public class UserInfoResponse {
    private String email;
    private String userName;
    private String phone;
    private UserRole userRole;

    public static UserInfoResponse of(String email, String userName, String phone, UserRole role){
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.email = email;
        userInfoResponse.userName = userName;
        userInfoResponse.phone = phone;
        userInfoResponse.userRole = role;
        return userInfoResponse;
    }

    public static UserInfoResponse from(UserDto userDto){
        return UserInfoResponse.of(
                userDto.getEmail(),
                userDto.getUserName(),
                userDto.getPhone(),
                userDto.getUserRole()
        );
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhone() {
        return phone;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}

package oort.cloud.openmarket.user.data;


import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.enums.UserStatus;

public class UserDto {
    private String email;
    private String userName;
    private String phone;
    private UserRole userRole;
    private UserStatus userStatus;

    public static UserDto of(String email, String userName, String phone, UserRole userRole, UserStatus userStatus) {
        UserDto userDto = new UserDto();
        userDto.email = email;
        userDto.userName = userName;
        userDto.phone = phone;
        userDto.userRole = userRole;
        userDto.userStatus = userStatus;
        return userDto;
    }

    private UserDto() {}

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

    public UserStatus getUserStatus() { return userStatus; }
}

package oort.cloud.openmarket.user.data;


import oort.cloud.openmarket.user.entity.Users;
import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.enums.UserStatus;

import java.util.Objects;

public class UserDto {
    private Long userId;
    private String email;
    private String userName;
    private String phone;
    private UserRole userRole;
    private UserStatus userStatus;

    public static UserDto of(Long userId, String email, String userName, String phone, UserRole userRole, UserStatus userStatus) {
        UserDto userDto = new UserDto();
        userDto.userId = userId;
        userDto.email = email;
        userDto.userName = userName;
        userDto.phone = phone;
        userDto.userRole = userRole;
        userDto.userStatus = userStatus;
        return userDto;
    }

    public static UserDto from(Users user){
        return UserDto.of(
                user.getUserId(),
                user.getEmail(),
                user.getUserName(),
                user.getPhone(),
                user.getUserRole(),
                user.getUserStatus()
        );
    }
    private UserDto() {}

    public Long getUserId(){
        return userId;
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

    public UserStatus getUserStatus() { return userStatus; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", userRole=" + userRole +
                ", userStatus=" + userStatus +
                '}';
    }
}

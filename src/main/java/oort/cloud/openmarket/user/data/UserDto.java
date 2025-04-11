package oort.cloud.openmarket.user.data;


import oort.cloud.openmarket.user.enums.UserRole;
import oort.cloud.openmarket.user.enums.UserStatus;

public class UserDto {
    private Long userId;
    private String email;
    private String userName;
    private String phone;
    private UserRole userRole;
    private UserStatus userStatus;

    public UserDto(Long userId, String email, String userName, String phone, UserRole userRole, UserStatus userStatus) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.phone = phone;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }
    public Long getUserId(){return userId;}
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

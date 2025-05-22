package oort.cloud.openmarket.data;

import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.user.enums.UserRole;

public class SignUpRequestTest extends SignUpRequest {
    private String email;
    private String password;
    private String userName;
    private String phone;
    private UserRole userRole;

    public SignUpRequestTest(String email, String password, String userName, String phone, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phone = phone;
        this.userRole = userRole;
    }

    public String getPassword() {return password;}

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

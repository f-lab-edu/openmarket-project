package oort.cloud.openmarket.auth.request;

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
}

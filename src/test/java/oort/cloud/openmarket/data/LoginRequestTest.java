package oort.cloud.openmarket.data;

import oort.cloud.openmarket.auth.controller.request.LoginRequest;

public class LoginRequestTest extends LoginRequest {
    private String email;
    private String password;
    public LoginRequestTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

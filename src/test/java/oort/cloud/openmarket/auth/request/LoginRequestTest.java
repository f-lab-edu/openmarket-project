package oort.cloud.openmarket.auth.request;

import oort.cloud.openmarket.auth.controller.request.LoginRequest;

public class LoginRequestTest extends LoginRequest {
    private String email;
    private String password;
    public LoginRequestTest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

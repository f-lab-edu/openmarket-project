package oort.cloud.openmarket.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class LoginRequest {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotEmpty(message = "이메일은 필수 값입니다.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수 값입니다.")
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

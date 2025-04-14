package oort.cloud.openmarket.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import oort.cloud.openmarket.user.enums.UserRole;

public class SignUpRequest {
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotNull(message = "이메일은 필수 값입니다.")
    @NotEmpty(message = "이메일은 필수 값입니다.")
    private String email;
    @NotNull(message = "비밀번호는 필수 값입니다.")
    @NotEmpty(message = "비밀번호는 필수 값입니다.")
    private String password;
    @NotNull(message = "이름은 필수 값입니다.")
    @NotEmpty(message = "이름은 필수 값입니다.")
    private String userName;
    @NotNull(message = "연락처는 필수 값입니다.")
    @NotEmpty(message = "연락처는 필수 값입니다.")
    private String phone;
    @NotNull(message = "유저 권한은 필수 값입니다.")
    private UserRole userRole;

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

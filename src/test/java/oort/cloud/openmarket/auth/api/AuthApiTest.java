package oort.cloud.openmarket.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import oort.cloud.openmarket.auth.controller.request.LoginRequest;
import oort.cloud.openmarket.data.LoginRequestTest;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
import oort.cloud.openmarket.data.SignUpRequestTest;
import oort.cloud.openmarket.user.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DisplayName("회원가입이 성공한다.")
    void success_sign_up() throws Exception {
        SignUpRequest request = getSignUpRequest();

        mockMvc.perform(post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    private SignUpRequest getSignUpRequest() {
        return new SignUpRequestTest(
                "test1@email.com",
                "test123",
                "test",
                "12312341234",
                UserRole.BUYER
        );
    }

    @Test
    @DisplayName("회원가입에 필요한 데이터가 유효하지 않은 경우 유요하지 않은 데이터를 응답으로 보낸다")
    void user_data_validation() throws Exception {
        //given
        String request = getInvalidRequest();

        //when then
        mockMvc.perform(post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().is4xxClientError())
                .andDo(print());
        /*
            {
                "errors":[
                    {"field":"password","message":"비밀번호는 필수 값입니다."},
                    {"field":"userRole","message":"유저 권한은 필수 값입니다."},
                    {"field":"email","message":"올바른 이메일 형식이 아닙니다."}
                ],
                "exceptionType":"INVALID_PARAMETER"
            }
         */
    }

    @Test
    @DisplayName("로그인에 성공하면 accessToken refreshToken을 반환한다.")
    void success_login() throws Exception {
        //given
        LoginRequest request = getLoginRequest();

        //when then
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }



    private String getInvalidRequest() {
        return """
                {
                    "email" : "ewqe",
                    "password" : "",
                    "userName" : "test",
                    "phone" : "12314"
                }
                """;
    }
    private LoginRequest getLoginRequest() {
        return new LoginRequestTest(
                "test1@email.com",
                "test123"
        );
    }
}

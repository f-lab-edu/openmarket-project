package oort.cloud.openmarket.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import oort.cloud.openmarket.auth.controller.request.SignUpRequest;
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

    @Test
    @DisplayName("회원가입이 성공한다.")
    void success_sign_up() throws Exception {
        String request = getValidRequest();

        mockMvc.perform(post("/v1/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
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

    private String getValidRequest() {
        return """
                {
                    "email" : "test223@email.com",
                    "password" : "1234",
                    "userName" : "test",
                    "phone" : "12341231234",
                    "userRole" : "BUYER"
                }
                """;
    }
}

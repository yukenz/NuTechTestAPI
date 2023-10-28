package nutech.awan.ppob.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.WebResponse;
import nutech.awan.ppob.utils.JWTUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTopUpTest {

    @Autowired
    MessageSource messageSource;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void loginAndGetBalance() throws Exception {

        LoginRequest formLogin = LoginRequest.builder()
                .email("yuyun.purniawan@gmail.com")
                .password("theravian")
                .build();

        //OK 200
        mockMvc.perform(
                        post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(formLogin))
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.status").value(HttpStatus.OK.value()),
                        jsonPath("$.message").value(messageSource.getMessage("login_success", null, Locale.of("id", "ID"))),
                        jsonPath("$.data.token").isString())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();

                    WebResponse<LoginResponse> loginResponseWebResponse = objectMapper.readValue(content, new TypeReference<WebResponse<LoginResponse>>() {
                    });

                    //Valid TopUp
                    topUpValidValue(loginResponseWebResponse.getData().getToken());

                    //InvalidTopUp
                    topUpInvalidValue(loginResponseWebResponse.getData().getToken());

                });
    }


    void topUpValidValue(String token) throws Exception {

        TopUpRequest form = TopUpRequest.builder().topUpAmount(10000L).build();

        mockMvc.perform(post("/topup")
                        .header("Authorization", JWTUtil.BEARER_TOKEN_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.status").value(HttpStatus.OK.value()),
                        jsonPath("$.message").value(messageSource.getMessage("topup_success", null, Locale.of("id", "ID"))),
                        jsonPath("$.data.balance").isNumber()
                )
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }

    void topUpInvalidValue(String token) throws Exception {

        TopUpRequest form = TopUpRequest.builder().topUpAmount(-10000L).build();

        mockMvc.perform(post("/topup")
                        .header("Authorization", JWTUtil.BEARER_TOKEN_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.message").value(messageSource.getMessage("topup_invalid", null, Locale.of("id", "ID"))),
                        jsonPath("$.data").value(Matchers.nullValue())
                )
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }

    @Test
    void topUpWithInvalidToken() throws Exception {

        TopUpRequest form = TopUpRequest.builder().topUpAmount(10000L).build();

        mockMvc.perform(post("/topup")
                        .header("Authorization", JWTUtil.BEARER_TOKEN_PREFIX + "dawdw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(form))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()),
                        jsonPath("$.message").value(messageSource.getMessage("token_error", null, Locale.of("id", "ID"))),
                        jsonPath("$.data").value(Matchers.nullValue())
                )
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }
}

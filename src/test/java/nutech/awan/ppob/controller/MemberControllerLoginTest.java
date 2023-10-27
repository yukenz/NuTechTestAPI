package nutech.awan.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerLoginTest {

    @Autowired
    MessageSource messageSource;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ResultHandler print = result -> System.out.println(result.getResponse().getContentAsString());

    @Test
    void testLoginValid() throws Exception {

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
                .andDo(print);


    }

    @Test
    void testLoginInvalidForm() throws Exception {

        LoginRequest formLogin = LoginRequest.builder()
                .email("yuyun.purniawangmail.com")
                .password("1234567")
                .build();

        //OK 200
        mockMvc.perform(
                        post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(formLogin))
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.message").isString(),
                        jsonPath("$.data").value(Matchers.nullValue())
                ).andDo(print);


    }

    @Test
    void testLoginWrongPassword() throws Exception {

        LoginRequest formLogin = LoginRequest.builder()
                .email("yuyun.purniawan@gmail.com")
                .password("theravians") //pass salah
                .build();

        //OK 200
        mockMvc.perform(
                        post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(formLogin))
                )
                .andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()),
                        jsonPath("$.message").value(messageSource.getMessage("login_invalid", null, Locale.of("id", "ID"))),
                        jsonPath("$.data").value(Matchers.nullValue())
                ).andDo(print);


    }

    @Test
    void testLoginWrongEmail() throws Exception {

        LoginRequest formLogin = LoginRequest.builder()
                .email("yuyun.urniawan@gmail.com")
                .password("theravian") //pass salah
                .build();

        //OK 200
        mockMvc.perform(
                        post("/login")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(formLogin))
                )
                .andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.value()),
                        jsonPath("$.message").value(messageSource.getMessage("login_invalid", null, Locale.of("id", "ID"))),
                        jsonPath("$.data").value(Matchers.nullValue())
                ).andDo(print);


    }
}

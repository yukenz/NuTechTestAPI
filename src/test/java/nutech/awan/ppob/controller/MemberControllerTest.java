package nutech.awan.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nutech.awan.ppob.model.request.RegistrationRequest;
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
public class MemberControllerTest {

    @Autowired
    MessageSource messageSource;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    void setUp() {
        memberRepository.flush();
    }

    ResultHandler print = result -> System.out.println(result.getResponse().getContentAsString());

    @Test
    void testRegisterAndDuplicate() throws Exception {

        setUp();

        RegistrationRequest formRegistration = RegistrationRequest.builder()
                .email("yuyun.purniawan@gmail.com")
                .password("theravian")
                .firstName("yuyun")
                .lastName("purniawan")
                .build();

        //OK 200
        mockMvc.perform(
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formRegistration))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.status").value(200),
                jsonPath("$.message").value(messageSource.getMessage("register_success", null, Locale.of("id", "ID"))),
                jsonPath("$.data").value(Matchers.nullValue())
        );

        //Duplikat Email 422
        mockMvc.perform(
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formRegistration))
        ).andExpectAll(
                status().isUnprocessableEntity(),
                jsonPath("$.status").value(HttpStatus.UNPROCESSABLE_ENTITY.value()),
                jsonPath("$.message").value("email sudah terdaftar"),
                jsonPath("$.data").value(Matchers.nullValue())
        ).andDo(print);

    }

    @Test
    void testRegisterWithInvalidValue() throws Exception {

        RegistrationRequest formRegistration = RegistrationRequest.builder()
                .email("yuyun.purniawangmail.com")
                .password("1234567")
                .firstName("yuyun")
                .lastName("purniawan")
                .build();

        //BadRequest 400
        mockMvc.perform(
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formRegistration))
        ).andExpectAll(
                status().isBadRequest(),
                jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
                jsonPath("$.message").isString(),
                jsonPath("$.data").value(Matchers.nullValue())
        ).andDo(print);


    }
}

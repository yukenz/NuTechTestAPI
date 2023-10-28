package nutech.awan.ppob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InformationControllerBannerTest {

    @Autowired
    MessageSource messageSource;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void testBanner() throws Exception {

        mockMvc.perform(get("/banner")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.status").value(HttpStatus.OK.value()),
                        jsonPath("$.message").value(messageSource.getMessage("success", null, Locale.of("id", "ID"))),
                        jsonPath("$.data").isArray()
                )
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }

}

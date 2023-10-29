package nutech.awan.ppob;


import com.fasterxml.jackson.databind.ObjectMapper;
import nutech.awan.ppob.model.request.RegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ConstraintViolationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testViolation() throws Exception {

        RegistrationRequest formRegistration = RegistrationRequest.builder()
                .email("yu")
                .password("asdwfas")
                .first_name("awan")
                .last_name("theravian")
                .build();

        mockMvc.perform(
                post("/registration")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formRegistration))
        ).andDo(result -> {
            String contentAsString = result.getResponse().getContentAsString();
            System.out.println(contentAsString);
        });

    }
}

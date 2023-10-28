package nutech.awan.ppob.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.ProfileUpdateRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.WebResponse;
import nutech.awan.ppob.utils.JWTUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerProfileUploadTest {

    @Autowired
    MessageSource messageSource;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void loginAndUpdateImage_Valid_Invalid() throws Exception {

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

                    updateImage(loginResponseWebResponse.getData().getToken());
                    updateImageWithInvalidJpg(loginResponseWebResponse.getData().getToken());

                });
    }

    void updateImage(String token) throws Exception {

        Resource resource = resourceLoader.getResource("classpath:/static/ktp.jpg");
        InputStream inputStream = resource.getInputStream();

        mockMvc.perform(put("/profile/image")
                        .header("Authorization", JWTUtil.BEARER_TOKEN_PREFIX + token)
                        .contentType(MediaType.IMAGE_JPEG)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(inputStream.readAllBytes())
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.status").value(HttpStatus.OK.value()),
                        jsonPath("$.message").value(messageSource.getMessage("profile_image_update_success", null, Locale.of("id", "ID"))),
                        jsonPath("$.data.profile_image").isString()
                )
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }

    void updateImageWithInvalidJpg(String token) throws Exception {

        Resource resource = resourceLoader.getResource("classpath:/static/my.txt");
        InputStream inputStream = resource.getInputStream();

        mockMvc.perform(put("/profile/image")
                        .header("Authorization", JWTUtil.BEARER_TOKEN_PREFIX + token)
                        .contentType(MediaType.IMAGE_JPEG)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(inputStream.readAllBytes())
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()),
                        jsonPath("$.message").value(messageSource.getMessage("profile_image_update_invalid", null, Locale.of("id", "ID"))),
                        jsonPath("$.data").value(Matchers.nullValue())
                )
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
    }

    @Test
    void getProfileWitInvalidToken() throws Exception {

        ProfileUpdateRequest formUpdateProfile = ProfileUpdateRequest.builder()
                .firstName("yuyun")
                .lastName("purniawan")
                .build();

        mockMvc.perform(put("/profile/update")
                        .header("Authorization", JWTUtil.BEARER_TOKEN_PREFIX + "asdw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(formUpdateProfile))
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

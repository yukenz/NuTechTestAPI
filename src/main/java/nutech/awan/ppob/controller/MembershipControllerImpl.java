package nutech.awan.ppob.controller;

import nutech.awan.ppob.controller.interfaces.MembershipController;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.ProfileUpdateRequest;
import nutech.awan.ppob.model.request.RegistrationRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.ProfileViewResponse;
import nutech.awan.ppob.model.response.WebResponse;
import nutech.awan.ppob.service.interfaces.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@Component
public class MembershipControllerImpl implements MembershipController {

    @Autowired
    MembershipService membershipService;
    @Autowired
    MessageSource messageSource;

    @Override
    public WebResponse<String> registration(RegistrationRequest form) {

        membershipService.registration(form);

        return WebResponse.<String>builder()
                .status(HttpStatus.OK.value())
                .message(messageSource.getMessage("register_success", null, Locale.of("id", "ID")))
                .build();
    }

    @Override
    public WebResponse<LoginResponse> login(LoginRequest form) {

        LoginResponse loginResponse = membershipService.login(form);

        return WebResponse.<LoginResponse>builder()
                .status(HttpStatus.OK.value())
                .message(messageSource.getMessage("login_success", null, Locale.of("id", "ID")))
                .data(loginResponse)
                .build();
    }

    @Override
    public WebResponse<ProfileViewResponse> profile(Member member) {
        return null;
    }

    @Override
    public WebResponse<ProfileViewResponse> profileUpdate(Member member, ProfileUpdateRequest form) {
        return null;
    }

    @Override
    public WebResponse<ProfileViewResponse> profilImage(Member member, MultipartFile fileForm) {
        return null;
    }
}

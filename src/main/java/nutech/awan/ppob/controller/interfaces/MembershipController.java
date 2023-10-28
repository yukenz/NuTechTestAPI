package nutech.awan.ppob.controller.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.ProfileUpdateRequest;
import nutech.awan.ppob.model.request.RegistrationRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.ProfileViewResponse;
import nutech.awan.ppob.model.response.WebResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public interface MembershipController {

    /* POST: registration */
    @PostMapping(
            path = "/registration",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse registration(@RequestBody RegistrationRequest form);

    /* POST: login */
    @PostMapping(
            path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<LoginResponse> login(@RequestBody LoginRequest form);

    /* GET: profile | TOKEN */
    @GetMapping(
            path = "/profile",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<ProfileViewResponse> profile(Member member);

    /* PUT: profile->update | TOKEN */
    @PutMapping(
            path = "/profile/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<ProfileViewResponse> profileUpdate(Member member, @RequestBody ProfileUpdateRequest form);

    /* PUT: profile->image | TOKEN */
    @PutMapping(
            path = "/profile/image",
            consumes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<ProfileViewResponse> profilImage(Member member, HttpServletRequest request);


}

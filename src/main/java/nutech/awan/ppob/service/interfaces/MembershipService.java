package nutech.awan.ppob.service.interfaces;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.ProfileUpdateRequest;
import nutech.awan.ppob.model.request.RegistrationRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.ProfileViewResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MembershipService {

    void registration(RegistrationRequest registrationRequest);

    LoginResponse login(LoginRequest loginRequest);

    ProfileViewResponse profile(Member member);

    ProfileViewResponse profileUpdate(Member member, ProfileUpdateRequest profileUpdateRequest);

    ProfileViewResponse profileImage(Member member, MultipartFile fileForm);


}

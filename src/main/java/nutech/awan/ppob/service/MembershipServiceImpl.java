package nutech.awan.ppob.service;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.RegistrationRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.ProfileViewResponse;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import nutech.awan.ppob.service.interfaces.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@Component
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public void registration(RegistrationRequest registrationRequest) {

        validationService.validateObject(registrationRequest);

        if (memberRepository.isExist(registrationRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "email sudah terdaftar");
        }

        Member member = Member.builder()
                .email(registrationRequest.getEmail())
                .password(DigestUtils.md5DigestAsHex(registrationRequest.getPassword().getBytes()))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .build();

        try {
            memberRepository.save(member);
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        validationService.validateObject(loginRequest);


        return null;
    }

    @Override
    public ProfileViewResponse profile(Member member) {

        return null;
    }

    @Override
    public ProfileViewResponse profileUpdate(Member member, LoginRequest loginRequest) {
        validationService.validateObject(loginRequest);

        return null;
    }

    @Override
    public ProfileViewResponse profileImage(Member member, MultipartFile fileForm) {

        return null;
    }
}

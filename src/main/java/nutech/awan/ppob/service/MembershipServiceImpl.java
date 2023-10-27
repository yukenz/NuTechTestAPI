package nutech.awan.ppob.service;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.RegistrationRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.ProfileViewResponse;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import nutech.awan.ppob.service.interfaces.MembershipService;
import nutech.awan.ppob.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Locale;

@Component
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public void registration(RegistrationRequest registrationRequest) {

        validationService.validateObject(registrationRequest);

        if (memberRepository.isIdExist(registrationRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "email sudah terdaftar");
        }

        Member member = Member.builder()
                .email(registrationRequest.getEmail())
                .password(DigestUtils.md5DigestAsHex(registrationRequest.getPassword().getBytes()))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .profileImage(null)
                .balance(0L)
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

        Member member = memberRepository.findById(loginRequest.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        messageSource.getMessage("login_invalid", null, Locale.of("id", "ID"))
                ));

        //Password hash ga sama
        if (!DigestUtils.md5DigestAsHex(loginRequest.getPassword().getBytes()).equals(member.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    messageSource.getMessage("login_invalid", null, Locale.of("id", "ID"))
            );
        }

        //Token valid 12 jam dengan subject email
        String token = jwtUtil.createToken(
                member.getEmail(),
                Duration.ofHours(12).toMillis()
        );

        return LoginResponse.builder()
                .token(token).build();
    }

    @Override
    public ProfileViewResponse profile(Member member) {

        return ProfileViewResponse.builder()
                .email(member.getEmail())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .profileImage(member.getProfileImage())
                .build();
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

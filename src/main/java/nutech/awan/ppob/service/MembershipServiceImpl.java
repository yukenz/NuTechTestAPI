package nutech.awan.ppob.service;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.LoginRequest;
import nutech.awan.ppob.model.request.ProfileUpdateRequest;
import nutech.awan.ppob.model.request.RegistrationRequest;
import nutech.awan.ppob.model.response.LoginResponse;
import nutech.awan.ppob.model.response.ProfileViewResponse;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import nutech.awan.ppob.service.interfaces.MembershipService;
import nutech.awan.ppob.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Locale;

@Component
public class MembershipServiceImpl implements MembershipService {

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MemberRepository memberRepository;

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
                .firstName(registrationRequest.getFirst_name())
                .lastName(registrationRequest.getLast_name())
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
                .profile_image(member.getProfileImage())
                .build();
    }

    @Override
    public ProfileViewResponse profileUpdate(Member member, ProfileUpdateRequest profileUpdateRequest) {

        validationService.validateObject(profileUpdateRequest);

        //Sudah di verifikasi not blank jadi set saja
        member.setFirstName(profileUpdateRequest.getFirst_name());
        member.setLastName(profileUpdateRequest.getLast_name());

        try {
            memberRepository.update(member);
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        return ProfileViewResponse.builder()
                .email(member.getEmail())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .profile_image(member.getProfileImage())
                .build();
    }

    @Override
    public ProfileViewResponse profileImage(Member member, HttpServletRequest request) {

        /* Get Resource Path at Runtime a.k.a Directotory*/
        Resource assetsFolder = resourceLoader.getResource("classpath:/assets/");
        /* Parsing Name File a.k.a FileName.ext */
        String fileName = member.getEmail().replaceAll("\\W", "_") + ".jpg";

        //Validasi Image Valid
        try (ServletInputStream inputStream = request.getInputStream()) {
            Path pathFile = Path.of(
                    Path.of(assetsFolder.getURI()).toString() + "/" + fileName
            );
            ImageIO.write(validationService.isValidImage(inputStream), "jpg", pathFile.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    messageSource.getMessage("profile_image_update_invalid", null, Locale.of("id", "ID"))
            );
        }

        //Update Entity
        member.setProfileImage(String.format("%s/%s", appUrl, fileName));
        try {
            memberRepository.update(member);
        } catch (SQLException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }

        return ProfileViewResponse.builder()
                .email(member.getEmail())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .profile_image(member.getProfileImage())
                .build();
    }
}

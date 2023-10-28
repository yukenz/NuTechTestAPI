package nutech.awan.ppob.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Locale;
import java.util.Set;

@Service
public class ValidationService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private Validator validator;

    public void validateObject(Object object) {

        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        //Ada Violasi
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

    }

    public BufferedImage isValidImage(InputStream inputStream) {

        try (inputStream) {
            BufferedImage image = ImageIO.read(inputStream);
            image.getHeight();
            image.getWidth();

            return image;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("profile_image_update_invalid", null, Locale.of("id", "ID"))
            );
        }

    }

    public Long validateBalance(long balance, long purchase) {

        long moneyAfter = balance - purchase;

        if (moneyAfter < 0) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    messageSource.getMessage("transaction_balanceproblem", null, Locale.of("id", "ID"))
            );
        }

        return moneyAfter;

    }

}

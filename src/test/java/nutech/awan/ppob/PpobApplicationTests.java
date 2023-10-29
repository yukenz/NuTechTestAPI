package nutech.awan.ppob;

import nutech.awan.ppob.service.ValidationService;
import nutech.awan.ppob.utils.ImageResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@SpringBootTest
class PpobApplicationTests {

    @Autowired
    MessageSource messageSource;

    @Autowired
    ImageResource imageResource;

    @Autowired
    ValidationService validationService;

    @Test
    void messagesProperties() {
        String message = messageSource.getMessage("token_error", null, Locale.of("id", "ID"));
        Assertions.assertEquals("Token tidak tidak valid atau kadaluwarsa", message);
    }
}

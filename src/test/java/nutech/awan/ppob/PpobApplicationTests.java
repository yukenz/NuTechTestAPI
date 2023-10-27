package nutech.awan.ppob;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
class PpobApplicationTests {

    @Autowired
    MessageSource messageSource;

    @Test
    void messagesProperties() {
        String message = messageSource.getMessage("token_error", null, Locale.of("id", "ID"));
        Assertions.assertEquals("Token tidak tidak valid atau kadaluwarsa", message);
    }

}

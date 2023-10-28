package nutech.awan.ppob;

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

    @Test
    void messagesProperties() {
        String message = messageSource.getMessage("token_error", null, Locale.of("id", "ID"));
        Assertions.assertEquals("Token tidak tidak valid atau kadaluwarsa", message);
    }

    @Test
    void imageResourceAndValidate() throws IOException {

        InputStream txt = imageResource.getImageStream("my.txt");
        InputStream image = imageResource.getImageStream("ktp.jpg");

        Assertions.assertNotNull(txt);
        Assertions.assertNotNull(image);

        System.out.println(imageResource.isValidImage(image));
        System.out.println(imageResource.isValidImage(txt));

    }
}

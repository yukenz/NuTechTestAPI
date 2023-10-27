package nutech.awan.ppob;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import nutech.awan.ppob.service.ValidationService;
import org.hibernate.validator.constraints.Length;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidatorTest {

    public static class ConstraintTest {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String val1;

        @Length(max = 10)
        private String val2;

        @Min(0)
        private Integer val3;


        public ConstraintTest(String email, String val1, String val2, Integer val3) {
            this.email = email;
            this.val1 = val1;
            this.val2 = val2;
            this.val3 = val3;
        }
    }

    @Autowired
    ValidationService validationService;

    @Test
    void testValidation() {

        Assertions.assertThrows(ConstraintViolationException.class,
                () -> validationService.validateObject(new ConstraintTest("yuyun", " ", "dadadfasfawdadwda", -1))
        );

    }
}

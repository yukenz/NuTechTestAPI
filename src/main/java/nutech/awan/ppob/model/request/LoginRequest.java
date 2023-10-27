package nutech.awan.ppob.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class LoginRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8)
    private String password;


}

package nutech.awan.ppob.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String first_name;

    @NotBlank
    private String last_name;

    @NotBlank
    @Length(min = 8)
    private String password;

}

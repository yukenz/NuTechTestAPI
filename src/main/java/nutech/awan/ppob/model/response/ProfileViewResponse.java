package nutech.awan.ppob.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileViewResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String profileImage;

}

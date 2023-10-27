package nutech.awan.ppob.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileViewResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String profileImage;

}

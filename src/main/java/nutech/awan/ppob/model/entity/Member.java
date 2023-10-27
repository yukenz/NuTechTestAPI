package nutech.awan.ppob.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String profileImage;
    private Long balance;
}

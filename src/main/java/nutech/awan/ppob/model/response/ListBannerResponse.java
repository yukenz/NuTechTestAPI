package nutech.awan.ppob.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListBannerResponse {

    private String banner_name;
    private String banner_image;
    private String description;

}

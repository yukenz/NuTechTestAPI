package nutech.awan.ppob.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListBannerResponse {

    private String banner_name;
    private String banner_image;
    private String description;

}

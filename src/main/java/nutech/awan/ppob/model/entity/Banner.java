package nutech.awan.ppob.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner {

    private Integer id;
    private String name;
    private String image_url;
    private String description;

}

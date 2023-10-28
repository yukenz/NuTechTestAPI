package nutech.awan.ppob.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/* to avoid conflict package with Service Annotation */
public class ServicePayment {

    private String code;
    private String name;
    private String description;
    private Long price;
    private String icon_url;

}

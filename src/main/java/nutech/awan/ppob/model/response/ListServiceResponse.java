package nutech.awan.ppob.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListServiceResponse {

    private String service_code;
    private String service_name;
    private String service_icon;
    private Long service_tarif;

}

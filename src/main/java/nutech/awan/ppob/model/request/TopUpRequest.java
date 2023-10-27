package nutech.awan.ppob.model.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopUpRequest {

    @Min(0)
    private Long topUpAmount;

}

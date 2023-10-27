package nutech.awan.ppob.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopUpAndBalanceResponse {

    private Long balance;

}

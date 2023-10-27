package nutech.awan.ppob.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequest {

    private String serviceCode;

}

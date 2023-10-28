package nutech.awan.ppob.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private String invoice_number;
    private String service_code;
    private String service_name;
    private String transaction_type;
    private Long total_amount;
    private Instant created_on;

}

package nutech.awan.ppob.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionResponse {

    private String invoice_number;
    private String service_code;
    private String service_name;
    private String transaction_type;
    private Long total_amount;
    private Date created_on;

}

package nutech.awan.ppob.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListTransactionHistory {

    private String invoice_number;
    private String transaction_type;
    private String description;
    private Long total_amount;
    private Date created_on;
}

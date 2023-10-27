package nutech.awan.ppob.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryResponse {

    private Integer offset;
    private Integer limit;
    private List<ListTransactionHistory> transactionHistories;

}

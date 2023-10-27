package nutech.awan.ppob.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionHistoryResponse {

    private Integer offset;
    private Integer limit;
    private List<ListTransactionHistory> transactionHistories;

}

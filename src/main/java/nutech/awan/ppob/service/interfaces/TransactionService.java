package nutech.awan.ppob.service.interfaces;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.entity.ServicePayment;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.response.TopUpAndBalanceResponse;
import nutech.awan.ppob.model.response.TransactionHistoryResponse;
import nutech.awan.ppob.model.response.TransactionResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public interface TransactionService {

    TopUpAndBalanceResponse balance(Member member);

    TopUpAndBalanceResponse topup(Member member, TopUpRequest topUpRequest);

    TransactionResponse transaction(Member member, ServicePayment servicePayment);

    List<TransactionHistoryResponse> transactionHistory(Member member, Integer offset, Integer limit);

    String createInvoice(Instant instantTime);
}

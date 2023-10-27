package nutech.awan.ppob.service.interfaces;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.request.TransactionRequest;
import nutech.awan.ppob.model.response.TopUpAndBalanceResponse;
import nutech.awan.ppob.model.response.TransactionHistoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    TopUpAndBalanceResponse balance(Member member);

    TopUpAndBalanceResponse topup(Member member, TopUpRequest topUpRequest);

    TopUpAndBalanceResponse transaction(Member member, TransactionRequest transactionRequest);

    List<TransactionHistoryResponse> transactionHistory(Member member, Integer offset, Integer limit);

}

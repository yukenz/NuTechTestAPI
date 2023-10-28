package nutech.awan.ppob.service;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.request.TransactionRequest;
import nutech.awan.ppob.model.response.TopUpAndBalanceResponse;
import nutech.awan.ppob.model.response.TransactionHistoryResponse;
import nutech.awan.ppob.service.interfaces.MembershipService;
import nutech.awan.ppob.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private MembershipService membershipService;

    @Override
    public TopUpAndBalanceResponse balance(Member member) {

        return TopUpAndBalanceResponse.builder()
                .balance(member.getBalance())
                .build();
    }

    @Override
    public TopUpAndBalanceResponse topup(Member member, TopUpRequest topUpRequest) {
        return null;
    }

    @Override
    public TopUpAndBalanceResponse transaction(Member member, TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public List<TransactionHistoryResponse> transactionHistory(Member member, Integer offset, Integer limit) {
        return null;
    }
}

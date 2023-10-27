package nutech.awan.ppob.controller;

import nutech.awan.ppob.controller.interfaces.TransactionController;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.request.TransactionRequest;
import nutech.awan.ppob.model.response.TopUpAndBalanceResponse;
import nutech.awan.ppob.model.response.TransactionHistoryResponse;
import nutech.awan.ppob.model.response.TransactionResponse;
import nutech.awan.ppob.model.response.WebResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionControllerImpl implements TransactionController {
    @Override
    public WebResponse<TopUpAndBalanceResponse> balance(Member member) {
        return null;
    }

    @Override
    public WebResponse<TopUpAndBalanceResponse> topup(Member member, TopUpRequest topUpRequest) {
        return null;
    }

    @Override
    public WebResponse<TransactionResponse> transaction(Member member, TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public WebResponse<List<TransactionHistoryResponse>> transactionHistory(Member member, Integer offset, Integer limit) {
        return null;
    }
}

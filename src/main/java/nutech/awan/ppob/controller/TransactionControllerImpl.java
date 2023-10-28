package nutech.awan.ppob.controller;

import nutech.awan.ppob.controller.interfaces.TransactionController;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.entity.ServicePayment;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.response.TopUpAndBalanceResponse;
import nutech.awan.ppob.model.response.TransactionHistoryResponse;
import nutech.awan.ppob.model.response.TransactionResponse;
import nutech.awan.ppob.model.response.WebResponse;
import nutech.awan.ppob.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TransactionControllerImpl implements TransactionController {

    @Autowired
    TransactionService transactionService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public WebResponse<TopUpAndBalanceResponse> balance(Member member) {

        TopUpAndBalanceResponse balance = transactionService.balance(member);

        return WebResponse.<TopUpAndBalanceResponse>builder()
                .status(HttpStatus.OK.value())
                .message(messageSource.getMessage("balance_success", null, Locale.of("id", "ID")))
                .data(balance)
                .build();

    }

    @Override
    public WebResponse<TopUpAndBalanceResponse> topup(Member member, TopUpRequest topUpRequest) {

        TopUpAndBalanceResponse topup = transactionService.topup(member, topUpRequest);

        return WebResponse.<TopUpAndBalanceResponse>builder()
                .status(HttpStatus.OK.value())
                .message(messageSource.getMessage("topup_success", null, Locale.of("id", "ID")))
                .data(topup)
                .build();
    }

    @Override
    public WebResponse<TransactionResponse> transaction(Member member, ServicePayment servicePayment) {

        TransactionResponse transaction = transactionService.transaction(member, servicePayment);

        return WebResponse.<TransactionResponse>builder()
                .status(HttpStatus.OK.value())
                .message(messageSource.getMessage("transaction_success", null, Locale.of("id", "ID")))
                .data(transaction)
                .build();
    }

    @Override
    public WebResponse<TransactionHistoryResponse> transactionHistory(Member member, Integer offset, Integer limit) {

        TransactionHistoryResponse transactionHistoryResponse = transactionService.transactionHistory(member, offset, limit);

        return WebResponse.<TransactionHistoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message(messageSource.getMessage("history_success", null, Locale.of("id", "ID")))
                .data(transactionHistoryResponse)
                .build();
    }
}

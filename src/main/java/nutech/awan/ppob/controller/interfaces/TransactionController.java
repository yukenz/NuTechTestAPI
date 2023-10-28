package nutech.awan.ppob.controller.interfaces;


import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.entity.ServicePayment;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.request.TransactionRequest;
import nutech.awan.ppob.model.response.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface TransactionController {

    /* GET: balance | TOKEN */
    @GetMapping(
            path = "/balance",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<TopUpAndBalanceResponse> balance(Member member);

    /* POST: topup | TOKEN */
    @PostMapping(
            path = "/topup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<TopUpAndBalanceResponse> topup(Member member, @RequestBody TopUpRequest topUpRequest);

    /* POST: transaction | TOKEN */
    @PostMapping(
            path = "/transaction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<TransactionResponse> transaction(Member member, ServicePayment servicePayment);

    /* POST: transaction | TOKEN */
    @GetMapping(
            path = "/transaction/history",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<TransactionHistoryResponse> transactionHistory(
            Member member,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(required = false,defaultValue = "100") Integer limit
    );


}

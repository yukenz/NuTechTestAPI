package nutech.awan.ppob.service;

import jakarta.validation.ConstraintViolationException;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.entity.ServicePayment;
import nutech.awan.ppob.model.entity.TransactionHistory;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.response.ListTransactionHistory;
import nutech.awan.ppob.model.response.TopUpAndBalanceResponse;
import nutech.awan.ppob.model.response.TransactionHistoryResponse;
import nutech.awan.ppob.model.response.TransactionResponse;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import nutech.awan.ppob.repository.interfaces.TransactionHistoriRepository;
import nutech.awan.ppob.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    ValidationService validationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TransactionHistoriRepository transactionHistoriRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public TopUpAndBalanceResponse balance(Member member) {

        return TopUpAndBalanceResponse.builder()
                .balance(member.getBalance())
                .build();
    }

    @Override
    public TopUpAndBalanceResponse topup(Member member, TopUpRequest topUpRequest) {


        //Message menyesuaikan API
        try {
            validationService.validateObject(topUpRequest);
        } catch (ConstraintViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("topup_invalid", null, Locale.of("id", "ID"))
            );
        }

        //Time Stamp
        Instant now = Instant.now();

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .invoice(createInvoice(now))
                .memberEmail(member.getEmail())
                .description("Top Up")
                .amount(topUpRequest.getTopUpAmount())
                .type(TransactionHistory.TransactionType.TOPUP)
                .createdOn(now)
                .build();

        //Handle Saving
        try {
            transactionHistoriRepository.save(transactionHistory);
            Long balanceAfter = memberRepository.updateBalanceById(member.getEmail(), topUpRequest.getTopUpAmount());
            member.setBalance(balanceAfter);
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        return TopUpAndBalanceResponse.builder()
                .balance(member.getBalance())
                .build();
    }

    @Override
    public TransactionResponse transaction(Member member, ServicePayment servicePayment) {

        //Time Stamp
        Instant now = Instant.now();

        //Check Balance For Transaction
        Long moneyAfter = validationService.validateBalance(member.getBalance(), servicePayment.getPrice());

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .invoice(createInvoice(now))
                .memberEmail(member.getEmail())
                .description(servicePayment.getDescription())
                .amount(servicePayment.getPrice())
                .type(TransactionHistory.TransactionType.PAYMENT)
                .createdOn(now)
                .build();

        try {
            transactionHistoriRepository.save(transactionHistory);
            member.setBalance(moneyAfter);
            memberRepository.update(member);
        } catch (SQLException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }

        return TransactionResponse.builder()
                .invoice_number(transactionHistory.getInvoice())
                .service_code(servicePayment.getCode())
                .service_name(servicePayment.getName())
                .transaction_type(transactionHistory.getType().value())
                .total_amount(transactionHistory.getAmount())
                .created_on(transactionHistory.getCreatedOn())
                .build();
    }

    @Override
    public TransactionHistoryResponse transactionHistory(Member member, Integer offset, Integer limit) {

        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(
                Sort.Order.desc("created_on")
        ));

        try {
            List<ListTransactionHistory> listTransactionHistories = transactionHistoriRepository.findAllByEmail(member.getEmail(), pageRequest)
                    .stream().map(transactions -> ListTransactionHistory.builder()
                            .invoice_number(transactions.getInvoice())
                            .transaction_type(transactions.getType().value())
                            .description(transactions.getDescription())
                            .total_amount(transactions.getAmount())
                            .created_on(transactions.getCreatedOn())
                            .build()
                    ).toList();


            return TransactionHistoryResponse.builder()
                    .offset(offset)
                    .limit(limit)
                    .records(listTransactionHistories)
                    .build();

        } catch (SQLException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }

    }

    @Override
    public String createInvoice(Instant instantTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy-HHmmssSS");
        String dateHash = sdf.format(Date.from(instantTime));

        return String.format("INV%s", dateHash);

    }
}

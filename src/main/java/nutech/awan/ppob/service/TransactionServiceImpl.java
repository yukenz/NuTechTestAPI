package nutech.awan.ppob.service;

import jakarta.validation.ConstraintViolationException;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.request.TopUpRequest;
import nutech.awan.ppob.model.request.TransactionRequest;
import nutech.awan.ppob.model.response.TopUpAndBalanceResponse;
import nutech.awan.ppob.model.response.TransactionHistoryResponse;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import nutech.awan.ppob.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    ValidationService validationService;

    @Autowired
    private MemberRepository memberRepository;

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

        member.setBalance(member.getBalance() + topUpRequest.getTopUpAmount());

        //Handle Saving
        try {
            memberRepository.update(member);
        } catch (SQLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        return TopUpAndBalanceResponse.builder()
                .balance(member.getBalance())
                .build();
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

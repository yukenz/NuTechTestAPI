package nutech.awan.ppob.repository.interfaces;

import nutech.awan.ppob.model.entity.TransactionHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface TransactionHistoriRepository {

    String TABLENAME = "transactions";

    void save(TransactionHistory transactionHistory) throws SQLException;

    List<TransactionHistory> findAllByEmail(String email, PageRequest pageRequest) throws SQLException;
}

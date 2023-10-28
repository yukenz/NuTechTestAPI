package nutech.awan.ppob.repository.interfaces;

import nutech.awan.ppob.model.entity.TransactionHistory;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface TransactionHistoriRepository {

    String TABLENAME = "transactions";

    void save(TransactionHistory transactionHistory) throws SQLException;
}

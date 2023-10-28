package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.entity.TransactionHistory;
import nutech.awan.ppob.repository.interfaces.TransactionHistoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class TransactionHistoryRepositoryImpl implements TransactionHistoriRepository {

    @Autowired
    private HikariDataSource dataSource;

    @Override
    public void save(TransactionHistory transactionHistory) throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        String.format("insert into transactions(invoice, member_email, type, description, amount, created_on) values (?, ?, ?, ?, ?, ?);")
                )
        ) {

            statement.setString(1, transactionHistory.getInvoice());
            //Constraint
            statement.setString(2, transactionHistory.getMemberEmail());
            //Enum
            statement.setString(3, transactionHistory.getType().value());
            statement.setString(4, transactionHistory.getDescription());
            statement.setLong(5, transactionHistory.getAmount());
            //utils to sql
            statement.setTimestamp(6, Timestamp.from(transactionHistory.getCreatedOn()));

            statement.execute();

        } catch (SQLException e) {
            throw e;
        }

    }
}

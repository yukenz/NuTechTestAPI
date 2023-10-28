package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.entity.TransactionHistory;
import nutech.awan.ppob.repository.interfaces.TransactionHistoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<TransactionHistory> findAllByEmail(String email, PageRequest pageRequest) throws SQLException {

        //Parsing Sort
        String orderBy = pageRequest.getSort().get().map(
                order -> String.format("%s %s", order.getProperty(), order.getDirection().toString())
        ).collect(Collectors.joining(","));

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        String.format("select * from %s where member_email = ? order by ? limit ? offset ?", TransactionHistoriRepository.TABLENAME)
                )
        ) {

            preparedStatement.setString(1, email); //Email
            preparedStatement.setString(2, orderBy); //Order by name asc
            preparedStatement.setInt(3, pageRequest.getPageSize()); //limit
            preparedStatement.setInt(4, pageRequest.getPageNumber()); //offset

            ResultSet resultSet = preparedStatement.executeQuery();

            List<TransactionHistory> transactions = new ArrayList<>();

            while (resultSet.next()) {
                transactions.add(TransactionHistory.builder()
                        .invoice(resultSet.getString("invoice"))
                        .memberEmail(resultSet.getString("member_email"))
                        .description(resultSet.getString("description"))
                        .amount(resultSet.getLong("amount"))
                        .type(TransactionHistory.TransactionType
                                .valueOf(resultSet.getString("type"))
                        )
                        .createdOn(resultSet.getTimestamp("created_on").toInstant())
                        .build());
            }

            return transactions;

        } catch (SQLException e) {
            throw e;
        }
    }
}

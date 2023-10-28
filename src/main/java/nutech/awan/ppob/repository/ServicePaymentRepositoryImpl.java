package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.entity.ServicePayment;
import nutech.awan.ppob.repository.interfaces.ServicePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ServicePaymentRepositoryImpl implements ServicePaymentRepository {

    @Autowired
    private HikariDataSource dataSource;

    @Override
    public List<ServicePayment> findAll() throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {

            List<ServicePayment> payments = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery(
                    String.format("select * from %s", ServicePaymentRepository.TABLENAME)
            );

            while (resultSet.next()) {
                payments.add(ServicePayment.builder()
                        .code(resultSet.getString("code"))
                        .description(resultSet.getString("description"))
                        .name(resultSet.getString("name"))
                        .icon_url(resultSet.getString("icon_url"))
                        .price(resultSet.getLong("price"))
                        .build()
                );
            }

            return payments;
        } catch (SQLException e) {
            throw e;
        }

    }

    @Override
    public Optional<ServicePayment> findById(String code) {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        String.format("select * from %s where code = ?", ServicePaymentRepository.TABLENAME)
                )
        ) {

            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();


            if (!resultSet.first()) {
                return Optional.empty();
            }

            ServicePayment servicePayment = ServicePayment.builder()
                    .code(resultSet.getString("code"))
                    .description(resultSet.getString("description"))
                    .name(resultSet.getString("name"))
                    .icon_url(resultSet.getString("icon_url"))
                    .price(resultSet.getLong("price"))
                    .build();

            return Optional.of(servicePayment);

        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }
}

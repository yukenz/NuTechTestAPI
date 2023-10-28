package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.entity.ServicePayment;
import nutech.awan.ppob.repository.interfaces.ServicePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

//            code, name, description, price, icon_url
            while (resultSet.next()) {
                payments.add(ServicePayment.builder()
                        .code(resultSet.getString("code"))
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
}

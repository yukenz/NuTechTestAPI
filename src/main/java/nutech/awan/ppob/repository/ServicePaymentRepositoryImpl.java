package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.response.ListServiceResponse;
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
    public List<ListServiceResponse> findAll() throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {

            List<ListServiceResponse> listBannerResponses = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery(
                    String.format("select * from %s", ServicePaymentRepository.TABLENAME)
            );

//            while (resultSet.next()) {
//                listBannerResponses.add(ListServiceResponse.builder()
//                        .service_code()
//                        .service_name()
//                        .service_icon()
//                        .service_tarif()
//                        .build());
//            }

            return listBannerResponses;
        } catch (SQLException e) {
            throw e;
        }

    }
}

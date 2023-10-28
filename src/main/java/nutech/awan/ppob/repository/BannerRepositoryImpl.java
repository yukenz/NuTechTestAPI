package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.response.ListBannerResponse;
import nutech.awan.ppob.repository.interfaces.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class BannerRepositoryImpl implements BannerRepository {

    @Autowired
    private HikariDataSource dataSource;

    @Override
    public List<ListBannerResponse> findAll() throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {

            List<ListBannerResponse> listBannerResponses = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery(
                    String.format("select * from %s", BannerRepository.TABLENAME)
            );

            while (resultSet.next()) {
                listBannerResponses.add(ListBannerResponse.builder()
                        .banner_name(resultSet.getString("name"))
                        .banner_image("image_url")
                        .description("description")
                        .build());
            }

            return listBannerResponses;
        } catch (SQLException e) {
            throw e;
        }

    }
}

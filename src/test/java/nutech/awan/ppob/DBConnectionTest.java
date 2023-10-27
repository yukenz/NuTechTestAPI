package nutech.awan.ppob;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DBConnectionTest {

    @Autowired
    HikariDataSource hikariDataSource;

    @Test
    void testGetConnection() throws SQLException {

        Connection connection = hikariDataSource.getConnection();
        Assertions.assertNotNull(connection);

    }
}

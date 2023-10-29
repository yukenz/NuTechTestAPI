package nutech.awan.ppob.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CustomDataSource {

    @Value("${database.url}")
    private String JDBCUrl;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Bean
    HikariDataSource hikariDataSource() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl(JDBCUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);


        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(Duration.ofMinutes(1).toMillis());
        hikariConfig.setMaxLifetime(Duration.ofMinutes(10).toMillis());

        return new HikariDataSource(hikariConfig);
    }

}

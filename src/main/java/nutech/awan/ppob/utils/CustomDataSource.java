package nutech.awan.ppob.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CustomDataSource {

    @Bean
    HikariDataSource hikariDataSource() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mariadb://localhost:3306/nutech");
        hikariConfig.setUsername("yukenz");
        hikariConfig.setPassword("awan");


        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(Duration.ofMinutes(1).toMillis());
        hikariConfig.setMaxLifetime(Duration.ofMinutes(10).toMillis());

        return new HikariDataSource(hikariConfig);
    }

}

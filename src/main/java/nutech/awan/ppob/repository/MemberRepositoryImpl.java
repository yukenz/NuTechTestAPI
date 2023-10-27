package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Optional;

@Component
public class MemberRepositoryImpl implements MemberRepository {


    @Autowired
    HikariDataSource dataSource;

    @Override
    public Optional<Member> findById(String email) {
        return Optional.empty();
    }

    @Override
    public void save(Member member) throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        String.format("insert into %s (email, first_name, last_name, password) values (?, ?, ?, ?);", MemberRepository.TABLENAME)
                );
        ) {

            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setString(2, member.getFirstName());
            preparedStatement.setString(3, member.getLastName());
            preparedStatement.setString(4, member.getPassword());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw e;
        }

    }

    @Override
    public boolean isExist(String email) {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(String.format(
                        "select email from %s where email = ?",
                        MemberRepository.TABLENAME)
                )
        ) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void flush() {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement()
        ) {

            statement.execute(String.format("truncate table %s", MemberRepository.TABLENAME));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

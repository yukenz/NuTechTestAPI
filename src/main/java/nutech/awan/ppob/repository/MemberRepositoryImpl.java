package nutech.awan.ppob.repository;

import com.zaxxer.hikari.HikariDataSource;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.Optional;

@Component
public class MemberRepositoryImpl implements MemberRepository {

    @Autowired
    private HikariDataSource dataSource;

    @Override
    public Optional<Member> findById(String email) {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        String.format("select * from %s where email = ?;", MemberRepository.TABLENAME)
                );
        ) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            //Kosong
            if (!resultSet.first()) {
                return Optional.empty();
            }

            Member member = Member.builder()
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .profileImage(resultSet.getString("profile_image"))
                    .balance(resultSet.getLong("balance"))
                    .build();

            //Isi
            return Optional.of(member);
        } catch (SQLException e) {
            e.printStackTrace();
            //Err
            return Optional.empty();
        }

    }

    @Override
    public void save(Member member) throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        String.format("insert into %s (email, first_name, last_name, password, profile_image, balance) values (?, ?, ?, ?, ?, ?);", MemberRepository.TABLENAME)
                );
        ) {

            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setString(2, member.getFirstName());
            preparedStatement.setString(3, member.getLastName());
            preparedStatement.setString(4, member.getPassword());
            preparedStatement.setString(5, member.getProfileImage());
            preparedStatement.setLong(6, member.getBalance());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw e;
        }

    }

    @Override
    public void update(Member member) throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        String.format("update %s set first_name = ?, last_name  = ?, password = ?, profile_image = ?, balance = ? where email = ?;", MemberRepository.TABLENAME)
                );
        ) {

            preparedStatement.setString(1, member.getFirstName());
            preparedStatement.setString(2, member.getLastName());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.setString(4, member.getProfileImage());
            preparedStatement.setLong(5, member.getBalance());
            preparedStatement.setString(6, member.getEmail());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public boolean isIdExist(String email) {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(String.format(
                        "select email from %s where email = ?",
                        MemberRepository.TABLENAME)
                )
        ) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.first();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Long updateBalanceById(String email, Long amountAdd) throws SQLException {

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(
                        String.format("update %s set balance = ? where email = ?;", MemberRepository.TABLENAME)
                );
                PreparedStatement queryStatement = connection.prepareStatement(
                        String.format("select balance from %s where email = ? for update;", MemberRepository.TABLENAME)
                )
        ) {
            //Transaction
            connection.setAutoCommit(false);


            queryStatement.setString(1, email);
            ResultSet resultSet = queryStatement.executeQuery();
            //result kosong
            if (!resultSet.next()) {
                throw new ResponseStatusException(
                        HttpStatus.EXPECTATION_FAILED,
                        "Email untuk update balance tidak ditemukan"
                );
            }

            Long balanceAfter = resultSet.getLong("balance") + amountAdd;

            //Update
            updateStatement.setLong(1, balanceAfter);
            updateStatement.setString(2, email);
            updateStatement.execute();

            //Commit / Rollback mechanism
            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

            return balanceAfter;

        } catch (SQLException e) {
            throw e;
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

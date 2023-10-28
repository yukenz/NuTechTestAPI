package nutech.awan.ppob.repository.interfaces;

import nutech.awan.ppob.model.entity.Member;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;

@Repository
public interface MemberRepository {

    String TABLENAME = "members";

    Optional<Member> findById(String email);

    void save(Member member) throws SQLException;

    void update(Member member) throws SQLException;

    boolean isIdExist(String email);

    void flush();

}

package nutech.awan.ppob.repository.interfaces;

import nutech.awan.ppob.model.entity.ServicePayment;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface ServicePaymentRepository {

    String TABLENAME = "services";


    List<ServicePayment> findAll() throws SQLException;

    Optional<ServicePayment> findById(String code);
}

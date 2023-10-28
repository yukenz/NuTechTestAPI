package nutech.awan.ppob.repository.interfaces;

import nutech.awan.ppob.model.response.ListServiceResponse;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface ServicePaymentRepository {

    String TABLENAME = "services";


    List<ListServiceResponse> findAll() throws SQLException;
}

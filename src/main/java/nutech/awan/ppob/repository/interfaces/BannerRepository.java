package nutech.awan.ppob.repository.interfaces;

import nutech.awan.ppob.model.entity.Banner;
import nutech.awan.ppob.model.response.ListBannerResponse;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface BannerRepository {

    String TABLENAME = "banners";


    List<Banner> findAll() throws SQLException;
}

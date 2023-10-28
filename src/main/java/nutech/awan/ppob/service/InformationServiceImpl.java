package nutech.awan.ppob.service;

import nutech.awan.ppob.model.response.ListBannerResponse;
import nutech.awan.ppob.model.response.ListServiceResponse;
import nutech.awan.ppob.repository.interfaces.BannerRepository;
import nutech.awan.ppob.service.interfaces.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@Component
public class InformationServiceImpl implements InformationService {

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public List<ListBannerResponse> banner() {

        try {
            return bannerRepository.findAll();
        } catch (SQLException ex) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getMessage());
        }

    }

    @Override
    public List<ListServiceResponse> service() {
        return null;
    }
}

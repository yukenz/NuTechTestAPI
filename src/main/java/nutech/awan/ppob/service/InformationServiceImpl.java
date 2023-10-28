package nutech.awan.ppob.service;

import nutech.awan.ppob.model.entity.Banner;
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

            List<Banner> banners = bannerRepository.findAll();

            //Transform to response
            return banners.stream().map(banner -> ListBannerResponse.builder()
                    .banner_name(banner.getName())
                    .banner_image(banner.getImage_url())
                    .description(banner.getDescription())
                    .build()).toList();

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

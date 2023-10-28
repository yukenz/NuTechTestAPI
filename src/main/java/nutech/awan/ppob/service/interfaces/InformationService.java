package nutech.awan.ppob.service.interfaces;

import nutech.awan.ppob.model.response.ListBannerResponse;
import nutech.awan.ppob.model.response.ListServiceResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InformationService {

    List<ListBannerResponse> banner();

    List<ListServiceResponse> service();

}

package nutech.awan.ppob.controller;

import nutech.awan.ppob.controller.interfaces.InformationController;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.response.ListBannerResponse;
import nutech.awan.ppob.model.response.ListServiceResponse;
import nutech.awan.ppob.model.response.WebResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InformationControllerImpl implements InformationController {
    @Override
    public WebResponse<List<ListBannerResponse>> banner() {
        return null;
    }

    @Override
    public WebResponse<List<ListServiceResponse>> service(Member member) {
        return null;
    }
}

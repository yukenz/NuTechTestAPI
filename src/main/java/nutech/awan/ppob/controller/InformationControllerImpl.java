package nutech.awan.ppob.controller;

import nutech.awan.ppob.controller.interfaces.InformationController;
import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.response.ListBannerResponse;
import nutech.awan.ppob.model.response.ListServiceResponse;
import nutech.awan.ppob.model.response.WebResponse;
import nutech.awan.ppob.service.interfaces.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class InformationControllerImpl implements InformationController {

    @Autowired
    InformationService informationService;
    @Autowired
    private MessageSource messageSource;

    @Override
    public WebResponse<List<ListBannerResponse>> banner() {
        List<ListBannerResponse> banner = informationService.banner();

        return WebResponse.<List<ListBannerResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(messageSource.getMessage("success", null, Locale.of("id", "ID")))
                .data(banner)
                .build();
    }

    @Override
    public WebResponse<List<ListServiceResponse>> service(Member member) {
        return null;
    }
}

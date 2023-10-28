package nutech.awan.ppob.controller.interfaces;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.model.response.ListBannerResponse;
import nutech.awan.ppob.model.response.ListServiceResponse;
import nutech.awan.ppob.model.response.WebResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface InformationController {

    /* GET: banner */
    @GetMapping(
            path = "/banner",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<List<ListBannerResponse>> banner();

    /* GET: service | TOKEN */
    @GetMapping(
            path = "/services",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    WebResponse<List<ListServiceResponse>> service(Member member);

}

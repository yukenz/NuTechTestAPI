package nutech.awan.ppob.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebResponse<T> {

    private Integer status;
    private String message;
    private T data;

}

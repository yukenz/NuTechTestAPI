package nutech.awan.ppob.advice;

import nutech.awan.ppob.model.response.WebResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ResponseStatusAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<WebResponse<String>> responseStatusException(ResponseStatusException ex) {

        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(ex.getStatusCode().value())
                .message(ex.getReason())
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(webResponse);
    }

}

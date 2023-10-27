package nutech.awan.ppob.advice;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.RequiredTypeException;
import nutech.awan.ppob.model.response.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class JWTAdvice {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler({MalformedJwtException.class, ExpiredJwtException.class, RequiredTypeException.class})
    ResponseEntity<WebResponse<String>> jwtHandler() {

        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        String message = messageSource.getMessage("token_error", null, Locale.of("id", "ID"));

        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(unauthorized.value())
                .message(message)
                .build();

        return ResponseEntity.status(unauthorized).body(webResponse);
    }


}

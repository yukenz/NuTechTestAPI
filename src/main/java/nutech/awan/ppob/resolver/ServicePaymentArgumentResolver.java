package nutech.awan.ppob.resolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import nutech.awan.ppob.model.entity.ServicePayment;
import nutech.awan.ppob.model.request.TransactionRequest;
import nutech.awan.ppob.repository.interfaces.ServicePaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

@Component
public class ServicePaymentArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MessageSource messageSource;

    @Autowired
    ServicePaymentRepository servicePaymentRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ServicePayment.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        //get request body and mapping
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String content = new String(request.getInputStream().readAllBytes());
        TransactionRequest transactionRequest = objectMapper.readValue(content, new TypeReference<TransactionRequest>() {
        });


        ServicePayment servicePayment = servicePaymentRepository
                .findById(transactionRequest.getServiceCode())
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                messageSource.getMessage("transaction_invalid", null, Locale.of("id", "ID"))
                        )
                );

        return servicePayment;
    }
}

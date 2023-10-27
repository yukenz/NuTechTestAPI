package nutech.awan.ppob.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationService {

    @Autowired
    private Validator validator;

    public void validateObject(Object object) {

        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        //Ada Violasi
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

    }

}

package nl.app.loopsnelheid.account.presentation;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import nl.app.loopsnelheid.account.application.SecurityResponseService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class AccountExceptionController
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { DataIntegrityViolationException.class, InvalidFormatException.class })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {

        return SecurityResponseService.generateResponse(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        return SecurityResponseService.generateResponse(details.toString(), HttpStatus.BAD_REQUEST);
    }
}

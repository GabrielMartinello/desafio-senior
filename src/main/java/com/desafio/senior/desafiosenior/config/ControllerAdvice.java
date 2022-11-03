package com.desafio.senior.desafiosenior.config;

import com.desafio.senior.desafiosenior.dto.form.ErroForm;
import com.desafio.senior.desafiosenior.exeption.ItemInativoException;
import com.desafio.senior.desafiosenior.exeption.RegisterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(RegisterNotFoundException.class)
    public ResponseEntity<Object> handleResultNotFoundException(RegisterNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemInativoException.class)
    public ResponseEntity<Object> handleResultItemInativoException(ItemInativoException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleResultItemInativoException(RuntimeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());

        List<ErroForm> erroDto = new ArrayList<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(erro -> {
                    String message = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
                    ErroForm erroForm = new ErroForm(erro.getField(), message);
                    erroDto.add(erroForm);
                });

        body.put("campo", erroDto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}

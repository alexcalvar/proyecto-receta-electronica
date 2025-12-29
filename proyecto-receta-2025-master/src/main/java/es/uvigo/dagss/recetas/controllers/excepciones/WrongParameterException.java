package es.uvigo.dagss.recetas.controllers.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String message) {
        super(message);
    }
}
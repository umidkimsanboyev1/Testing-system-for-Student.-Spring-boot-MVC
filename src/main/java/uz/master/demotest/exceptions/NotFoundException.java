package uz.master.demotest.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException{
    private HttpStatus status;

    public NotFoundException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public NotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

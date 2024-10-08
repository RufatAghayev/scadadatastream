package az.azerenerji.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Invalid Token")
public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException(){
        super("Invalid Token");
    }
}

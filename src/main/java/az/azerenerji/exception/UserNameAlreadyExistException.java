package az.azerenerji.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Password confirmation is not same")
public class UserNameAlreadyExistException extends RuntimeException {
    public UserNameAlreadyExistException(){
        super("Password confirmation is not same");
    }
}

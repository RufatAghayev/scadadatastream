package az.azerenerji.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "These credentials do not match our records.")
public class UserNameNotFoundException extends RuntimeException{
    public UserNameNotFoundException() {
        super("These credentials do not match our records.");
    }
}

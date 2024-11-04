package az.azerenerji.dto.response;

import lombok.Getter;

@Getter
public class ValidationError {
    private String field;
    private String message;


}

package az.azerenerji.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponseDto {
    long id;

    public LoginResponseDto(Long id){
        this.id=id;
    }
    String username;

}

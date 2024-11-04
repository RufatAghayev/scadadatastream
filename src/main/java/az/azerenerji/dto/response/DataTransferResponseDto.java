package az.azerenerji.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataTransferResponseDto {
    long id;
    String data_Id_1;
    String data_Id_2;
    LocalDateTime createdTime;

}

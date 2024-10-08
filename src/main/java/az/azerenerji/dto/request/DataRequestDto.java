package az.azerenerji.dto.request;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataRequestDto {
    String data_Id_1;
    String data_Id_2;
    String createdTime;
}

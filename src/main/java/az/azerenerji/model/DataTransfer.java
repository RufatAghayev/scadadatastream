package az.azerenerji.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String data_Id_1;
    String data_Id_2;
    @Column(name = "createdTimee",columnDefinition = "TIMESTAMP")
    LocalDateTime createdTime= LocalDateTime.now();
    LocalDateTime updateTime;
}

package az.azerenerji.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long size;

    @Lob
    private byte[] data;


}

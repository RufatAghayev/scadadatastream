package az.azerenerji.dto.response;

import lombok.Data;

@Data
public class FileDownloadResponseDto {
    private Long id;
    private String fileName;
    private String fileDownloadUri;  // Faylı əldə etmək üçün URL
    private byte[] fileData;         // Faylın kontenti (müştəriyə lazım olarsa)


    public FileDownloadResponseDto(Long id, String fileName, String fileDownloadUri, byte[] fileData) {
        this.id = id;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileData = fileData;
    }
}

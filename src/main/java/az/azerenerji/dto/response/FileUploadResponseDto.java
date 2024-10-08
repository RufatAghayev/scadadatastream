package az.azerenerji.dto.response;

import lombok.Data;

@Data
public class FileUploadResponseDto {
    private Long id;
    private String fileName;
    private String fileDownloadUri;
    private String message;

    public FileUploadResponseDto(Long id, String fileName, String fileDownloadUri, String message) {
        this.id = id;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.message = message;
    }
}

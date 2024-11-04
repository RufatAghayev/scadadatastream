package az.azerenerji.dto.response;

import lombok.Data;

@Data
public class FileDownloadResponseDto {
    private Long id;
    private String fileName;
    private String fileDownloadUri;
    private byte[] fileData;


    public FileDownloadResponseDto(Long id, String fileName, String fileDownloadUri, byte[] fileData) {
        this.id = id;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileData = fileData;
    }
}

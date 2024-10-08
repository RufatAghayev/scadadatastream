package az.azerenerji.controller;

import az.azerenerji.dto.response.FileDownloadResponseDto;
import az.azerenerji.dto.response.FileUploadResponseDto;
import az.azerenerji.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import az.azerenerji.model.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @Operation(summary = "save", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<FileUploadResponseDto> uploadFile(@RequestPart("file") MultipartFile file) {
        try {
            File savedFile = fileService.saveFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")
                    .path(savedFile.getId().toString())
                    .toUriString();

            FileUploadResponseDto responseDto = new FileUploadResponseDto(
                    savedFile.getId(),
                    savedFile.getFileName(),
                    fileDownloadUri,
                    "Fayl uğurla yükləndi!"
            );
            return ResponseEntity.ok(responseDto);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new FileUploadResponseDto(null, null, null, "Fayl yüklənmədi"));
        }
    }
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<List<FileDownloadResponseDto>> getAllFiles() {
        List<File> fileEntities = fileService.findAll();

        List<FileDownloadResponseDto> responseDtos = fileEntities.stream()
                .map(fileEntity -> new FileDownloadResponseDto(
                        fileEntity.getId(),
                        fileEntity.getFileName(),
                        "/files/download/" + fileEntity.getId(),
                        null
                ))
                .collect(Collectors.toList());
        if (responseDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseDtos);
    }
    @GetMapping("/download/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @Operation(summary = "download", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {


        File fileEntity = fileService.getFile(id);
        byte[] fileData = fileService.downloadFile(id);


        String fileType = fileEntity.getFileName().substring(fileEntity.getFileName().lastIndexOf(".") + 1).toLowerCase();
        MediaType contentType;

        switch (fileType) {
            case "jpg":
            case "jpeg":
                contentType = MediaType.IMAGE_JPEG;
                break;
            case "png":
                contentType = MediaType.IMAGE_PNG;
                break;
            case "gif":
                contentType = MediaType.IMAGE_GIF;
                break;
            case "pdf":
            case "doc":
            case "docx":
            case "xls":
            case "xlsx":
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")

                        .header("File-Name", fileEntity.getFileName())
                        .header("File-Download-Uri", "/download/" + id)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(fileData);
            default:
                contentType = MediaType.APPLICATION_OCTET_STREAM;
                break;
        }


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileEntity.getFileName() + "\"")
                // Burada da əlavə məlumatları başlığa əlavə edirik
                .header("File-Name", fileEntity.getFileName())
                .header("File-Download-Uri", "/download/" + id)
                .contentType(contentType)
                .body(fileData);
    }

    @DeleteMapping(path = "/file/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByFile(@PathVariable long id){
        fileService.deleteByFile(id);
    }
}
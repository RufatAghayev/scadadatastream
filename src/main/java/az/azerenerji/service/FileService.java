package az.azerenerji.service;

import az.azerenerji.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import az.azerenerji.model.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    @Autowired
    private  FileRepository fileRepository;

    @Transactional
    public File saveFile(MultipartFile file) throws IOException {
        File fileEntity = new File();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setSize(file.getSize());
        fileEntity.setData(file.getBytes());

        return fileRepository.save(fileEntity);
    }

    public File getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fayl tapılmadı id ilə: " + id));
    }
    public List<File> findAll() {
        return fileRepository.findAll();
    }

    public byte[] downloadFile(Long id) {
        File fileEntity = getFile(id);
        return fileEntity.getData();
    }
    public void deleteByFile(Long id){
       fileRepository.deleteById(id);
    }
}

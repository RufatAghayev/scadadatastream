package az.azerenerji.service.impl;

import az.azerenerji.dto.request.DataRequestDto;
import az.azerenerji.dto.response.DataTransferResponseDto;
import az.azerenerji.mapper.DataTransferMapper;
import az.azerenerji.model.DataTransfer;
import az.azerenerji.repository.DataTransferRepository;
import az.azerenerji.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final DataTransferRepository dataTransferRepository;
    private final DataTransferMapper dataTransferMapper;

    @Override
    public void deleteById(long id) {
        dataTransferRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DataTransferResponseDto updateDate(DataRequestDto dataRequestDto, long id) {
        DataTransfer dataTransfer1 = dataTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found"));


        LocalTime updateTime = LocalTime.parse(dataRequestDto.getCreatedTime(), DateTimeFormatter.ofPattern("HH:mm"));
        dataTransfer1.setUpdateTime(updateTime);
        dataTransferMapper.dataTransferUpdate(dataRequestDto, dataTransfer1);
        DataTransfer update = dataTransferRepository.save(dataTransfer1);
        return dataTransferMapper.map(update);
    }

    public List<DataTransferResponseDto> findAll() {
        List<DataTransfer> all = dataTransferRepository.findAll();
        List<DataTransferResponseDto> map = dataTransferMapper.map(all);
        return map;
    }

    @Override
    @Transactional
    public DataTransferResponseDto dataSave(DataRequestDto dataRequestDto) {
        DataTransfer dataTransfer = dataTransferMapper.map(dataRequestDto);

        // `createdTime` təyin edin
        LocalTime createdTime = LocalTime.parse(dataRequestDto.getCreatedTime(), DateTimeFormatter.ofPattern("HH:mm"));
        dataTransfer.setCreatedTime(createdTime);

        // Boş və ya null olub olmadığını yoxlayın
        if (dataRequestDto.getData_Id_1() == null || dataRequestDto.getData_Id_1().isEmpty()) {
            throw new IllegalArgumentException("Data_Id_1 cannot be null or empty");
        }

        if (dataRequestDto.getData_Id_2() == null || dataRequestDto.getData_Id_2().isEmpty()) {
            throw new IllegalArgumentException("Data_Id_2 cannot be null or empty");
        }

        // Yeni DataTransfer obyektini yaddaşa yazın
        DataTransfer savedDataTransfer = dataTransferRepository.save(dataTransfer);

        // Saxlanılmış məlumatları cavab olaraq qaytarın
        return dataTransferMapper.map(savedDataTransfer);


    }
}







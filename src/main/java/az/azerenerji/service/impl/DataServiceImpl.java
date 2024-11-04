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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

    private final DataTransferRepository dataTransferRepository;
    private final DataTransferMapper dataTransferMapper;

    @Override
    public List<DataTransferResponseDto> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        List<DataTransfer> data = dataTransferRepository.findByDateRange(startDate, endDate);
        if (data == null || data.isEmpty()) {
            return null;
        }

        List<DataTransferResponseDto> map = dataTransferMapper.map(data);
        return map;
    }

    @Override
    public void deleteById(long id) {
        dataTransferRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DataTransferResponseDto updateDate(DataRequestDto dataRequestDto, long id) {
        DataTransfer dataTransfer1 = dataTransferRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found"));


        LocalDateTime updateTime = LocalDateTime.parse(dataRequestDto.getCreatedTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
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


        LocalDateTime createdDateTime;
        try {

            createdDateTime = LocalDateTime.parse(dataRequestDto.getCreatedTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException("CreatedTime format is invalid. Please use yyyy-MM-dd'T'HH:mm:ss format.");
        }


        dataTransfer.setCreatedTime(createdDateTime);


        if (dataRequestDto.getData_Id_1() == null || dataRequestDto.getData_Id_1().isEmpty()) {
            throw new IllegalArgumentException("Data_Id_1 cannot be null or empty");
        }

        if (dataRequestDto.getData_Id_2() == null || dataRequestDto.getData_Id_2().isEmpty()) {
            throw new IllegalArgumentException("Data_Id_2 cannot be null or empty");
        }


        DataTransfer savedDataTransfer = dataTransferRepository.save(dataTransfer);


        return dataTransferMapper.map(savedDataTransfer);

    }
}







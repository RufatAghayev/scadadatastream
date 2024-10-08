package az.azerenerji.service;

import az.azerenerji.dto.request.DataRequestDto;
import az.azerenerji.dto.response.DataTransferResponseDto;

import java.util.List;

public interface DataService {
    void deleteById(long id);
    DataTransferResponseDto updateDate(DataRequestDto dataRequestDto, long id);
    List<DataTransferResponseDto> findAll();
    DataTransferResponseDto dataSave(DataRequestDto dataRequestDto);

}

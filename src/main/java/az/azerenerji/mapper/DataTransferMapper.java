package az.azerenerji.mapper;

import az.azerenerji.dto.request.DataRequestDto;

import az.azerenerji.dto.response.DataTransferResponseDto;
import az.azerenerji.model.DataTransfer;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring",builder = @Builder(disableBuilder = true),imports = {Object.class, LocalTime.class})
public abstract class DataTransferMapper {

    public abstract DataTransferResponseDto map(DataTransfer dataTransfer);

    public abstract List<DataTransferResponseDto> map(List<DataTransfer> dataTransfers);

    public abstract DataTransfer map(DataRequestDto dataRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "data_Id_1", target = "data_Id_1")
    @Mapping(source = "data_Id_2", target = "data_Id_2")
    public abstract void dataTransferUpdate(DataRequestDto dataRequestDto, @MappingTarget DataTransfer dataTransfer);
}

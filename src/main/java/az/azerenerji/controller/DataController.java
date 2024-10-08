package az.azerenerji.controller;

import az.azerenerji.dto.request.DataRequestDto;
import az.azerenerji.dto.response.DataTransferResponseDto;
import az.azerenerji.repository.DataTransferRepository;
import az.azerenerji.service.impl.DataServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/datatransfer")
@RequiredArgsConstructor
public class DataController {
    private final DataTransferRepository dataTransferRepository;
    private final DataServiceImpl dataService;


    @GetMapping
    @Operation(security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    public ResponseEntity<List<DataTransferResponseDto>> findAll() {
        return ResponseEntity.ok(dataService.findAll());

    }

    @PostMapping(path = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @Operation(summary = "save", security = @SecurityRequirement(name = "bearerAuth"))
    public DataTransferResponseDto dataSave(@RequestBody DataRequestDto requestDto) {
        return dataService.dataSave(requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @Operation(summary = "deleteById", security = @SecurityRequirement(name = "bearerAuth"))
    public void deleteById(@PathVariable long id) {
        dataTransferRepository.deleteById(id);

    }

    @PutMapping(path = "/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    @Operation(summary = "deleteById", security = @SecurityRequirement(name = "bearerAuth"))
    public DataTransferResponseDto updateData(@PathVariable long id, @RequestBody DataRequestDto dataRequestDto){
        return dataService.updateDate(dataRequestDto,id);


    }
}
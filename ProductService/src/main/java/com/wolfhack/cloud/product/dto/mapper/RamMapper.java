package com.wolfhack.cloud.product.dto.mapper;

import com.wolfhack.cloud.product.dto.RamFullDTO;
import com.wolfhack.cloud.product.dto.RamResponseDTO;
import com.wolfhack.cloud.product.model.Ram;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RamMapper {
    RamResponseDTO toRamResponseDTO(Ram ram);
    Ram toRam(RamFullDTO dto);

    List<RamResponseDTO> toRamResponseListDTO(List<Ram> rams);
    List<Ram> toRamList(List<RamFullDTO> dtos);
}

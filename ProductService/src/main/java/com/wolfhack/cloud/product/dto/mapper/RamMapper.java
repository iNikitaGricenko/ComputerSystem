package com.wolfhack.cloud.product.dto.mapper;

import com.wolfhack.cloud.product.dto.RamFullDTO;
import com.wolfhack.cloud.product.dto.RamResponseDTO;
import com.wolfhack.cloud.product.model.Ram;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RamMapper {
    RamResponseDTO toRamResponseDTO(Ram ram);
    Ram toRam(RamFullDTO dto);

    List<RamResponseDTO> toRamResponseListDTO(List<Ram> rams);
    List<Ram> toRamList(List<RamFullDTO> dtos);
}

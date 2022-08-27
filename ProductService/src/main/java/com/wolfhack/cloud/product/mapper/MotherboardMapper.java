package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.dto.MotherboardFullDTO;
import com.wolfhack.cloud.product.model.dto.MotherboardResponseDTO;
import com.wolfhack.cloud.product.model.Motherboard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MotherboardMapper {
    MotherboardResponseDTO toMotherboardResponseDTO(Motherboard motherboard);
    Motherboard toMotherboard(MotherboardFullDTO dto);

    List<MotherboardResponseDTO> toMotherboardResponseListDTO(List<Motherboard> motherboards);
    List<Motherboard> toMotherboardList(List<MotherboardFullDTO> dtos);
}

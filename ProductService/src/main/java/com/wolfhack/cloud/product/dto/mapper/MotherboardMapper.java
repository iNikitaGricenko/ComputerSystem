package com.wolfhack.cloud.product.dto.mapper;

import com.wolfhack.cloud.product.dto.MotherboardFullDTO;
import com.wolfhack.cloud.product.dto.MotherboardResponseDTO;
import com.wolfhack.cloud.product.model.Motherboard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MotherboardMapper {
    MotherboardResponseDTO toMotherboardResponseDTO(Motherboard motherboard);
    Motherboard toMotherboard(MotherboardFullDTO dto);

    List<MotherboardResponseDTO> toMotherboardResponseListDTO(List<Motherboard> motherboards);
    List<Motherboard> toMotherboardList(List<MotherboardFullDTO> dtos);
}

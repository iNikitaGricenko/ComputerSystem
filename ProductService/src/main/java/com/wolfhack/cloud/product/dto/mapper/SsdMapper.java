package com.wolfhack.cloud.product.dto.mapper;

import com.wolfhack.cloud.product.dto.SsdFullDTO;
import com.wolfhack.cloud.product.dto.SsdResponseDTO;
import com.wolfhack.cloud.product.model.Ssd;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SsdMapper {
    SsdResponseDTO toSsdResponseDTO(Ssd ssd);
    Ssd toSsd(SsdFullDTO dto);

    List<SsdResponseDTO> toSsdResponseListDTO(List<Ssd> ssds);
    List<Ssd> toSsdList(List<SsdFullDTO> dtos);
}

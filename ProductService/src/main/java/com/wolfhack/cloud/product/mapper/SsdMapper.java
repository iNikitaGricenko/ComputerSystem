package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Ssd;
import com.wolfhack.cloud.product.model.dto.SsdFullDTO;
import com.wolfhack.cloud.product.model.dto.SsdResponseDTO;
import com.wolfhack.cloud.product.model.search.SsdSearch;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SsdMapper {
	SsdResponseDTO toSsdResponseDTO(Ssd ssd);

	Ssd toSsd(SsdFullDTO dto);

	List<SsdResponseDTO> toSsdResponseListDTO(List<Ssd> ssds);

	List<Ssd> toSsdList(List<SsdFullDTO> dtos);

	Ssd toEntity(SsdSearch ssdSearch);

	SsdSearch toSearch(Ssd ssd);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	SsdSearch partialUpdate(@MappingTarget SsdSearch ssdSearch, Ssd ssd);
}

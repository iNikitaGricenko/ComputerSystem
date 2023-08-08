package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ram;
import com.wolfhack.cloud.product.model.search.RamSearch;
import com.wolfhack.cloud.product.model.dto.RamFullDTO;
import com.wolfhack.cloud.product.model.dto.RamResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RamMapper {
	RamResponseDTO toRamResponseDTO(Product<Ram> ram);

	RamResponseDTO toRamResponseDTO(Ram ram);

	Ram toRam(RamFullDTO dto);

	List<RamResponseDTO> toRamResponseListDTO(List<Ram> rams);

	List<Ram> toRamList(List<RamFullDTO> dtos);

	Ram toEntity(RamSearch ramSearch);

	RamSearch toSearch(Ram ram, long id);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	RamSearch partialUpdate(@MappingTarget RamSearch ramSearch, Ram ram);
}

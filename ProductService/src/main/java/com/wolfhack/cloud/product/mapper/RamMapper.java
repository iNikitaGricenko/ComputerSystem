package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ram;
import com.wolfhack.cloud.product.model.dto.RamFullDTO;
import com.wolfhack.cloud.product.model.dto.RamResponseDTO;
import com.wolfhack.cloud.product.model.search.RamSearch;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RamMapper {
	RamResponseDTO toRamResponseDTO(Product<Ram> ram);

	@Mapping(source = ".", target = "item")
	Product<Ram> toRam(RamFullDTO dto);

	List<RamResponseDTO> toRamResponseListDTO(List<Product<Ram>> rams);

	List<Product<Ram>> toRamList(List<RamFullDTO> dtos);

	Product<Ram> toEntity(RamSearch ramSearch);

	RamSearch toSearch(Product<Ram> ram);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	RamSearch partialUpdate(@MappingTarget RamSearch ramSearch, Product<Ram> ram);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Product<Ram> partialUpdate(@MappingTarget Product<Ram> toUpdate, Product<Ram> ram);
}

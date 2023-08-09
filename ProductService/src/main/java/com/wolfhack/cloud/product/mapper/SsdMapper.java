package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ssd;
import com.wolfhack.cloud.product.model.dto.SsdFullDTO;
import com.wolfhack.cloud.product.model.dto.SsdResponseDTO;
import com.wolfhack.cloud.product.model.search.SsdSearch;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SsdMapper {
	SsdResponseDTO toSsdResponseDTO(Product<Ssd> ssd);

	@Mapping(source = ".", target = "item")
	Product<Ssd> toSsd(SsdFullDTO dto);

	List<SsdResponseDTO> toSsdResponseListDTO(List<Product<Ssd>> ssds);

	List<Product<Ssd>> toSsdList(List<SsdFullDTO> dtos);

	Product<Ssd> toEntity(SsdSearch ssdSearch);

	@Mapping(source = "item", target = ".")
	SsdSearch toSearch(Product<Ssd> ssd);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	SsdSearch partialUpdate(@MappingTarget SsdSearch ssdSearch, Product<Ssd> ssd);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Product<Ssd> partialUpdate(@MappingTarget Product<Ssd> toUpdate, Product<Ssd> ssd);
}

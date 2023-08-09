package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.dto.MotherboardFullDTO;
import com.wolfhack.cloud.product.model.dto.MotherboardResponseDTO;
import com.wolfhack.cloud.product.model.search.MotherboardSearch;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MotherboardMapper {
	MotherboardResponseDTO toMotherboardResponseDTO(Product<Motherboard> motherboard);

	@Mapping(source = ".", target = "item")
	Product<Motherboard> toMotherboard(MotherboardFullDTO dto);

	List<MotherboardResponseDTO> toMotherboardResponseListDTO(List<Product<Motherboard>> motherboards);

	List<Product<Motherboard>> toMotherboardList(List<MotherboardFullDTO> dtos);

	Product<Motherboard> toEntity(MotherboardSearch motherboardSearch);

	@Mapping(source = "item", target = ".")
	MotherboardSearch toSearch(Product<Motherboard> motherboards);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	MotherboardSearch partialUpdate(@MappingTarget MotherboardSearch motherboardSearch, Product<Motherboard> motherboard);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Product<Motherboard> partialUpdate(@MappingTarget Product<Motherboard> toUpdate, Product<Motherboard> motherboard);
}

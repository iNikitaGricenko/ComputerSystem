package com.wolfhack.cloud.dto.mapper;

import com.wolfhack.cloud.dto.ProductRequestDTO;
import com.wolfhack.cloud.dto.ProductResponseDTO;
import com.wolfhack.cloud.model.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
    Product toProductFromRequest(ProductRequestDTO productRequestDTO);

    ProductRequestDTO toRequestDTO(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductRequestDTO(ProductRequestDTO productRequestDTO, @MappingTarget Product product);

    Product toProductFromResponse(ProductResponseDTO orderResponseDTO);

    ProductResponseDTO toResponseDTO(Product order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductResponseDTO(ProductResponseDTO orderResponseDTO, @MappingTarget Product order);
}

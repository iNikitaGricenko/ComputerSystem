package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.dto.GpuFullDTO;
import com.wolfhack.cloud.product.model.dto.GpuResponseDTO;
import com.wolfhack.cloud.product.model.search.GpuSearch;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GpuMapper {
	GpuResponseDTO toGpuResponseDTO(Product<Gpu> Cpu);

	@Mapping(source = ".", target = "item")
	Product<Gpu> toGpu(GpuFullDTO dto);

	List<GpuResponseDTO> toGpuResponseListDTO(List<Product<Gpu>> cpus);

	List<Product<Gpu>> toGpuList(List<GpuFullDTO> dtos);

	Product<Gpu> toEntity(GpuSearch gpuSearch);

	GpuSearch toSearch(Product<Gpu> gpu);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	GpuSearch partialUpdate(@MappingTarget GpuSearch gpuSearch, Product<Gpu> gpu);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Product<Gpu> partialUpdate(@MappingTarget Product<Gpu> toUpdate, Product<Gpu> gpu);
}

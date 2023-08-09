package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.dto.CpuFullDTO;
import com.wolfhack.cloud.product.model.dto.CpuResponseDTO;
import com.wolfhack.cloud.product.model.search.CpuSearch;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CpuMapper {
	CpuResponseDTO toCpuResponseDTO(Product<Cpu> cpu);

	@Mapping(source = ".", target = "item")
	Product<Cpu> toCpu(CpuFullDTO dto);

	List<CpuResponseDTO> toCpuResponseListDTO(List<Product<Cpu>> cpus);

	List<Product<Cpu>> toCpuList(List<CpuFullDTO> dtos);

	Product<Cpu> toEntity(CpuSearch cpuSearch);

	CpuSearch toSearchModel(Product<Cpu> cpu);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	CpuSearch partialUpdate(@MappingTarget CpuSearch toBeUpdated, Product<Cpu> cpu);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	Product<Cpu> partialUpdate(@MappingTarget Product<Cpu> toBeUpdated, Product<Cpu> cpu);
}

package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.search.CpuSearch;
import com.wolfhack.cloud.product.model.dto.CpuFullDTO;
import com.wolfhack.cloud.product.model.dto.CpuResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CpuMapper {
    CpuResponseDTO toCpuResponseDTO(Cpu cpu);

    Cpu toCpu(CpuFullDTO dto);

    List<CpuResponseDTO> toCpuResponseListDTO(List<Cpu> cpus);

    List<Cpu> toCpuList(List<CpuFullDTO> dtos);

    Cpu toEntity(CpuSearch cpuSearch);

    CpuSearch toSearchModel(Cpu cpu);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CpuSearch partialUpdate(@MappingTarget CpuSearch toBeUpdated,  Cpu cpu);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cpu partialUpdate(@MappingTarget Cpu toBeUpdated,  Cpu cpu);
}

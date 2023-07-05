package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.dto.CpuFullDTO;
import com.wolfhack.cloud.product.model.dto.CpuResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CpuMapper {
    CpuResponseDTO toCpuResponseDTO(Cpu cpu);
    Cpu toCpu(CpuFullDTO dto);

    List<CpuResponseDTO> toCpuResponseListDTO(List<Cpu> cpus);
    List<Cpu> toCpuList(List<CpuFullDTO> dtos);
}

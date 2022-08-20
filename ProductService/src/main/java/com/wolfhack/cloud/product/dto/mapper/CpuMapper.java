package com.wolfhack.cloud.product.dto.mapper;

import com.wolfhack.cloud.product.dto.CpuFullDTO;
import com.wolfhack.cloud.product.dto.CpuResponseDTO;
import com.wolfhack.cloud.product.model.Cpu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CpuMapper {
    CpuResponseDTO toCpuResponseDTO(Cpu cpu);
    Cpu toCpu(CpuFullDTO dto);

    List<CpuResponseDTO> toCpuResponseListDTO(List<Cpu> cpus);
    List<Cpu> toCpuList(List<CpuFullDTO> dtos);
}

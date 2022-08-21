package com.wolfhack.cloud.product.dto.mapper;

import com.wolfhack.cloud.product.dto.GpuFullDTO;
import com.wolfhack.cloud.product.dto.GpuResponseDTO;
import com.wolfhack.cloud.product.model.Gpu;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GpuMapper {
    GpuResponseDTO toGpuResponseDTO(Gpu Cpu);
    Gpu toGpu(GpuFullDTO dto);

    List<GpuResponseDTO> toGpuResponseListDTO(List<Gpu> cpus);
    List<Gpu> toGpuList(List<GpuFullDTO> dtos);
}

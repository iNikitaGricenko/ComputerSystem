package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.model.dto.GpuFullDTO;
import com.wolfhack.cloud.product.model.dto.GpuResponseDTO;
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

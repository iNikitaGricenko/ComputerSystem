package com.wolfhack.cloud.product.mapper;

import com.wolfhack.cloud.product.model.*;
import com.wolfhack.cloud.product.model.dto.ProductMiniDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {

    ProductMiniDTO toProductDTO(Cpu cpu);

    ProductMiniDTO toProductDTO(Gpu gpu);

    ProductMiniDTO toProductDTO(Ram ram);

    ProductMiniDTO toProductDTO(Ssd ssd);

    ProductMiniDTO toProductDTO(Motherboard motherboard);

    List<ProductMiniDTO> toProductListDTOFromCpu(List<Cpu> cpu);

    List<ProductMiniDTO> toProductListDTOFromGpu(List<Gpu> gpu);

    List<ProductMiniDTO> toProductListDTOFromRam(List<Ram> ram);

    List<ProductMiniDTO> toProductListDTOFromSsd(List<Ssd> ssd);

    List<ProductMiniDTO> toProductListDTOFromMotherboard(List<Motherboard> motherboard);
}

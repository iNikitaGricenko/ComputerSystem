package com.wolfhack.cloud.product.dto.mapper;

import com.wolfhack.cloud.product.dto.ProductMiniDTO;
import com.wolfhack.cloud.product.model.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
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

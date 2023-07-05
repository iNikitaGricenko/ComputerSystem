package com.wolfhack.cloud.product.service.search;

import com.wolfhack.cloud.product.model.Cpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CpuSearchServiceInterface {

	Long save(Cpu cpu);

	Page<Cpu> findByProductLine(String productLine, Pageable pageable);

	List<Cpu> findByAllFields(String line, Pageable pageable);

}

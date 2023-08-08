package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Cpu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CpuSearchServiceInterface {

	long save(Cpu cpu, long id);

	Page<Cpu> findByProductLine(String productLine, Pageable pageable);

	List<Cpu> findByTitle(String line, Pageable pageable);

	List<Cpu> findByAllTextFields(String line, Pageable pageable);

	List<Cpu> findByAllFields(String line, Pageable pageable);

	long update(Cpu cpu, long id);

	void delete(long cpuId);
}

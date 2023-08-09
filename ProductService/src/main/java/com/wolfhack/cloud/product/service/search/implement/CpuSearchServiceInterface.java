package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CpuSearchServiceInterface {

	long save(Product<Cpu> cpu);

	Page<Product<Cpu>> findByProductLine(String productLine, Pageable pageable);

	List<Product<Cpu>> findByTitle(String line, Pageable pageable);

	List<Product<Cpu>> findByAllTextFields(String line, Pageable pageable);

	List<Product<Cpu>> findByAllFields(String line, Pageable pageable);

	long update(Product<Cpu> cpu, long id);

	void delete(long cpuId);
}

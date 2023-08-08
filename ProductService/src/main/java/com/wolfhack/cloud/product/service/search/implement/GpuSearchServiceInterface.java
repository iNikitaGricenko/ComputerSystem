package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Gpu;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GpuSearchServiceInterface {

	long save(Gpu gpu);

	List<Gpu> findByTitle(String line, Pageable pageable);

	List<Gpu> findByAllTextFields(String line, Pageable pageable);

	List<Gpu> findByAllFields(String line, Pageable pageable);

	long update(Gpu gpu);

	void delete(long gpuId);

}

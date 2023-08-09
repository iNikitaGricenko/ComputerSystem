package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GpuSearchServiceInterface {

	long save(Product<Gpu> gpu);

	List<Product<Gpu>> findByTitle(String line, Pageable pageable);

	List<Product<Gpu>> findByAllTextFields(String line, Pageable pageable);

	List<Product<Gpu>> findByAllFields(String line, Pageable pageable);

	long update(Product<Gpu> gpu, long id);

	void delete(long gpuId);

}

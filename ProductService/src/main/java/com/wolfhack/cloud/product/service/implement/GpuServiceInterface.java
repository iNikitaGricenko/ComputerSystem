package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GpuServiceInterface {
	Page<Product<Gpu>> findAll(Pageable pageable);

	Long save(Product<Gpu> gpu);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	Product<Gpu> findById(Long id);

	List<Product<Gpu>> searchByTitle(String query, Pageable pageable);

	void delete(long id);

	long update(long id, Product<Gpu> gpu);
}
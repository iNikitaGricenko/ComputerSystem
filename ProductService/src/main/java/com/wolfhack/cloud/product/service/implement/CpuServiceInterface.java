package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CpuServiceInterface {
	Page<Product<Cpu>> findAll(Pageable pageable);

	Long save(Product<Cpu> cpu);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	Product<Cpu> findById(Long id);

	List<Product<Cpu>> searchByTitle(String query, Pageable pageable);

	void delete(long id);

	long update(long id, Product<Cpu> cpu);
}

package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MotherboardServiceInterface {
	Page<Product<Motherboard>> findAll(Pageable pageable);

	Product<Motherboard> findById(Long id);

	Long save(Product<Motherboard> motherboard);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	List<Product<Motherboard>> searchByTitle(String query, Pageable pageable);

	void delete(long id);

	long update(long id, Product<Motherboard> motherboard);
}
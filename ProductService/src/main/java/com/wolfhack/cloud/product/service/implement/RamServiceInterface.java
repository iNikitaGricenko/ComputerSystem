package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RamServiceInterface {
	Page<Product<Ram>> findAll(Pageable pageable);

	Long save(Product<Ram> ram);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	Product<Ram> findById(Long id);

	List<Product<Ram>> searchByTitle(String query, Pageable pageable);

	void delete(long id);

	long update(long id, Product<Ram> ram);
}

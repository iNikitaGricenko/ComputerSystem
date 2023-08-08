package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ssd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SsdServiceInterface {
	Page<Product<Ssd>> findAll(Pageable pageable);

	Long save(Ssd ssd);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	Product<Ssd> findById(Long id);

	List<Ssd> searchByTitle(String query, Pageable pageable);

	void delete(long id);

	long update(long id, Ssd ssd);
}

package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Ssd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SsdServiceInterface {
	Page<Ssd> findAll(Pageable pageable);

	Long save(Ssd ssd);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	Ssd findById(Long id);

	Page<Ssd> searchByQuery(String query, Pageable pageable);
}

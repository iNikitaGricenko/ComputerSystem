package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Motherboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MotherboardServiceInterface {
	Page<Motherboard> findAll(Pageable pageable);

	Motherboard findById(Long id);

	Long save(Motherboard motherboard);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	List<Motherboard> searchByTitle(String query, Pageable pageable);

	void delete(long id);

	long update(Motherboard motherboard);
}
package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Ram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RamServiceInterface {
	Page<Ram> findAll(Pageable pageable);

	Long save(Ram ram);

	String addPhoto(Long id, MultipartFile multipartFile) throws IOException;

	Ram findById(Long id);

	List<Ram> searchByTitle(String query, Pageable pageable);

	void delete(long id);

	long update(Ram ram);
}

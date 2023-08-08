package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Ram;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RamSearchServiceInterface {

	long save(Ram ram);

	List<Ram> findByTitle(String line, Pageable pageable);

	List<Ram> findByAllTextFields(String line, Pageable pageable);

	List<Ram> findByAllFields(String line, Pageable pageable);

	long update(Ram ram);

	void delete(long ramId);

}

package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Motherboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MotherboardSearchServiceInterface {

	long save(Motherboard motherboard);

	List<Motherboard> findByTitle(String line, Pageable pageable);

	List<Motherboard> findByAllTextFields(String line, Pageable pageable);

	List<Motherboard> findByAllFields(String line, Pageable pageable);

	long update(Motherboard motherboard);

	void delete(long motherboardId);

}

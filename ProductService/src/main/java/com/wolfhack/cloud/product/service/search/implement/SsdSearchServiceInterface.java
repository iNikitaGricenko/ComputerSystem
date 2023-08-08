package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Ssd;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SsdSearchServiceInterface {

	long save(Ssd ssd);

	List<Ssd> findByTitle(String line, Pageable pageable);

	List<Ssd> findByAllTextFields(String line, Pageable pageable);

	List<Ssd> findByAllFields(String line, Pageable pageable);

	long update(Ssd ssd);

	void delete(long ssdId);

}

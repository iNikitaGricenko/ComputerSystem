package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ssd;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SsdSearchServiceInterface {

	long save(Product<Ssd> ssd);

	List<Product<Ssd>> findByTitle(String line, Pageable pageable);

	List<Product<Ssd>> findByAllTextFields(String line, Pageable pageable);

	List<Product<Ssd>> findByAllFields(String line, Pageable pageable);

	long update(Product<Ssd> ssd, long id);

	void delete(long ssdId);

}

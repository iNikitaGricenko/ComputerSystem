package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.model.Ram;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RamSearchServiceInterface {

	long save(Product<Ram> ram);

	List<Product<Ram>> findByTitle(String line, Pageable pageable);

	List<Product<Ram>> findByAllTextFields(String line, Pageable pageable);

	List<Product<Ram>> findByAllFields(String line, Pageable pageable);

	long update(Product<Ram> ram, long id);

	void delete(long ramId);

}

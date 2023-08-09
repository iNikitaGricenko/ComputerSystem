package com.wolfhack.cloud.product.service.search.implement;

import com.wolfhack.cloud.product.model.Motherboard;
import com.wolfhack.cloud.product.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MotherboardSearchServiceInterface {

	long save(Product<Motherboard> motherboard);

	List<Product<Motherboard>> findByTitle(String line, Pageable pageable);

	List<Product<Motherboard>> findByAllTextFields(String line, Pageable pageable);

	List<Product<Motherboard>> findByAllFields(String line, Pageable pageable);

	long update(Product<Motherboard> motherboard, long id);

	void delete(long motherboardId);

}

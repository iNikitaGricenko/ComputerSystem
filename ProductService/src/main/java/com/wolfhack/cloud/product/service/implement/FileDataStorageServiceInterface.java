package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FileDataStorageServiceInterface {

	String save(Storage storage);

	List<String> save(List<Storage> storage);

	void delete(String id);

	void delete(List<String> ids);

	Page<Storage> getAll(Pageable pageable);

	List<Storage> getAll(List<String> ids);

	Storage get(String id);

}

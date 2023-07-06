package com.wolfhack.cloud.product.service.implement;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageServiceInterface {

	String upload(MultipartFile file) throws IOException;

	void delete(String fileId);

}

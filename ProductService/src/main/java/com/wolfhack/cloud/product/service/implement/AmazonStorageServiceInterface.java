package com.wolfhack.cloud.product.service.implement;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.wolfhack.cloud.product.model.Storage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AmazonStorageServiceInterface extends StorageServiceInterface {

	S3ObjectInputStream download(String fileId);

	void delete(List<String> fileIds);

}

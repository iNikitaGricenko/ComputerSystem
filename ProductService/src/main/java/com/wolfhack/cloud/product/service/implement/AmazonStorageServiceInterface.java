package com.wolfhack.cloud.product.service.implement;

import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.util.List;

public interface AmazonStorageServiceInterface extends StorageServiceInterface {

	S3ObjectInputStream download(String fileId);

	void delete(List<String> fileIds);

}

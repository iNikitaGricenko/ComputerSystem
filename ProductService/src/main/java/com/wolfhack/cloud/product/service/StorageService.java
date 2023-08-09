package com.wolfhack.cloud.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.wolfhack.cloud.product.model.FileStorage;
import com.wolfhack.cloud.product.service.implement.AmazonStorageServiceInterface;
import com.wolfhack.cloud.product.service.implement.FileDataStorageServiceInterface;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StorageService implements AmazonStorageServiceInterface {

	private final AmazonS3 amazonS3Client;

	private final FileDataStorageServiceInterface fileDataStorage;

	@Value("${AWS_STORAGE_BUCKET_NAME}") private String bucket;

	private static final String DEFAULT_STORAGE_KEY = "picture/";

	@Override
	public String upload(MultipartFile file) throws IOException {
		PutObjectRequest request = convertFileToPutRequest(file);

		amazonS3Client.putObject(request);
		String pathToFile = amazonS3Client.getUrl(bucket, DEFAULT_STORAGE_KEY).getFile();

		return saveToLocalStorage(file, pathToFile);
	}

	@Override
	public S3ObjectInputStream download(String fileId) {
		FileStorage fileStorage = fileDataStorage.get(fileId);
		String key = DEFAULT_STORAGE_KEY + fileStorage.getName();

		S3Object object = amazonS3Client.getObject(bucket, key);
		return object.getObjectContent();
	}

	@Override
	public void delete(List<String> fileIds) {
		List<String> collect = fileDataStorage.getAll(fileIds).stream().map(FileStorage::getName).map(name -> DEFAULT_STORAGE_KEY + name).collect(Collectors.toList());

		DeleteObjectsRequest request = new DeleteObjectsRequest(bucket).withKeys(collect.toArray(String[]::new));
		amazonS3Client.deleteObjects(request);
	}

	@Override
	public void delete(String fileId) {
		String name = fileDataStorage.get(fileId).getName();
		String key = DEFAULT_STORAGE_KEY + name;

		amazonS3Client.deleteObject(bucket, key);
	}

	public String saveFileAndThen(MultipartFile multipartFile, List<FileStorage> storageList, Runnable then) throws IOException {
		String fileId = upload(multipartFile);
		FileStorage fileStorage = fileDataStorage.get(fileId);

		Optional.ofNullable(storageList).filter(Predicate.not(List::isEmpty)).ifPresent(storages -> storages.add(fileStorage));
		then.run();

		return fileStorage.getUrl();
	}

	private PutObjectRequest convertFileToPutRequest(MultipartFile file) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		InputStream inputStream = file.getInputStream();
		String contentType = file.getContentType();
		String key = StorageService.DEFAULT_STORAGE_KEY + file.getName();

		metadata.setContentLength(inputStream.available());
		Optional.ofNullable(contentType).filter(Strings::isNotBlank).ifPresent(metadata::setContentType);

		return new PutObjectRequest(bucket, key, inputStream, metadata);
	}

	private String saveToLocalStorage(MultipartFile file, String pathToFile) {
		FileStorage fileStorage = FileStorage.builder().key(StorageService.DEFAULT_STORAGE_KEY).name(file.getName()).type(file.getContentType()).size(file.getSize()).url(pathToFile).build();

		return fileDataStorage.save(fileStorage);
	}
}

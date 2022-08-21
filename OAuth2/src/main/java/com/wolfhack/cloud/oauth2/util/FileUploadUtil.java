package com.wolfhack.cloud.oauth2.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void trySaveFile(String uploadDirectory, String fileName, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return;
        }
        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioException) {
            throw new IOException("Could not save image file: " + fileName, ioException);
        }
    }

    public static void trySaveFile(String uploadDirectory, String[] fileName, MultipartFile[] multipartFile) throws IOException {

        if (fileName.length != multipartFile.length) {
            throw new IllegalArgumentException("file names array length more/less than files array length");
        }

        for (int i = 0; i <multipartFile.length ; i++) {
            trySaveFile(uploadDirectory, fileName[i], multipartFile[i]);
        }
    }

}

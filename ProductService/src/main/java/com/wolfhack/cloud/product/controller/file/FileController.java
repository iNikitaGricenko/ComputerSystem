package com.wolfhack.cloud.product.controller.file;

import com.wolfhack.cloud.product.service.implement.AmazonStorageServiceInterface;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FileController {

	private final AmazonStorageServiceInterface storageService;

	@RequestMapping(
			value = "/downloads/{fileId}",
			method = {RequestMethod.GET, RequestMethod.OPTIONS},
			produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody byte[] downloadFile(@PathVariable("fileId") String fileId) throws IOException {
		InputStream inputStream = storageService.download(fileId);
		return IOUtils.toByteArray(inputStream);
	}

}

package com.wolfhack.cloud.product.controller.file;

import com.wolfhack.cloud.product.exception.handler.error.ErrorBody;
import com.wolfhack.cloud.product.service.implement.AmazonStorageServiceInterface;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/file")
@Tag(name = "File API")
@RequiredArgsConstructor
public class FileController {

	private final AmazonStorageServiceInterface storageService;

	@RequestMapping(
			value = "/download/{fileId}",
			method = {RequestMethod.GET, RequestMethod.OPTIONS},
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	@ResponseStatus(value = HttpStatus.OK)
	@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorBody.class)))
	@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = byte[].class)))
	@Parameter(name = "fileId", example = "1", in = ParameterIn.PATH, required = true)
	public @ResponseBody byte[] downloadFile(@PathVariable("fileId") String fileId) throws IOException {
		InputStream inputStream = storageService.download(fileId);
		return IOUtils.toByteArray(inputStream);
	}

	@RequestMapping(
			value = "/upload",
			method = RequestMethod.POST,
			consumes = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
	)
	@ResponseStatus(value = HttpStatus.OK)
	public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		storageService.upload(file);
	}

}

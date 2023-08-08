package com.wolfhack.cloud.product.controller.rest;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.service.implement.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final IProductService productService;

	@DeleteMapping
	public void removeItems(@RequestBody List<Long> ids) {
		productService.reduceItemsQuantity(ids);
	}

	@PostMapping("/validate")
	public void validateItems(@RequestBody List<Product> products) {
		productService.validateItems(products);
	}

}

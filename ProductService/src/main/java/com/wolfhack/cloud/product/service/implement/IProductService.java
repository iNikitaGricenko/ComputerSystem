package com.wolfhack.cloud.product.service.implement;

import com.wolfhack.cloud.product.model.Product;

import java.util.List;

public interface IProductService {

	void reduceItemsQuantity(List<Long> itemIds);

	void validateItems(List<Product> itemIds);
}

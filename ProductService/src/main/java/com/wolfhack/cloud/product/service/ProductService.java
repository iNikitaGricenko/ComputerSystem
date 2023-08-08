package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.model.Product;
import com.wolfhack.cloud.product.repository.ProductRepository;
import com.wolfhack.cloud.product.service.implement.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

	private final ProductRepository productRepository;
	private final MongoTemplate mongoTemplate;

	@Override
	public void reduceItemsQuantity(List<Long> itemIds) {
		Query query = new Query(Criteria.where("id").in(itemIds));
		Update update = new Update();
		update.inc("item.quantity", -1);
		mongoTemplate.updateMulti(query, update, Product.class);
	}

	@Override
	public void validateItems(List<Product> itemIds) {
		Query query = new Query(Criteria.where("id").in(itemIds.stream().map(Product::getId).toList()));
		List<Product> products = mongoTemplate.find(query, Product.class);

		if (products.size() != itemIds.size()) {
			throw new RuntimeException("Not valid");
		}
	}
}

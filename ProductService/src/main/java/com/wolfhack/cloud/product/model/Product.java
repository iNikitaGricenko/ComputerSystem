package com.wolfhack.cloud.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Document("product")
@NoArgsConstructor
@AllArgsConstructor
public class Product<T> {

	@NotNull
	@Min(0)
	private Long id;
	@Size(min = 5) private String title;
	@NotNull
	@Min(5)
	private int quantity;
	@NotNull
	@Min(0)
	private float unitPrice;

	private T item;

	private List<FileStorage> photos;

}

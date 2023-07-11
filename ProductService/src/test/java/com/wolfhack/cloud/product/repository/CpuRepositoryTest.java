package com.wolfhack.cloud.product.repository;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.wolfhack.cloud.product.model.Cpu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class CpuRepositoryTest {

	@Test
	@DisplayName("given object to save" +
			" when save object using repository" +
			" then object is saved" +
			" and all instances can be retrieved")
	public void testFindAllOnSave(@Autowired CpuRepository cpuRepository) {
		String name = "test";
		String model = "v1";
		int quantity = 1;
		float unitPrice = 99.9f;
		String description = "None description provided for test";

		Cpu objectToSave = new Cpu();
		objectToSave.setName(name);
		objectToSave.setModel(model);
		objectToSave.setQuantity(quantity);
		objectToSave.setUnitPrice(unitPrice);
		objectToSave.setDescription(description);

		// when
		Cpu saved = cpuRepository.save(objectToSave);
		assertNotNull(saved.getId());

		// then
		List<Cpu> all = cpuRepository.findAll();
		assertNotNull(all);
		assertFalse(all.isEmpty());
	}

	@Test
	@DisplayName("given object to save" +
			" when save object using repository" +
			" then object is saved" +
			" and hes instance can be retrieved")
	public void testFindOneOnSave(@Autowired CpuRepository cpuRepository) {
		String name = "test";
		String model = "v1";
		int quantity = 1;
		float unitPrice = 99.9f;
		String description = "None description provided for test";

		Cpu objectToSave = new Cpu();
		objectToSave.setName(name);
		objectToSave.setModel(model);
		objectToSave.setQuantity(quantity);
		objectToSave.setUnitPrice(unitPrice);
		objectToSave.setDescription(description);

		// when
		Cpu saved = cpuRepository.save(objectToSave);
		assertNotNull(saved.getId());

		// then
		Cpu retrieved = cpuRepository.findById(saved.getId()).get();
		assertNotNull(retrieved);
		assertNotNull(retrieved.getId());
		assertNotEquals(0, retrieved.getId());
		assertEquals(name, retrieved.getName());
		assertEquals(model, retrieved.getModel());
		assertEquals(quantity, retrieved.getQuantity());
		assertEquals(unitPrice, retrieved.getUnitPrice());
		assertEquals(description, retrieved.getDescription());
	}

}
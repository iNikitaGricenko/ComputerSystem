package com.wolfhack.cloud.product.repository;

import com.wolfhack.cloud.product.model.Gpu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mongounit.AssertMatchesDataset;
import org.mongounit.LocationType;
import org.mongounit.MongoUnitTest;
import org.mongounit.SeedWithDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MongoUnitTest
class GpuRepositoryTest {

	@Autowired
	private GpuRepository gpuRepository;

	@Test
	@DisplayName("Create gpu on empty database")
	@AssertMatchesDataset
	public void createGpu() {
		String name = "test";
		String model = "v1";
		int quantity = 1;
		float unitPrice = 99.9f;
		String description = "None description provided for test";

		Gpu objectToSave = new Gpu();
		objectToSave.setName(name);
		objectToSave.setModel(model);
		objectToSave.setQuantity(quantity);
		objectToSave.setUnitPrice(unitPrice);
		objectToSave.setDescription(description);

		Gpu saved = gpuRepository.save(objectToSave);
	}

	@Test
	@DisplayName("Create gpu on a non-empty database")
	@SeedWithDataset("oneGpuToStart.json")
	@AssertMatchesDataset("expected2Gpu.jso")
	public void createGpuWithExistingData() {
		String name = "test";
		String model = "v1";
		int quantity = 1;
		float unitPrice = 99.9f;
		String description = "None description provided for test";

		Gpu objectToSave = new Gpu();
		objectToSave.setName(name);
		objectToSave.setModel(model);
		objectToSave.setQuantity(quantity);
		objectToSave.setUnitPrice(unitPrice);
		objectToSave.setDescription(description);

		Gpu saved = gpuRepository.save(objectToSave);
	}

	@Test
  @DisplayName("Create gpu on a non-empty database with classpath root datasets")
  @SeedWithDataset(
      value = "common/createGpuWithExistingData-seed.json",
      locationType = LocationType.CLASSPATH_ROOT)
  @AssertMatchesDataset(
      value = "common/createGpuWithExistingData-expected.json",
      locationType = LocationType.CLASSPATH_ROOT
  )
  void createGpuWithExistingDataWithPackageRelative() {
		String name = "test";
		String model = "v1";
		int quantity = 1;
		float unitPrice = 99.9f;
		String description = "None description provided for test";

		Gpu objectToSave = new Gpu();
		objectToSave.setName(name);
		objectToSave.setModel(model);
		objectToSave.setQuantity(quantity);
		objectToSave.setUnitPrice(unitPrice);
		objectToSave.setDescription(description);

		Gpu saved = gpuRepository.save(objectToSave);
  }

	@Test
	@DisplayName("Update gpu")
	@SeedWithDataset("createGpuWithExistingData-seed.json")
	@AssertMatchesDataset
	public void updateGpu() {
		String name = "test";
		String model = "v2";
		int quantity = 15;
		float unitPrice = 99.9f;
		String memoryType = "DDR5";

		Gpu objectToUpdate = new Gpu();
		objectToUpdate.setId(1L);
		objectToUpdate.setModel(model);
		objectToUpdate.setQuantity(quantity);
		objectToUpdate.setMemoryType(memoryType);

		Gpu updated = gpuRepository.save(objectToUpdate);
		assertNotNull(updated);
		assertEquals(1L, updated.getId());
		assertEquals(model, updated.getModel());
		assertEquals(quantity, updated.getQuantity());
		assertEquals(memoryType, updated.getMemoryType());

		assertNull(updated.getName());
		assertNotEquals(0, updated.getUnitPrice());

		assertEquals(name, updated.getName());
		assertEquals(unitPrice, updated.getUnitPrice());
	}

	@Test
  @DisplayName("Delete gpu")
  @SeedWithDataset("createGpuWithExistingData-seed.json")
  @AssertMatchesDataset
  void deleteGpuKeepCollections() {
    gpuRepository.deleteById(1L);
  }

}
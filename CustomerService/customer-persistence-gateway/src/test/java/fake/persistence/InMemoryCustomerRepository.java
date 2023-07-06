package fake.persistence;

import com.wolfhack.cloud.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCustomerRepository implements CrudRepository<Customer, Long> {

	private final static Map<Long, Customer> fakeTable = new HashMap<Long, Customer>();
	private static long id = 1;

	@Override
	public <S extends Customer> S save(S entity) {
		Long entityId = Optional.ofNullable(entity.getId()).orElse(id);
		entity.setId(entityId);
		fakeTable.put(id, entity);
		id += 1;
		return entity;
	}

	@Override
	public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
		entities.iterator().forEachRemaining(entity -> {
			Long entityId = Optional.ofNullable(entity.getId()).orElse(id);
			entity.setId(entityId);
			fakeTable.put(id, entity);
			id += 1;
		});
		return entities;
	}

	@Override
	public Optional<Customer> findById(Long aLong) {
		return Optional.ofNullable(fakeTable.get(aLong));
	}

	@Override
	public boolean existsById(Long aLong) {
		return Optional.ofNullable(fakeTable.get(aLong)).isPresent();
	}

	@Override
	public Iterable<Customer> findAll() {
		return fakeTable.values();
	}

	@Override
	public Iterable<Customer> findAllById(Iterable<Long> longs) {
		ArrayList<Customer> customers = new ArrayList<>();
		longs.forEach(id -> {
			Optional<Customer> customer = findById(id);
			customer.ifPresent(customers::add);
		});
		return customers;
	}

	@Override
	public long count() {
		return fakeTable.values().size();
	}

	@Override
	public void deleteById(Long aLong) {
		fakeTable.remove(aLong);
	}

	@Override
	public void delete(Customer entity) {
		fakeTable.remove(entity.getId(), entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> longs) {
		longs.iterator().forEachRemaining(this::deleteById);
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		entities.iterator().forEachRemaining(entity -> {
			fakeTable.remove(entity.getId(), entity);
		});
	}

	@Override
	public void deleteAll() {
		fakeTable.clear();
	}
}

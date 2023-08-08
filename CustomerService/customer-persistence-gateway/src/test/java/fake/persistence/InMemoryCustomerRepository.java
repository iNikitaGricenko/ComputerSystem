package fake.persistence;

import com.wolfhack.cloud.entity.EntityCustomer;
import com.wolfhack.cloud.repository.CustomerRepository;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class InMemoryCustomerRepository implements CustomerRepository {

	private final static Map<Long, EntityCustomer> fakeTable = new HashMap<Long, EntityCustomer>();
	private static long id = 1;

	@Override
	public <S extends EntityCustomer> S save(S entity) {
		Long entityId = Optional.ofNullable(entity.getId()).orElse(id);
		entity.setId(entityId);
		fakeTable.put(id, entity);
		id += 1;
		return entity;
	}

	@Override
	public <S extends EntityCustomer> List<S> saveAll(Iterable<S> entities) {
		List<S> entityCustomers = new ArrayList<>();
		entities.iterator().forEachRemaining(entity -> {
			Long entityId = Optional.ofNullable(entity.getId()).orElse(id);
			entity.setId(entityId);
			fakeTable.put(id, entity);
			id += 1;
			entityCustomers.add(entity);
		});
		return entityCustomers;
	}

	@Override
	public void flush() {

	}

	@Override
	public <S extends EntityCustomer> S saveAndFlush(S entity) {
		S saved = save(entity);
		entity = saved;
		return saved;
	}

	@Override
	public <S extends EntityCustomer> List<S> saveAllAndFlush(Iterable<S> entities) {
		List<S> entityCustomers = new ArrayList<>();
		entities.forEach(this::saveAndFlush);
		entities.forEach(entityCustomers::add);
		return entityCustomers;
	}

	@Override
	public void deleteAllInBatch(Iterable<EntityCustomer> entities) {
		deleteAll(entities);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Long> longs) {
		deleteAllById(longs);
	}

	@Override
	public void deleteAllInBatch() {
		deleteAll();
	}

	@Override
	public EntityCustomer getOne(Long aLong) {
		return findById(aLong).get();
	}

	@Override
	public EntityCustomer getById(Long aLong) {
		return findById(aLong).get();
	}

	@Override
	public EntityCustomer getReferenceById(Long aLong) {
		return findById(aLong).get();
	}

	@Override
	public <S extends EntityCustomer> Optional<S> findOne(Example<S> example) {
		return (Optional<S>) findById(example.getProbe().getId());
	}

	@Override
	public <S extends EntityCustomer> List<S> findAll(Example<S> example) {
		return findAll(example);
	}

	@Override
	public <S extends EntityCustomer> List<S> findAll(Example<S> example, Sort sort) {
		return findAll(example);
	}

	@Override
	public <S extends EntityCustomer> Page<S> findAll(Example<S> example, Pageable pageable) {
		List<S> all = findAll(example);
		return new PageImpl<>(all, pageable, all.size());
	}

	@Override
	public <S extends EntityCustomer> long count(Example<S> example) {
		return fakeTable.values().size();
	}

	@Override
	public <S extends EntityCustomer> boolean exists(Example<S> example) {
		return fakeTable.values().contains(example.getProbe());
	}

	@Override
	public <S extends EntityCustomer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return (R) findById(example.getProbe().getId());
	}

	@Override
	public Optional<EntityCustomer> findById(Long aLong) {
		return Optional.ofNullable(fakeTable.get(aLong));
	}

	@Override
	public boolean existsById(Long aLong) {
		return Optional.ofNullable(fakeTable.get(aLong)).isPresent();
	}

	@Override
	public List<EntityCustomer> findAll() {
		return new ArrayList<>(fakeTable.values());
	}

	@Override
	public List<EntityCustomer> findAll(Sort sort) {
		return new ArrayList<>(fakeTable.values());
	}

	@Override
	public Page<EntityCustomer> findAll(Pageable pageable) {
		List<EntityCustomer> all = findAll(pageable.getSort());
		return new PageImpl<>(all, pageable, all.size());
	}

	@Override
	public List<EntityCustomer> findAllById(Iterable<Long> longs) {
		ArrayList<EntityCustomer> customers = new ArrayList<>();
		longs.forEach(id -> {
			Optional<EntityCustomer> customer = findById(id);
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
	public void delete(EntityCustomer entity) {
		fakeTable.remove(entity.getId(), entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> longs) {
		longs.iterator().forEachRemaining(this::deleteById);
	}

	@Override
	public void deleteAll(Iterable<? extends EntityCustomer> entities) {
		entities.iterator().forEachRemaining(entity -> {
			fakeTable.remove(entity.getId(), entity);
		});
	}

	@Override
	public void deleteAll() {
		fakeTable.clear();
	}
}

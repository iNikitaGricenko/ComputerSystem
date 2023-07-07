package fake.persistence;

import com.wolfhack.cloud.customer.model.enums.OrderStatus;
import com.wolfhack.cloud.entity.EntityCustomerOrder;
import com.wolfhack.cloud.repository.CustomerRepository;
import com.wolfhack.cloud.repository.OrderRepository;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class InMemoryOrderRepository implements OrderRepository {

	private final static Map<Long, EntityCustomerOrder> fakeTable = new HashMap<Long, EntityCustomerOrder>();
	private static long id = 1;

	@Override
	public <S extends EntityCustomerOrder> S save(S entity) {
		Long entityId = Optional.ofNullable(entity.getId()).orElse(id);
		entity.setId(entityId);
		fakeTable.put(id, entity);
		id += 1;
		return entity;
	}

	@Override
	public <S extends EntityCustomerOrder> List<S> saveAll(Iterable<S> entities) {
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
	public <S extends EntityCustomerOrder> S saveAndFlush(S entity) {
		S saved = save(entity);
		entity = saved;
		return saved;
	}

	@Override
	public <S extends EntityCustomerOrder> List<S> saveAllAndFlush(Iterable<S> entities) {
		List<S> entityCustomers = new ArrayList<>();
		entities.forEach(this::saveAndFlush);
		entities.forEach(entityCustomers::add);
		return entityCustomers;
	}

	@Override
	public void deleteAllInBatch(Iterable<EntityCustomerOrder> entities) {
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
	public EntityCustomerOrder getOne(Long aLong) {
		return findById(aLong).get();
	}

	@Override
	public EntityCustomerOrder getById(Long aLong) {
		return findById(aLong).get();
	}

	@Override
	public EntityCustomerOrder getReferenceById(Long aLong) {
		return findById(aLong).get();
	}

	@Override
	public <S extends EntityCustomerOrder> Optional<S> findOne(Example<S> example) {
		return (Optional<S>) findById(example.getProbe().getId());
	}

	@Override
	public <S extends EntityCustomerOrder> List<S> findAll(Example<S> example) {
		return findAll(example);
	}

	@Override
	public <S extends EntityCustomerOrder> List<S> findAll(Example<S> example, Sort sort) {
		return findAll(example);
	}

	@Override
	public <S extends EntityCustomerOrder> Page<S> findAll(Example<S> example, Pageable pageable) {
		List<S> all = findAll(example);
		return new PageImpl<>(all, pageable, all.size());
	}

	@Override
	public <S extends EntityCustomerOrder> long count(Example<S> example) {
		return fakeTable.values().size();
	}

	@Override
	public <S extends EntityCustomerOrder> boolean exists(Example<S> example) {
		return fakeTable.values().contains(example.getProbe());
	}

	@Override
	public <S extends EntityCustomerOrder, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
		return (R) findById(example.getProbe().getId());
	}

	@Override
	public Optional<EntityCustomerOrder> findById(Long aLong) {
		return Optional.ofNullable(fakeTable.get(aLong));
	}

	@Override
	public boolean existsById(Long aLong) {
		return Optional.ofNullable(fakeTable.get(aLong)).isPresent();
	}

	@Override
	public List<EntityCustomerOrder> findAll() {
		return new ArrayList<>(fakeTable.values());
	}

	@Override
	public List<EntityCustomerOrder> findAll(Sort sort) {
		return new ArrayList<>(fakeTable.values());
	}

	@Override
	public Page<EntityCustomerOrder> findAll(Pageable pageable) {
		List<EntityCustomerOrder> all = findAll(pageable.getSort());
		return new PageImpl<>(all, pageable, all.size());
	}

	@Override
	public List<EntityCustomerOrder> findAllById(Iterable<Long> longs) {
		ArrayList<EntityCustomerOrder> customers = new ArrayList<>();
		longs.forEach(id -> {
			Optional<EntityCustomerOrder> customer = findById(id);
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
	public void delete(EntityCustomerOrder entity) {
		fakeTable.remove(entity.getId(), entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> longs) {
		longs.iterator().forEachRemaining(this::deleteById);
	}

	@Override
	public void deleteAll(Iterable<? extends EntityCustomerOrder> entities) {
		entities.iterator().forEachRemaining(entity -> {
			fakeTable.remove(entity.getId(), entity);
		});
	}

	@Override
	public void deleteAll() {
		fakeTable.clear();
	}

	@Override
	public List<EntityCustomerOrder> findAllByStatusAndCompletedBetween(OrderStatus status, LocalDateTime from, LocalDateTime to) {
		return fakeTable.values().stream()
				.filter(entityCustomerOrder -> entityCustomerOrder.getStatus().equals(status))
				.filter(entityCustomerOrder -> entityCustomerOrder.getCompleted() != null ? entityCustomerOrder.getCompleted().isAfter(from) : true)
				.filter(entityCustomerOrder -> entityCustomerOrder.getCompleted() != null ? entityCustomerOrder.getCompleted().isBefore(to) : true)
				.toList();
	}
}

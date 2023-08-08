package com.wolfhack.cloud.product.service.search;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.CpuNotFoundException;
import com.wolfhack.cloud.product.mapper.CpuMapper;
import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.search.CpuSearch;
import com.wolfhack.cloud.product.repository.search.CpuSearchRepository;
import com.wolfhack.cloud.product.service.search.implement.CpuSearchServiceInterface;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CpuSearchService implements CpuSearchServiceInterface {

	private final CpuMapper cpuMapper;
	private final CpuSearchRepository cpuSearchRepository;
	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public long save(Cpu cpu) {
		CpuSearch searchModel = cpuMapper.toSearchModel(cpu);
		return cpuSearchRepository.save(searchModel).getId();
	}

	@Override
	public Page<Cpu> findByProductLine(String productLine, Pageable pageable) {
		return cpuSearchRepository.findByProductLine(productLine, pageable).map(cpuMapper::toEntity);
	}

	@Override
	public List<Cpu> findByTitle(String line, Pageable pageable) {
		Query searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(line).field("name").field("model").field("productLine").type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(Fuzziness.ONE).prefixLength(3)).build();

		SearchHits<CpuSearch> cpus = elasticsearchOperations.search(searchQuery, CpuSearch.class, IndexCoordinates.of("product-cpu"));

		return cpus.map(SearchHit::getContent).map(cpuMapper::toEntity).toList();
	}

	@AopLog
	@Override
	public List<Cpu> findByAllTextFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Cpu.class.getFields()).filter(field -> field.getType().isInstance(String.class)).map(Field::getName).toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(line, fields).type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(Fuzziness.ONE).prefixLength(3)).build();

		SearchHits<CpuSearch> cpus = elasticsearchOperations.search(searchQuery, CpuSearch.class, IndexCoordinates.of("product-cpu"));

		return cpus.map(SearchHit::getContent).map(cpuMapper::toEntity).toList();
	}

	@AopLog
	@Override
	public List<Cpu> findByAllFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Cpu.class.getFields()).map(Field::getName).toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(line, fields).type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(Fuzziness.ONE).prefixLength(3)).build();

		SearchHits<CpuSearch> cpus = elasticsearchOperations.search(searchQuery, CpuSearch.class, IndexCoordinates.of("product-cpu"));

		return cpus.map(SearchHit::getContent).map(cpuMapper::toEntity).toList();
	}

	@Override
	public long update(Cpu cpu) {
		CpuSearch cpuSearch = cpuSearchRepository.findById(cpu.getId()).orElseThrow(CpuNotFoundException::new);

		cpuMapper.partialUpdate(cpuSearch, cpu);

		return cpuSearchRepository.save(cpuSearch).getId();
	}

	@Override
	public void delete(long cpuId) {
		cpuSearchRepository.deleteById(cpuId);
	}

}

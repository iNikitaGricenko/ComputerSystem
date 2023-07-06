package com.wolfhack.cloud.product.service.search;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.CpuNotFoundException;
import com.wolfhack.cloud.product.exception.GpuNotFoundException;
import com.wolfhack.cloud.product.mapper.CpuMapper;
import com.wolfhack.cloud.product.mapper.GpuMapper;
import com.wolfhack.cloud.product.model.Cpu;
import com.wolfhack.cloud.product.model.Gpu;
import com.wolfhack.cloud.product.model.search.CpuSearch;
import com.wolfhack.cloud.product.model.search.GpuSearch;
import com.wolfhack.cloud.product.repository.search.CpuSearchRepository;
import com.wolfhack.cloud.product.repository.search.GpuSearchRepository;
import com.wolfhack.cloud.product.service.search.implement.CpuSearchServiceInterface;
import com.wolfhack.cloud.product.service.search.implement.GpuSearchServiceInterface;
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
public class GpuSearchService implements GpuSearchServiceInterface {

	private final GpuMapper gpuMapper;
	private final GpuSearchRepository gpuSearchRepository;
	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public long save(Gpu gpu) {
		GpuSearch searchModel = gpuMapper.toSearch(gpu);
		return gpuSearchRepository.save(searchModel).getId();
	}

	@Override
	public List<Gpu> findByTitle(String line, Pageable pageable) {
		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line)
						.field("name")
						.field("model")
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<GpuSearch> gpus = elasticsearchOperations.search(searchQuery, GpuSearch.class, IndexCoordinates.of("product-gpu"));

		return gpus.map(SearchHit::getContent).map(gpuMapper::toEntity).toList();
	}

	@AopLog
	@Override
	public List<Gpu> findByAllTextFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Gpu.class.getFields())
				.filter(field -> field.getType().isInstance(String.class))
				.map(Field::getName)
				.toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line, fields)
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<GpuSearch> gpus = elasticsearchOperations.search(searchQuery, GpuSearch.class, IndexCoordinates.of("product-gpu"));

		return gpus.map(SearchHit::getContent).map(gpuMapper::toEntity).toList();
	}
	
	@AopLog
	@Override
	public List<Gpu> findByAllFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Gpu.class.getFields())
				.map(Field::getName)
				.toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.multiMatchQuery(line, fields)
						.type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
						.fuzziness(Fuzziness.ONE)
						.prefixLength(3)).build();

		SearchHits<GpuSearch> gpus = elasticsearchOperations.search(searchQuery, GpuSearch.class, IndexCoordinates.of("product-gpu"));

		return gpus.map(SearchHit::getContent).map(gpuMapper::toEntity).toList();
	}

	@Override
	public long update(Gpu gpu) {
		GpuSearch gpuSearch = gpuSearchRepository.findById(gpu.getId()).orElseThrow(GpuNotFoundException::new);

		gpuMapper.partialUpdate(gpuSearch, gpu);

		return gpuSearchRepository.save(gpuSearch).getId();
	}

	@Override
	public void delete(long gpuId) {
		gpuSearchRepository.deleteById(gpuId);
	}

}

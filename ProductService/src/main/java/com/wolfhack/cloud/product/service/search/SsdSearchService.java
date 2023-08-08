package com.wolfhack.cloud.product.service.search;

import com.wolfhack.cloud.product.annotations.AopLog;
import com.wolfhack.cloud.product.exception.SsdNotFoundException;
import com.wolfhack.cloud.product.mapper.SsdMapper;
import com.wolfhack.cloud.product.model.Ssd;
import com.wolfhack.cloud.product.model.search.SsdSearch;
import com.wolfhack.cloud.product.repository.search.SsdSearchRepository;
import com.wolfhack.cloud.product.service.search.implement.SsdSearchServiceInterface;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
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
public class SsdSearchService implements SsdSearchServiceInterface {

	private final SsdMapper ssdMapper;
	private final SsdSearchRepository ssdSearchRepository;
	private final ElasticsearchOperations elasticsearchOperations;

	@Override
	public long save(Ssd ssd) {
		SsdSearch searchModel = ssdMapper.toSearch(ssd);
		return ssdSearchRepository.save(searchModel).getId();
	}

	@Override
	public List<Ssd> findByTitle(String line, Pageable pageable) {
		Query searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(line).field("name").field("model").type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(Fuzziness.ONE).prefixLength(3)).build();

		SearchHits<SsdSearch> ssds = elasticsearchOperations.search(searchQuery, SsdSearch.class, IndexCoordinates.of("product-ssd"));

		return ssds.map(SearchHit::getContent).map(ssdMapper::toEntity).toList();
	}

	@AopLog
	@Override
	public List<Ssd> findByAllTextFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Ssd.class.getFields()).filter(field -> field.getType().isInstance(String.class)).map(Field::getName).toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(line, fields).type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(Fuzziness.ONE).prefixLength(3)).build();

		SearchHits<SsdSearch> ssds = elasticsearchOperations.search(searchQuery, SsdSearch.class, IndexCoordinates.of("product-ssd"));

		return ssds.map(SearchHit::getContent).map(ssdMapper::toEntity).toList();
	}

	@AopLog
	@Override
	public List<Ssd> findByAllFields(String line, Pageable pageable) {
		String[] fields = Arrays.stream(Ssd.class.getFields()).map(Field::getName).toArray(String[]::new);

		Query searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.multiMatchQuery(line, fields).type(MultiMatchQueryBuilder.Type.BEST_FIELDS).fuzziness(Fuzziness.ONE).prefixLength(3)).build();

		SearchHits<SsdSearch> ssds = elasticsearchOperations.search(searchQuery, SsdSearch.class, IndexCoordinates.of("product-ssd"));

		return ssds.map(SearchHit::getContent).map(ssdMapper::toEntity).toList();
	}

	@Override
	public long update(Ssd ssd) {
		SsdSearch ssdSearch = ssdSearchRepository.findById(ssd.getId()).orElseThrow(SsdNotFoundException::new);

		ssdMapper.partialUpdate(ssdSearch, ssd);

		return ssdSearchRepository.save(ssdSearch).getId();
	}

	@Override
	public void delete(long ssdId) {
		ssdSearchRepository.deleteById(ssdId);
	}

}

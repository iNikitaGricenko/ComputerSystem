package com.wolfhack.cloud.product.service;

import com.wolfhack.cloud.product.model.DatabaseSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class DatabaseSequenceService {

	private final MongoOperations mongoOperations;

	public long generateSequence(String seqName) {
		DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)), new Update().inc("sequence", 1), options().returnNew(true).upsert(true), DatabaseSequence.class);
		return !Objects.isNull(counter) ? counter.getSequence() : 1;
	}

}

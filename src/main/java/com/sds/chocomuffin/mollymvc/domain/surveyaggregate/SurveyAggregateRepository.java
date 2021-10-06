package com.sds.chocomuffin.mollymvc.domain.surveyaggregate;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyAggregateRepository extends MongoRepository<SurveyAggregate, String> {
}

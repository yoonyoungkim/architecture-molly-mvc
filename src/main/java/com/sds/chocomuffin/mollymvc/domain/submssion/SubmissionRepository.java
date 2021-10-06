package com.sds.chocomuffin.mollymvc.domain.submssion;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubmissionRepository extends MongoRepository<Submission, String> {
}

package com.sds.chocomuffin.mollymvc.application;

import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.SurveyAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SurveyAggregateService {
    private final SurveyAggregateRepository surveyAggregateRepository;

    public SurveyAggregateView getSurveyAggregateView(String surveyId) {
        return surveyAggregateRepository.findById(surveyId)
                .map(SurveyAggregateView::new).orElseThrow(() -> new RuntimeException("no item"));
    }
}

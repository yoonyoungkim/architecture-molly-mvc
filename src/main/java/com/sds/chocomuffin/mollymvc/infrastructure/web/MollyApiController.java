package com.sds.chocomuffin.mollymvc.infrastructure.web;

import com.sds.chocomuffin.mollymvc.application.SubmissionService;
import com.sds.chocomuffin.mollymvc.application.SurveyAggregateService;
import com.sds.chocomuffin.mollymvc.application.SurveyAggregateView;
import com.sds.chocomuffin.mollymvc.infrastructure.web.request.SubmissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class MollyApiController {
    private final SurveyAggregateService surveyAggregateService;
    private final SubmissionService submissionService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/submission")
    public void submit(@RequestBody SubmissionRequest submissionRequest) {

        submissionService.addSubmission(submissionRequest.getMixedId(), submissionRequest.getBackgroundSurvey(), submissionRequest.getAnswers());
    }

    @GetMapping("/newsurvey/{surveyId}")
    public SurveyAggregateView getSurvey(@PathVariable String surveyId) {
        return surveyAggregateService.getSurveyAggregateView(surveyId);
    }

}

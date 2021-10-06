package com.sds.chocomuffin.mollymvc.application;

import com.sds.chocomuffin.mollymvc.domain.submssion.Answer;
import com.sds.chocomuffin.mollymvc.domain.submssion.Submission;
import com.sds.chocomuffin.mollymvc.domain.submssion.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    public void addSubmission(String surveyId, List<Answer> backgroundSurvey, List<Answer> answers) {
        Submission submission = new Submission(null, surveyId, "", "", backgroundSurvey, answers, LocalDateTime.now());
        submissionRepository.save(submission);
    }
}

package com.sds.chocomuffin.mollymvc.infrastructure.web.request;

import com.sds.chocomuffin.mollymvc.domain.submssion.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SubmissionRequest {
    private String mixedId;
    private List<Answer> backgroundSurvey;
    private List<Answer> answers;
}

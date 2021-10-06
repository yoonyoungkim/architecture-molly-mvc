package com.sds.chocomuffin.mollymvc.domain.question.specs;

import com.sds.chocomuffin.mollymvc.domain.question.Question;

public interface Opinion {
    boolean isHasOpinionInputOption();
    String getOpinionQuestionText();
    Question withHasOpinionInputOption(boolean hasOpinionInputOption);
}

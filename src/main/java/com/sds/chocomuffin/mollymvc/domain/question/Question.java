package com.sds.chocomuffin.mollymvc.domain.question;


import com.sds.chocomuffin.mollymvc.domain.question.values.DisplayLogic;

import java.util.List;
import java.util.Set;

public interface Question extends CommonAction {

    String getId();

    String getText();

    String getPlainText();

    QuestionType getQuestionType();

    RequiredType getRequiredType();

    Set<String> getTags();

    String getKeyword();

    String getReferenceId();

    String getReferenceNo();

    List<DisplayLogic> getDisplayLogics();

    Question withId(String id);

    Question withRequiredType(RequiredType requiredType);

    Question withReferenceId(String referenceId);

    Question withReferenceNo(String referenceNo);

    Question withDisplayLogics(List<DisplayLogic> displayLogics);

    default boolean isRatingLike() {
        return getQuestionType() == QuestionType.RATING || getQuestionType() == QuestionType.RATING_INDEX || getQuestionType() == QuestionType.MATRIX;
    }

    default boolean equals(Question question) {
        return (this.getId().equals(question.getId())
                && this.getQuestionType().equals(question.getQuestionType())
                && this.getText().equals(question.getText())
        );
    }

}

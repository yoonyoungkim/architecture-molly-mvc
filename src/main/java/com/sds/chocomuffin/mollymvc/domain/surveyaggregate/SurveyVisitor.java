package com.sds.chocomuffin.mollymvc.domain.surveyaggregate;

public interface SurveyVisitor {

    void visit(CategoryView categoryView);
    void visit(QuestionView questionView);
}

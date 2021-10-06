package com.sds.chocomuffin.mollymvc.domain.surveyaggregate;

public interface ViewNode {

    String getId();
    void setId(String id);

    void accept(SurveyVisitor visitor);
}

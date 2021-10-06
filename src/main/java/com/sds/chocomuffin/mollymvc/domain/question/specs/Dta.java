package com.sds.chocomuffin.mollymvc.domain.question.specs;

import com.sds.chocomuffin.mollymvc.domain.question.values.Choice;

import java.util.List;

public interface Dta {

    List<String> getC1();

    List<String> getS1();

    String getIndex(Choice maxChoice);

    String getIndexSummary(Choice maxChoice);

    List<String> getFactor(int index);
}

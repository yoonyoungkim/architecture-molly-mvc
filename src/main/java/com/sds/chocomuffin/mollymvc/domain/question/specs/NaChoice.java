package com.sds.chocomuffin.mollymvc.domain.question.specs;


import com.sds.chocomuffin.mollymvc.domain.question.Question;

public interface NaChoice {
    boolean isHasNaChoice();

    String getNaChoiceLabel();

    Question withHasNaChoice(boolean hasNaChoice);
}

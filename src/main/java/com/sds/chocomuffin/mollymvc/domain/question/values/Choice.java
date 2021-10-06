package com.sds.chocomuffin.mollymvc.domain.question.values;

import com.sds.chocomuffin.mollymvc.domain.question.FatChoice;

public interface Choice {

    String getId();
    String getText();
    String getPlainText();

    FatChoice toFatChoice();
}

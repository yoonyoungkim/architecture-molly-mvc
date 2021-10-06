package com.sds.chocomuffin.mollymvc.domain.question;

import com.sds.chocomuffin.mollymvc.domain.question.values.DisplayLogic;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class QuestionBasic {

    private final String id;
    private final QuestionType questionType;
    private final RequiredType requiredType;
    private final String text;
    private final String plainText;
    private final Set<String> tags;
    private final String keyword;
    private final String referenceId;
    private final String referenceNo;
    private final List<DisplayLogic> displayLogics;
}

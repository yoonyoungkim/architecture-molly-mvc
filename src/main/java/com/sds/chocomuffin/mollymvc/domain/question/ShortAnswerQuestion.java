package com.sds.chocomuffin.mollymvc.domain.question;

import com.sds.chocomuffin.mollymvc.domain.question.values.DisplayLogic;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.QuestionView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@ToString
public class ShortAnswerQuestion implements Question {

    @With
    private final String id;
    private final QuestionType questionType;
    @With private final RequiredType requiredType;
    private final String text;
    private final String plainText;
    private final Set<String> tags;
    private final String keyword;
    @With
    private final String referenceId;
    @With
    private final String referenceNo;
    @With
    private final List<DisplayLogic> displayLogics;

    @Override
    public boolean equals(Question question) {
        if (!(question instanceof ShortAnswerQuestion)) return false;

        return Question.super.equals(question);
    }

    public ShortAnswerQuestion(QuestionBasic questionBasic) {
        this.id = questionBasic.getId();
        this.questionType = questionBasic.getQuestionType();
        this.requiredType = questionBasic.getRequiredType();
        this.text = questionBasic.getText();
        this.plainText = questionBasic.getPlainText();
        this.tags = questionBasic.getTags();
        this.keyword = questionBasic.getKeyword();
        this.referenceId = questionBasic.getReferenceId();
        this.referenceNo = questionBasic.getReferenceNo();
        this.displayLogics = questionBasic.getDisplayLogics();
    }

    @Override
    public QuestionView toQuestionView() {
        return questionViewBuilder(this)
                .build();
    }
}

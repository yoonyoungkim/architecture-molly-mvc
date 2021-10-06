package com.sds.chocomuffin.mollymvc.domain.question;

import com.sds.chocomuffin.mollymvc.domain.question.specs.HasChoice;
import com.sds.chocomuffin.mollymvc.domain.question.specs.NaChoice;
import com.sds.chocomuffin.mollymvc.domain.question.specs.Opinion;
import com.sds.chocomuffin.mollymvc.domain.question.specs.RatingLike;
import com.sds.chocomuffin.mollymvc.domain.question.values.Choice;
import com.sds.chocomuffin.mollymvc.domain.question.values.DisplayLogic;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.QuestionView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@ToString
public class MatrixQuestion implements Question, HasChoice, NaChoice, Opinion, RatingLike {

    @With
    private final String id;
    private final QuestionType questionType;
    @With
    private final RequiredType requiredType;
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

    private final List<ChildQuestion> childQuestions;
    private final List<Choice> choices;
    @With
    private final boolean hasNaChoice;
    private final String naChoiceLabel;
    @With
    private final boolean hasOpinionInputOption;
    private final String opinionQuestionText;

    public MatrixQuestion(QuestionBasic basic, List<ChildQuestion> childQuestions, List<Choice> choices, boolean hasNaChoice, String naChoiceLabel, boolean hasOpinionInputOption, String opinionQuestionText) {
        this.id = basic.getId();
        this.questionType = basic.getQuestionType();
        this.requiredType = basic.getRequiredType();
        this.text = basic.getText();
        this.plainText = basic.getPlainText();
        this.tags = basic.getTags();
        this.keyword = basic.getKeyword();
        this.referenceId = basic.getReferenceId();
        this.referenceNo = basic.getReferenceNo();
        this.displayLogics = basic.getDisplayLogics();

        this.childQuestions = childQuestions;
        this.choices = choices;
        this.hasNaChoice = hasNaChoice;
        this.naChoiceLabel = naChoiceLabel;
        this.hasOpinionInputOption = hasOpinionInputOption;
        this.opinionQuestionText = opinionQuestionText;
    }

    public final List<ChildQuestion> getChildQuestions() {
        return childQuestions;
    }

    @Override
    public boolean equals(Question question) {
        if (!(question instanceof MatrixQuestion)) return false;

        if (!Question.super.equals(question)) return false;

        return choiceEquals((HasChoice) question);
    }

    @Override
    public QuestionView toQuestionView() {
        return questionViewBuilder(this)
                .choices(this.toFatChoice(this.getChoices()))
                .hasNaChoice(this.isHasNaChoice())
                .naChoiceLabel(this.getNaChoiceLabel())
                .hasOpinionInputOption(this.isHasOpinionInputOption())
                .opinionQuestionText(this.getOpinionQuestionText())
                .childQuestions(this.getChildQuestions())
                .build();
    }
}

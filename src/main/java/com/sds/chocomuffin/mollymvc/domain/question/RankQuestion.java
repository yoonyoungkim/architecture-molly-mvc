package com.sds.chocomuffin.mollymvc.domain.question;

import com.sds.chocomuffin.mollymvc.domain.question.specs.*;
import com.sds.chocomuffin.mollymvc.domain.question.values.Choice;
import com.sds.chocomuffin.mollymvc.domain.question.values.DisplayLogic;
import com.sds.chocomuffin.mollymvc.domain.question.values.SkipLogic;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.QuestionView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@ToString
@Getter
public class RankQuestion implements Question, HasChoice, Opinion, HasSkipLogics, HasRank, HasChoiceLabel {

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

    @With
    private final List<SkipLogic> skipLogics;
    private final Integer maxRank;

    private final ChoiceLabel choiceLabel;
    private final ImageSize imageSize;

    private final List<Choice> choices;
    @With
    private final boolean hasOpinionInputOption;
    private final String opinionQuestionText;

    public RankQuestion(QuestionBasic basic, ChoiceLabel choiceLabel, ImageSize imageSize, List<Choice> choices, boolean hasOpinionInputOption, String opinionQuestionText, List<SkipLogic> skipLogics, Integer maxRank) {
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

        this.skipLogics = skipLogics;
        this.maxRank = maxRank;

        this.choiceLabel = choiceLabel;
        this.imageSize = imageSize;

        this.choices = choices;
        this.hasOpinionInputOption = hasOpinionInputOption;
        this.opinionQuestionText = opinionQuestionText;
    }

    @Override
    public boolean equals(Question question) {
        if (!(question instanceof RankQuestion)) return false;

        if (!Question.super.equals(question)) return false;

        return choiceEquals((HasChoice) question);
    }

    @Override
    public QuestionView toQuestionView() {
        return questionViewBuilder(this)
                .skipLogics(this.getSkipLogics())
                .maxRank(this.getMaxRank())
                .choiceLabel(this.getChoiceLabel())
                .imageSize(this.getImageSize())
                .choices(this.toFatChoice(this.getChoices(), this.getChoiceLabel()))
                .hasOpinionInputOption(this.isHasOpinionInputOption())
                .opinionQuestionText(this.getOpinionQuestionText())
                .build();
    }
}

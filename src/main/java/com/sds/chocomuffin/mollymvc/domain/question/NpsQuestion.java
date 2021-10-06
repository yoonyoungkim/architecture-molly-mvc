package com.sds.chocomuffin.mollymvc.domain.question;

import com.sds.chocomuffin.mollymvc.domain.question.specs.HasChoice;
import com.sds.chocomuffin.mollymvc.domain.question.specs.HasSkipLogics;
import com.sds.chocomuffin.mollymvc.domain.question.specs.Opinion;
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
public class NpsQuestion implements Question, HasChoice, Opinion, HasSkipLogics {

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

    private final List<Choice> choices;
    private final String lowText;
    private final String highText;
    @With
    private final boolean hasOpinionInputOption;
    private final String opinionQuestionText;

    public final String getLowText() {
        return lowText;
    }

    public final String getHighText() {
        return highText;
    }

    public NpsQuestion(QuestionBasic basic, List<Choice> choices, String lowText, String highText, boolean hasOpinionInputOption, String opinionQuestionText, List<SkipLogic> skipLogics) {
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

        this.choices = choices;
        this.lowText = lowText;
        this.highText = highText;
        this.hasOpinionInputOption = hasOpinionInputOption;
        this.opinionQuestionText = opinionQuestionText;
    }

    @Override
    public boolean equals(Question question) {
        if (!(question instanceof NpsQuestion)) return false;

        if (!Question.super.equals(question)) return false;

        return choiceEquals((HasChoice) question);
    }

    @Override
    public QuestionView toQuestionView() {
        return questionViewBuilder(this)
                .skipLogics(this.getSkipLogics())
                .choices(this.toFatChoice(this.getChoices()))
                .lowText(this.getLowText())
                .highText(this.getHighText())
                .hasOpinionInputOption(this.isHasOpinionInputOption())
                .opinionQuestionText(this.getOpinionQuestionText())
                .build();
    }
}

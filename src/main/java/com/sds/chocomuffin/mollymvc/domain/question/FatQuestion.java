package com.sds.chocomuffin.mollymvc.domain.question;

import com.sds.chocomuffin.mollymvc.domain.question.specs.ChoiceLabel;
import com.sds.chocomuffin.mollymvc.domain.question.specs.ImageSize;
import com.sds.chocomuffin.mollymvc.domain.question.values.*;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.SurveyVisitor;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.ViewNode;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@With
@ToString
public class FatQuestion implements ViewNode {

    private String id;
    private QuestionType questionType;
    private RequiredType requiredType;
    private String text;
    private String plainText;
    private Set<String> tags;
    private String keyword;
    private String referenceId;
    private String referenceNo;
    private List<DisplayLogic> displayLogics;

    private List<SkipLogic> skipLogics;

    private ChoiceLabel choiceLabel;
    private ImageSize imageSize;

    private List<FatChoice> choices;
    private boolean hasNaChoice;
    private String naChoiceLabel;
    private boolean hasOpinionInputOption;
    private String opinionQuestionText;

    private boolean hasEtcChoice;

    private List<ChildQuestion> childQuestions;
    private String lowText;
    private String highText;
    private Integer maxRank;

    public FatQuestion(QuestionBasic basic, List<SkipLogic> skipLogics, ChoiceLabel choiceLabel, ImageSize imageSize, List<FatChoice> choices, boolean hasNaChoice, String naChoiceLabel, boolean hasOpinionInputOption, String opinionQuestionText, boolean hasEtcChoice, List<ChildQuestion> childQuestions, String lowText, String highText, Integer maxRank) {
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

        this.choiceLabel = choiceLabel;
        this.imageSize = imageSize;
        this.choices = choices;
        this.hasNaChoice = hasNaChoice;
        this.naChoiceLabel = naChoiceLabel;
        this.hasOpinionInputOption = hasOpinionInputOption;
        this.opinionQuestionText = opinionQuestionText;
        this.hasEtcChoice = hasEtcChoice;
        this.childQuestions = childQuestions;
        this.lowText = lowText;
        this.highText = highText;
        this.maxRank = maxRank;
    }

    public QuestionBasic questionBasic() {
        return new QuestionBasic(id, questionType, requiredType, text, plainText, tags, keyword, referenceId, referenceNo, displayLogics);
    }

    public List<Choice> toChoice() {
        if (Objects.isNull(choices)) return List.of();
        if (choiceLabel == null || choiceLabel == ChoiceLabel.TEXT) {
            return this.choices.stream().map(c -> new RegularChoice(c.getId(), c.getText(), c.getPlainText())).collect(Collectors.toList());
        }
        return this.choices.stream().map(c -> new ImageChoice(c.getId(), c.getText(), c.getPlainText(), c.getUrl(), c.getImageUploadMode(), c.getFileName())).collect(Collectors.toList());
    }

    @Override
    public void setId(String id) {
        // do nothing
    }

    @Override
    public void accept(SurveyVisitor visitor) {
        // do nothing
    }
}

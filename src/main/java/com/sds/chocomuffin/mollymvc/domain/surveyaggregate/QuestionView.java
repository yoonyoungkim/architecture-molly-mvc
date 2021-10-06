package com.sds.chocomuffin.mollymvc.domain.surveyaggregate;

import com.sds.chocomuffin.mollymvc.domain.question.*;
import com.sds.chocomuffin.mollymvc.domain.question.specs.ChoiceLabel;
import com.sds.chocomuffin.mollymvc.domain.question.specs.ImageSize;
import com.sds.chocomuffin.mollymvc.domain.question.values.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
public class QuestionView implements ViewNode {

    @Setter
    private String id;
    private QuestionType questionType;
    private RequiredType requiredType;
    private String text;
    private String plainText;
    private Set<String> tags;
    private String keyword;
    private String referenceId;
    private String referenceNo;
    @Setter
    private List<DisplayLogic> displayLogics;
    @Setter
    private List<SkipLogic> skipLogics;
    private Integer maxRank;

    private List<String> c1;
    private List<String> s1;

    private final ChoiceLabel choiceLabel;
    private final ImageSize imageSize;

    private final List<FatChoice> choices;
    private final boolean hasNaChoice;
    private final String naChoiceLabel;
    private final boolean hasOpinionInputOption;
    private final String opinionQuestionText;
    private final boolean hasEtcChoice;

    private final List<ChildQuestion> childQuestions;

    private final String lowText;
    private final String highText;

    @Override
    public void accept(SurveyVisitor visitor) {
        visitor.visit(this);
    }

    public QuestionBasic questionBasic() {
        return new QuestionBasic(id, questionType, requiredType, text, plainText, tags, keyword, referenceId, referenceNo, displayLogics);
    }

    public List<Choice> toChoice() {
        if (Objects.isNull(choices)) return List.of();
        if (questionType == QuestionType.RATING_INDEX) {
            return this.choices.stream().map(c -> new IndexedChoice(c.getId(), c.getText(), c.getPlainText(), c.getIndex(), c.getIndexSummary(), c.getFactor())).collect(Collectors.toList());
        }
        if (choiceLabel == null || choiceLabel == ChoiceLabel.TEXT) {
            return this.choices.stream().map(c -> new RegularChoice(c.getId(), c.getText(), c.getPlainText())).collect(Collectors.toList());
        }
        return this.choices.stream().map(c -> new ImageChoice(c.getId(), c.getText(), c.getPlainText(), c.getUrl(), c.getImageUploadMode(), c.getFileName())).collect(Collectors.toList());
    }
}

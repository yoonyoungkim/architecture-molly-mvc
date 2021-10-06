package com.sds.chocomuffin.mollymvc.domain.question;

import com.sds.chocomuffin.mollymvc.domain.question.specs.*;
import com.sds.chocomuffin.mollymvc.domain.question.values.*;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.QuestionView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.With;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Getter
@ToString
public class RatingIndexQuestion implements Question, HasChoice, NaChoice, Opinion, HasSkipLogics, RatingLike, Dta {

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

    private final List<IndexedChoice> indexedChoices;
    private final List<String> c1;
    private final List<String> s1;
    @With
    private final boolean hasNaChoice;
    private final String naChoiceLabel;
    @With
    private final boolean hasOpinionInputOption;
    private final String opinionQuestionText;

    public RatingIndexQuestion(QuestionBasic basic, List<IndexedChoice> indexedChoices, List<String> c1, List<String> s1, boolean hasNaChoice, String naChoiceLabel, boolean hasOpinionInputOption, String opinionQuestionText, List<SkipLogic> skipLogics) {
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


        this.indexedChoices = indexedChoices;
        this.c1 = c1;
        this.s1 = s1;
        this.hasNaChoice = hasNaChoice;
        this.naChoiceLabel = naChoiceLabel;
        this.hasOpinionInputOption = hasOpinionInputOption;
        this.opinionQuestionText = opinionQuestionText;
    }

    public List<Choice> getChoices() {
        return indexedChoices.stream().map(ic -> (Choice) ic).collect(toList());
    }

    @Override
    public boolean equals(Question question) {
        if (!(question instanceof RatingIndexQuestion)) return false;

        if (!Question.super.equals(question)) return false;

        return choiceEquals((HasChoice) question);
    }

    @Override
    public QuestionView toQuestionView() {
        return questionViewBuilder(this)
                .skipLogics(this.getSkipLogics())
                .choices(fatChoices())
                .hasNaChoice(this.isHasNaChoice())
                .naChoiceLabel(this.getNaChoiceLabel())
                .hasOpinionInputOption(this.isHasOpinionInputOption())
                .opinionQuestionText(this.getOpinionQuestionText())
                .c1(this.getC1())
                .s1(this.getS1())
                .build();
    }

    private List<FatChoice> fatChoices() {
        return this.getIndexedChoices().stream().map(c -> new FatChoice(c.getId(), c.getText(), c.getPlainText(), null, null, null, c.getIndex(), c.getIndexSummary(), c.getFactor())).collect(Collectors.toList());
    }

    @Override
    public String getIndex(Choice maxChoice) {
        IndexedChoice indexedChoice = (IndexedChoice) maxChoice;
        return indexedChoice.getIndex();
    }

    @Override
    public String getIndexSummary(Choice maxChoice) {
        IndexedChoice indexedChoice = (IndexedChoice) maxChoice;
        return indexedChoice.getIndexSummary();
    }

    @Override
    public List<String> getFactor(int index) {
        Choice choice = this.getChoices().get(index);
        return ((IndexedChoice) choice).getFactor();
    }

    public RatingQuestion toRatingQuestion() {
        QuestionBasic basic = new QuestionBasic(id, QuestionType.RATING, requiredType, text, plainText, tags, keyword, null, null, displayLogics);
        return new RatingQuestion(basic, toRegularChoices(), hasNaChoice, naChoiceLabel, hasOpinionInputOption, opinionQuestionText, skipLogics);
    }

    private List<Choice> toRegularChoices() {
        return this.getIndexedChoices().stream().map(ic -> new RegularChoice(ic.getId(), ic.getText(), ic.getPlainText())).collect(toList());
    }
}

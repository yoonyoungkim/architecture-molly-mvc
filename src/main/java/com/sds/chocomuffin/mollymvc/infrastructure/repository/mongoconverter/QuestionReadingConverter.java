package com.sds.chocomuffin.mollymvc.infrastructure.repository.mongoconverter;

import com.sds.chocomuffin.mollymvc.domain.question.*;
import com.sds.chocomuffin.mollymvc.domain.question.specs.ChoiceLabel;
import com.sds.chocomuffin.mollymvc.domain.question.specs.ImageSize;
import com.sds.chocomuffin.mollymvc.domain.question.values.Choice;
import com.sds.chocomuffin.mollymvc.domain.question.values.IndexedChoice;
import com.sds.chocomuffin.mollymvc.domain.question.values.SkipLogic;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Map;

import static com.sds.chocomuffin.mollymvc.domain.question.QuestionType.*;

public class QuestionReadingConverter implements Converter<Document, Question> {

    private interface BsonToQuestionAction {
        Question toQuestion(Document document);
    }

    private Map<QuestionType, BsonToQuestionAction> actionMap;

    public QuestionReadingConverter() {
        actionMap = Map.of(
                RATING, this::toRatingQuestion,
                RATING_INDEX, this::toRatingIndexQuestion,
                MATRIX, this::toMatrixQuestion,
                SHORT_ANSWER, this::toShortAnswerQuestion,
                MULTIPLE_CHOICE, this::toMultipleChoiceQuestion,
                SINGLE_CHOICE, this::toSingleChoiceQuestion,
                NPS, this::toNps,
                RANK, this::toRankQuestion
        );
    }

    @Override
    public Question convert(Document obj) {

        QuestionType questionType = questionType(obj);

        return actionMap.get(questionType).toQuestion(obj);
    }

    private QuestionType questionType(Document obj) {
        return QuestionType.valueOf(obj.get("questionType", String.class));
    }

    private RatingQuestion toRatingQuestion(Document document) {

        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);

        QuestionBasic basic = bsonToQuestion.questionBasic();
        List<Choice> choices = bsonToQuestion.choices();
        boolean hasNaChoice = bsonToQuestion.hasNaChoice();
        String naChoiceLabel = bsonToQuestion.naChoiceLabel();
        boolean hasOpinionInputOption = bsonToQuestion.hasOpinionInputOption();
        String opinionQuestionText = bsonToQuestion.opinionQuestionText();

        List<SkipLogic> skipLogics = bsonToQuestion.skipLogics();

        return new RatingQuestion(basic, choices, hasNaChoice, naChoiceLabel, hasOpinionInputOption, opinionQuestionText, skipLogics);
    }

    private RatingIndexQuestion toRatingIndexQuestion(Document document) {

        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);
        QuestionBasic basic = bsonToQuestion.questionBasic();
        List<IndexedChoice> indexedChoices = bsonToQuestion.indexedChoices();

        List<String> c1 = bsonToQuestion.c1();
        List<String> s1 = bsonToQuestion.s1();

        boolean hasNaChoice = bsonToQuestion.hasNaChoice();
        String naChoiceLabel = bsonToQuestion.naChoiceLabel();
        boolean hasOpinionInputOption = bsonToQuestion.hasOpinionInputOption();
        String opinionQuestionText = bsonToQuestion.opinionQuestionText();

        List<SkipLogic> skipLogics = bsonToQuestion.skipLogics();

        return new RatingIndexQuestion(basic, indexedChoices, c1, s1, hasNaChoice, naChoiceLabel, hasOpinionInputOption, opinionQuestionText, skipLogics);
    }

    private MatrixQuestion toMatrixQuestion(Document document) {

        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);
        QuestionBasic basic = bsonToQuestion.questionBasic();
        List<Choice> choices = bsonToQuestion.choices();
        boolean hasNaChoice = bsonToQuestion.hasNaChoice();
        String naChoiceLabel = bsonToQuestion.naChoiceLabel();
        boolean hasOpinionInputOption = bsonToQuestion.hasOpinionInputOption();
        String opinionQuestionText = bsonToQuestion.opinionQuestionText();


        return new MatrixQuestion(basic, bsonToQuestion.childQuestions(), choices, hasNaChoice, naChoiceLabel, hasOpinionInputOption, opinionQuestionText);
    }

    private ShortAnswerQuestion toShortAnswerQuestion(Document document) {
        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);
        QuestionBasic basic = bsonToQuestion.questionBasic();
        return new ShortAnswerQuestion(basic);
    }

    private MultipleChoiceQuestion toMultipleChoiceQuestion(Document document) {

        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);
        QuestionBasic basic = bsonToQuestion.questionBasic();

        ChoiceLabel choiceLabel = bsonToQuestion.choiceLabel();
        ImageSize imageSize = bsonToQuestion.imageSize();
        List<Choice> choices = bsonToQuestion.choicesMayHaveImageChoice();
        boolean hasEtcChoice = bsonToQuestion.hasEtcChoice();
        boolean hasOpinionInputOption = bsonToQuestion.hasOpinionInputOption();
        String opinionQuestionText = bsonToQuestion.opinionQuestionText();

        List<SkipLogic> skipLogics = bsonToQuestion.skipLogics();

        return new MultipleChoiceQuestion(basic, choiceLabel, imageSize, choices, hasEtcChoice, hasOpinionInputOption, opinionQuestionText, skipLogics);
    }

    private SingleChoiceQuestion toSingleChoiceQuestion(Document document) {

        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);

        QuestionBasic basic = bsonToQuestion.questionBasic();

        ChoiceLabel choiceLabel = bsonToQuestion.choiceLabel();
        ImageSize imageSize = bsonToQuestion.imageSize();
        List<Choice> choices = bsonToQuestion.choicesMayHaveImageChoice();
        boolean hasEtcChoice = bsonToQuestion.hasEtcChoice();
        boolean hasOpinionInputOption = bsonToQuestion.hasOpinionInputOption();
        String opinionQuestionText = bsonToQuestion.opinionQuestionText();

        List<SkipLogic> skipLogics = bsonToQuestion.skipLogics();

        return new SingleChoiceQuestion(basic, choiceLabel, imageSize, choices, hasEtcChoice, hasOpinionInputOption, opinionQuestionText, skipLogics);
    }

    public NpsQuestion toNps(Document document) {

        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);
        QuestionBasic basic = bsonToQuestion.questionBasic();
        List<Choice> choices = bsonToQuestion.choicesMayHaveImageChoice();

        String lowText = bsonToQuestion.lowText();
        String highText = bsonToQuestion.highText();

        boolean hasOpinionInputOption = bsonToQuestion.hasOpinionInputOption();
        String opinionQuestionText = bsonToQuestion.opinionQuestionText();
        List<SkipLogic> skipLogics = bsonToQuestion.skipLogics();

        return new NpsQuestion(basic, choices, lowText, highText, hasOpinionInputOption, opinionQuestionText, skipLogics);
    }

    public RankQuestion toRankQuestion(Document document) {

        BsonToQuestion bsonToQuestion = new BsonToQuestion(document);
        QuestionBasic basic = bsonToQuestion.questionBasic();

        ChoiceLabel choiceLabel = bsonToQuestion.choiceLabel();
        ImageSize imageSize = bsonToQuestion.imageSize();
        List<Choice> choices = bsonToQuestion.choicesMayHaveImageChoice();
        boolean hasOpinionInputOption = bsonToQuestion.hasOpinionInputOption();
        String opinionQuestionText = bsonToQuestion.opinionQuestionText();

        List<SkipLogic> skipLogics = bsonToQuestion.skipLogics();

        int maxRank = bsonToQuestion.maxRank();

        return new RankQuestion(basic, choiceLabel, imageSize, choices, hasOpinionInputOption, opinionQuestionText, skipLogics, maxRank);
    }

}

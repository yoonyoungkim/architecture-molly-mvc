package com.sds.chocomuffin.mollymvc.infrastructure.repository.mongoconverter;

import com.sds.chocomuffin.mollymvc.domain.question.*;
import com.sds.chocomuffin.mollymvc.domain.question.specs.*;
import com.sds.chocomuffin.mollymvc.domain.question.values.*;
import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WritingConverter
public class QuestionWritingConverter implements Converter<Question, Document> {

    @Override
    public Document convert(Question q) {
        Document obj = new Document();
        obj.put("_id", q.getId());
        obj.put("questionType", q.getQuestionType().name());
        obj.put("requiredType", q.getRequiredType().name());
        obj.put("text", q.getText());
        obj.put("plainText", q.getPlainText());
        obj.put("tags", q.getTags());
        obj.put("keyword", q.getKeyword());
        obj.put("referenceId", q.getReferenceId());
        obj.put("referenceNo", q.getReferenceNo());
        obj.put("displayLogics", convertDisplayLogicDocumentList(q.getDisplayLogics()));

        if (q instanceof HasSkipLogics) {
            obj.put("skipLogics", convertSkipLogicDocumentList(((HasSkipLogics) q).getSkipLogics()));
        }

        if (q instanceof HasChoice) {
            obj.put("choices", convertChoiceDocumentList((HasChoice) q));
        }

        if (q instanceof NaChoice) {
            NaChoice naChoice = (NaChoice) q;
            obj.put("hasNaChoice", naChoice.isHasNaChoice());
            obj.put("naChoiceLabel", naChoice.getNaChoiceLabel());
        }

        if (q instanceof Opinion) {
            Opinion opinion = (Opinion) q;
            obj.put("hasOpinionInputOption", opinion.isHasOpinionInputOption());
            obj.put("opinionQuestionText", opinion.getOpinionQuestionText());
        }

        if (q instanceof EtcChoice) {
            obj.put("hasEtcChoice", ((EtcChoice) q).isHasEtcChoice());
        }

        if (q instanceof HasRank) {
            obj.put("maxRank", ((HasRank) q).getMaxRank());
        }

        if (q instanceof HasChoiceLabel) {
            obj.put("choiceLabel", ((HasChoiceLabel) q).getChoiceLabel().name());
            obj.put("imageSize", ((HasChoiceLabel) q).getImageSize().name());
        }

        if (QuestionType.MATRIX.equals(q.getQuestionType())) {

            MatrixQuestion matrixQuestion = (MatrixQuestion) q;
            obj.put("childQuestions", convertChildQuestionDocumentList(matrixQuestion));

        }

        if (QuestionType.NPS.equals(q.getQuestionType())) {

            NpsQuestion npsQuestion = (NpsQuestion) q;
            obj.put("lowText", npsQuestion.getLowText());
            obj.put("highText", npsQuestion.getHighText());

        }

        if (QuestionType.RATING_INDEX.equals(q.getQuestionType())) {
            RatingIndexQuestion ratingIndexQuestion = (RatingIndexQuestion) q;
            obj.put("C1", ratingIndexQuestion.getC1());
            obj.put("S1", ratingIndexQuestion.getS1());
        }

        return obj;
    }

    private List<Document> convertDisplayLogicDocumentList(List<DisplayLogic> displayLogics) {
        if (Objects.isNull(displayLogics)) return List.of();
        return displayLogics.stream().map(logic -> {
            Document obj = new Document();
            obj.put("questionId", logic.getQuestionId());
            obj.put("choiceId", logic.getChoiceId());
            obj.put("operator", logic.getOperator());
            obj.put("rank", logic.getRank());
            obj.put("rankOperator", Objects.nonNull(logic.getRankOperator()) ? logic.getRankOperator().name() : null);
            return obj;
        }).collect(Collectors.toList());
    }

    private List<Document> convertSkipLogicDocumentList(List<SkipLogic> skipLogics) {
        if (Objects.isNull(skipLogics)) return List.of();
        return skipLogics.stream().map(logic -> {
            Document obj = new Document();
            obj.put("skipLogicType", logic.getSkipLogicType().name());
            obj.put("fromChoiceId", logic.getFromChoiceId());
            obj.put("toId", logic.getToId());
            obj.put("rank", logic.getRank());
            obj.put("rankOperator", Objects.nonNull(logic.getRankOperator()) ? logic.getRankOperator().name() : null);
            return obj;
        }).collect(Collectors.toList());
    }

    private List<Document> convertChoiceDocumentList(HasChoice question) {
        return question.getChoices().stream().map(this::convertDocument).collect(Collectors.toList());
    }

    private Document convertDocument(Choice choice) {
        Document obj = new Document();
        obj.put("id", choice.getId());
        obj.put("text", choice.getText());
        obj.put("plainText", choice.getPlainText());
        if (choice instanceof IndexedChoice) {
            IndexedChoice indexedChoice = (IndexedChoice) choice;
            obj.put("index", indexedChoice.getIndex());
            obj.put("indexSummary", indexedChoice.getIndexSummary());
            obj.put("factor", indexedChoice.getFactor());
        }
        if (choice instanceof ImageChoice) {
            ImageChoice imageChoice = (ImageChoice) choice;
            obj.put("url", imageChoice.getUrl());

            obj.put("imageUploadMode", Objects.isNull(imageChoice.getImageUploadMode()) ? ImageUploadMode.DEFAULT.name() : imageChoice.getImageUploadMode().name());
            obj.put("fileName", imageChoice.getFileName());
        }
        return obj;
    }

    private List<Document> convertChildQuestionDocumentList(MatrixQuestion question) {
        return question.getChildQuestions().stream().map(this::convertChildQuestionDocument).collect(Collectors.toList());
    }

    private Document convertChildQuestionDocument(ChildQuestion childQuestion) {
        Document obj = new Document();
        obj.put("id", childQuestion.getId());
        obj.put("text", childQuestion.getText());
        obj.put("plainText", childQuestion.getPlainText());
        return obj;
    }


}

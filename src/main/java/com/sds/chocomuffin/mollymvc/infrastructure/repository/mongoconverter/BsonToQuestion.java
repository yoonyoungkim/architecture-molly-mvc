package com.sds.chocomuffin.mollymvc.infrastructure.repository.mongoconverter;

import com.sds.chocomuffin.mollymvc.domain.question.ChildQuestion;
import com.sds.chocomuffin.mollymvc.domain.question.QuestionBasic;
import com.sds.chocomuffin.mollymvc.domain.question.QuestionType;
import com.sds.chocomuffin.mollymvc.domain.question.RequiredType;
import com.sds.chocomuffin.mollymvc.domain.question.specs.*;
import com.sds.chocomuffin.mollymvc.domain.question.values.*;
import org.bson.Document;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BsonToQuestion {
    private final Document document;

    public BsonToQuestion(Document document) {
        this.document = document;
    }

    public QuestionBasic questionBasic() {
        String id = document.get("_id", String.class);
        QuestionType questionType = QuestionType.valueOf(document.get("questionType", String.class));
        RequiredType requiredType = RequiredType.valueOf(document.get("requiredType", String.class));
        String text = document.get("text", String.class);
        String plainText = document.get("plainText", String.class);
        Set<String> tags = Set.copyOf(document.get("tags", List.of()));
        String keyword = document.get("keyword", String.class);
        String referenceId = document.get("referenceId", String.class);
        String referenceNo = document.get("referenceNo", String.class);
        List<DisplayLogic> displayLogics = extractDisplayLogics(document, questionType);

        return new QuestionBasic(id, questionType, requiredType, text, plainText, tags, keyword, referenceId, referenceNo, displayLogics);
    }

    public List<Choice> choices() {
        List<Document> choiceDocumentList = document.get("choices", List.class);
        if (choiceDocumentList == null) return List.of();
        return choiceDocumentList.stream().map(this::convertChoice).collect(Collectors.toList());
    }

    public boolean hasNaChoice() {
        return document.getBoolean("hasNaChoice", false);
    }

    public String naChoiceLabel() {
        return document.get("naChoiceLabel", String.class);
    }

    public boolean hasOpinionInputOption() {
        return document.getBoolean("hasOpinionInputOption", false);
    }

    public String opinionQuestionText() {
        return document.get("opinionQuestionText", String.class);
    }

    public List<SkipLogic> skipLogics() {
        List<Document> skipLogicDocumentList = document.get("skipLogics", List.class);
        if (skipLogicDocumentList == null) return List.of();
        return skipLogicDocumentList.stream().map(this::convertSkipLogic).collect(Collectors.toList());
    }

    public ChoiceLabel choiceLabel() {
        String choiceLabelString = document.get("choiceLabel", String.class);
        return Objects.nonNull(choiceLabelString) ? ChoiceLabel.valueOf(choiceLabelString) : ChoiceLabel.TEXT;
    }

    public ImageSize imageSize() {
        String imageSizeString = document.get("imageSize", String.class);
        return Objects.nonNull(imageSizeString) ? ImageSize.valueOf(imageSizeString) : ImageSize.SMALL;
    }

    public List<Choice> choicesMayHaveImageChoice() {
        List<Document> choiceDocumentList = document.get("choices", List.class);
        if (choiceDocumentList == null) return List.of();
        String choiceLabelString = document.get("choiceLabel", String.class);
        ChoiceLabel choiceLabel = choiceLabelString != null ? ChoiceLabel.valueOf(choiceLabelString) : ChoiceLabel.TEXT;
        if (choiceLabel == ChoiceLabel.IMAGE) {
            return choiceDocumentList.stream().map(this::convertImageChoice).collect(Collectors.toList());
        }
        return choiceDocumentList.stream().map(this::convertChoice).collect(Collectors.toList());
    }

    public boolean hasEtcChoice() {
        return document.getBoolean("hasEtcChoice", false);
    }

    public List<ChildQuestion> childQuestions() {
        List<Document> childDocumentList = document.get("childQuestions", List.class);
        return childDocumentList == null ? List.of() : childDocumentList.stream().map(this::convertChildQuestion).collect(Collectors.toList());
    }

    private Choice convertImageChoice(Document doc) {
        String id = getChoiceId(doc);
        String text = doc.get("text", String.class);
        String plainText = doc.get("plainText", String.class);
        String url = doc.get("url", String.class);
        String imageUploadModeString = doc.get("imageUploadMode", String.class);
        ImageUploadMode imageUploadMode = Objects.isNull(imageUploadModeString) ? ImageUploadMode.DEFAULT : ImageUploadMode.valueOf(imageUploadModeString);

        String fileName = doc.get("fileName", String.class);

        return new ImageChoice(id, text, plainText, url, imageUploadMode, fileName);
    }

    private SkipLogic convertSkipLogic(Document doc) {
        SkipLogicType skipLogicType = SkipLogicType.valueOf(doc.get("skipLogicType", String.class));
        String fromChoiceId = doc.get("fromChoiceId", String.class);
        String toId = doc.get("toId", String.class);
        Integer rank = doc.get("rank", Integer.class);
        String rankOperatorString = doc.get("rankOperator", String.class);
        RankOperator rankOperator = Objects.isNull(rankOperatorString) ? null : RankOperator.valueOf(rankOperatorString);

        return new SkipLogic(skipLogicType, fromChoiceId, toId, rank, rankOperator);
    }


    private Choice convertChoice(Document doc) {
        String id = getChoiceId(doc);
        String text = doc.get("text", String.class);
        String rawPlainText = doc.get("plainText", String.class);
        String plainText = (rawPlainText == null || rawPlainText == "") ? text : rawPlainText;
        return new RegularChoice(id, text, plainText);
    }

    public String getChoiceId(Document doc) {
        if (doc.get("id") instanceof Integer) {
            return doc.get("id", Integer.class).toString();
        }
        if (doc.get("id") instanceof Double) {
            return doc.get("id", Double.class).toString();
        }
        return Objects.requireNonNullElse(doc.get("id", String.class), "");
    }

    private List<DisplayLogic> extractDisplayLogics(Document document, QuestionType questionType) {
        List<Document> displayLogicDocumentList = document.get("displayLogics", List.class);
        if (displayLogicDocumentList == null) return List.of();
        return displayLogicDocumentList.stream().map(this::convertDisplayLogic).collect(Collectors.toList());
    }

    private DisplayLogic convertDisplayLogic(Document doc) {
        String questionId = doc.get("questionId", String.class);
        String choiceId = doc.get("choiceId", String.class);
        String operator = doc.get("operator", String.class);
        Integer rank = doc.get("rank", Integer.class);
        String rankOperatorString = doc.get("rankOperator", String.class);
        RankOperator rankOperator = Objects.isNull(rankOperatorString) ? null : RankOperator.valueOf(rankOperatorString);

        return new DisplayLogic(questionId, choiceId, operator, rank, rankOperator);
    }

    private ChildQuestion convertChildQuestion(Document doc) {
        String id = doc.get("id", String.class);
        String text = doc.get("text", String.class);
        String plainText = doc.get("plainText", String.class);
        return new ChildQuestion(id, text, plainText);
    }

    public String lowText() {
        return document.get("lowText", String.class);
    }

    public String highText() {
        return document.get("highText", String.class);
    }

    public List<IndexedChoice> indexedChoices() {
        List<Document> choiceDocumentList = document.get("choices", List.class);
        if (choiceDocumentList == null) return List.of();
        return choiceDocumentList.stream().map(this::convertIndexedChoice).collect(Collectors.toList());
    }

    public List<String> c1() {
        return document.getList("C1", String.class);
    }

    public List<String> s1() {
        return document.getList("S1", String.class);
    }

    private IndexedChoice convertIndexedChoice(Document doc) {
        String id = this.getChoiceId(doc);
        String text = doc.get("text", String.class);
        String plainText = doc.get("plainText", String.class);

        String index = doc.get("index", String.class);
        String indexSummary = doc.get("indexSummary", String.class);
        List<String> factor = doc.getList("factor", String.class);

        return new IndexedChoice(id, text, plainText, index, indexSummary, factor);
    }

    public int maxRank() {
        return document.get("maxRank", Integer.class);
    }
}

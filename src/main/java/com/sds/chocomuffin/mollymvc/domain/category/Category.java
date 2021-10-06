package com.sds.chocomuffin.mollymvc.domain.category;

import com.sds.chocomuffin.mollymvc.domain.question.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "categories")
public class Category {

    @With
    @Id
    private String id;
    @With
    private String surveyId;
    @With
    private String name;
    @With
    private String description;
    private CategoryType type;
    private ItemType itemType;
    @With
    private List<String> childIds;

    public Category(String newCategoryId, String surveyId, String name) {
        this(newCategoryId, surveyId, name, null, CategoryType.CATEGORY, ItemType.QUESTION, List.of());
    }

    public Category(String newCategoryId, String surveyId, ItemType itemType, List<String> childIds) {
        this(newCategoryId, surveyId, "", null, CategoryType.CATEGORY, itemType, childIds);
    }

    public Category(String newCategoryId, String surveyId, String name, String description, ItemType itemType, List<String> childIds) {
        this(newCategoryId, surveyId, name, description, CategoryType.CATEGORY, itemType, childIds);
    }

    public Category addChildCategory(Category category) {
        List<String> newChildIds = this.getChildIds();
        newChildIds.add(category.getId());
        return this.withChildIds(newChildIds);
    }

    public Category removeChildCategory(String childId) {
        List<String> newChildIds = this.getChildIds().stream()
                .filter(cId -> !cId.equals(childId))
                .collect(Collectors.toList());
        return this.withChildIds(newChildIds);
    }

    public Category addQuestions(List<Question> questions) {
        List<String> questionIds = questions.stream().map(Question::getId).collect(Collectors.toList());
        List<String> newChildIds = this.getChildIds();
        if (Objects.isNull(newChildIds)) {
            newChildIds = questionIds;
        } else {
            newChildIds.addAll(questionIds);
        }
        return this.withChildIds(newChildIds);
    }

    public Category removeQuestion(String questionId) {
        List<String> childIds = this.getChildIds().stream()
                .filter(cId -> !cId.equals(questionId))
                .collect(Collectors.toList());
        return this.withChildIds(childIds);
    }

    public Category copyQuestion(String newQuestionId, String targetQuestionId) {
        List<String> childIds = this.getChildIds();
        int beforeQuestionIndex = childIds.indexOf(targetQuestionId);
        childIds.add(beforeQuestionIndex + 1, newQuestionId);
        return this.withChildIds(childIds);
    }

    public static Category emptyBackgroundSurveyCategory(){
        return new Category(null, null, "", "", CategoryType.BACKGROUND_SURVEY, ItemType.QUESTION, List.of());
    }
}

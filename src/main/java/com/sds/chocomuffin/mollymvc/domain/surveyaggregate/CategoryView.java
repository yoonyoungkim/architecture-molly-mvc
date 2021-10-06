package com.sds.chocomuffin.mollymvc.domain.surveyaggregate;

import com.sds.chocomuffin.mollymvc.domain.category.Category;
import com.sds.chocomuffin.mollymvc.domain.category.CategoryType;
import com.sds.chocomuffin.mollymvc.domain.category.ItemType;
import com.sds.chocomuffin.mollymvc.domain.question.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryView implements ViewNode {
    @Setter
    private String id;
    private String name;
    private String description;
    private CategoryType type;
    private ItemType itemType;
    private List<String> childIds;

    private List<ViewNode> childNodes;

    private boolean referenced;

    public CategoryView(Category category, Map<String, Category> categoryTable, Map<String, Question> questionTable) {
        this(category.getId(), category.getName(), category.getDescription(), category.getType(), category.getItemType(), category.getChildIds(), categoryTable, questionTable);
    }

    public CategoryView(String id, String name, String description, CategoryType type, ItemType itemType, List<String> childIds, Map<String, Category> categoryTable, Map<String, Question> questionTable) {

        if (itemType.equals(ItemType.QUESTION)) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.type = type;
            this.itemType = itemType;
            this.childIds = childIds;
            this.childNodes = childIds.stream()
                    .map(questionTable::get)
                    .map(Question::toQuestionView)
                    .collect(Collectors.toList());
            this.referenced = childNodes.stream().anyMatch(q -> !StringUtils.isEmpty(((QuestionView) q).getReferenceId()));
            return;
        }
        if (itemType.equals(ItemType.CATEGORY)) {
            List<ViewNode> childCategories = childIds.stream()
                    .map(categoryTable::get)
                    .map(c -> new CategoryView(c.getId(), c.getName(), c.getDescription(), c.getType(), c.getItemType(), c.getChildIds(), categoryTable, questionTable))
                    .collect(Collectors.toList());

            this.id = id;
            this.name = name;
            this.type = type;
            this.description = description;
            this.itemType = itemType;
            this.childIds = childIds;
            this.childNodes = childCategories;
            this.referenced = childCategories.stream().anyMatch(c -> ((CategoryView) c).isReferenced());
        }
    }


    public static CategoryView newBackgroundSurvey(String id, List<Question> backgroundSurvey) {
        List<String> childIds = backgroundSurvey.stream().map(Question::getId).collect(Collectors.toList());
        return new CategoryView(id, "기본 정보", "", CategoryType.BACKGROUND_SURVEY, ItemType.QUESTION, childIds, Map.of(), Map.of());
    }

    public static CategoryView empty() {
        return new CategoryView(null, "기본 정보", "", CategoryType.CATEGORY, ItemType.QUESTION, List.of(), Map.of(), Map.of());
    }

    public static CategoryView emptyBackgroundSurvey() {
        return new CategoryView(null, "기본 정보", "", CategoryType.BACKGROUND_SURVEY, ItemType.QUESTION, List.of(), Map.of(), Map.of());
    }

    @Override
    public void accept(SurveyVisitor visitor) {
        visitor.visit(this);
        for (ViewNode child : childNodes) {
            child.accept(visitor);
        }
    }
}

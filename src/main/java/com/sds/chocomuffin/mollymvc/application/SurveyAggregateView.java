package com.sds.chocomuffin.mollymvc.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.CategoryView;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.SurveyAggregate;
import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.ViewNode;
import com.sds.chocomuffin.mollymvc.survey.PageType;
import com.sds.chocomuffin.mollymvc.survey.Progress;
import com.sds.chocomuffin.mollymvc.survey.Survey;
import com.sds.chocomuffin.mollymvc.survey.ViewType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class SurveyAggregateView {
    @Setter
    private String id;
    private String title;
    private String teamName;
    private String description;
    private String closingMessage;
    private Progress progress;
    private ViewType viewType;
    private PageType pageType;
    private String libraryKey;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime closedDate;

    private String managerDisplayName;
    private String managerUsername;
    private int maximum;

    private CategoryView backgroundSurvey;
    private List<ViewNode> categories;

    private long submissionCount;

    public SurveyAggregateView(Survey survey, CategoryView backgroundSurvey, List<ViewNode> categories, long submissionCount) {
        this(
                survey.getId(),
                survey.getTitle(),
                survey.getTeamName(),
                survey.getDescription(),
                survey.getClosingMessage(),
                survey.getProgress(),
                survey.getViewType(),
                survey.getPageType(),
                survey.getLibraryKey(),
                survey.getManagedInfo().getCreatedDate(),
                survey.getManagedInfo().getStartedDate(),
                survey.getManagedInfo().getClosedDate(),
                survey.getManagedInfo().getManagedBy().getManagerName(),
                survey.getManagedInfo().getManagedBy().getManager(),
                survey.getMaximum(),
                backgroundSurvey,
                categories,
                submissionCount
        );
    }

    public SurveyAggregateView(SurveyAggregate surveyAggregate, long submissionCount) {
        this(
                surveyAggregate.getId(),
                surveyAggregate.getTitle(),
                surveyAggregate.getTeamName(),
                surveyAggregate.getDescription(),
                surveyAggregate.getClosingMessage(),
                surveyAggregate.getProgress(),
                surveyAggregate.getViewType(),
                surveyAggregate.getPageType(),
                surveyAggregate.getLibraryKey(),
                surveyAggregate.getCreatedDate(),
                surveyAggregate.getStartedDate(),
                surveyAggregate.getClosedDate(),
                surveyAggregate.getManagerDisplayName(),
                surveyAggregate.getManagerUsername(),
                surveyAggregate.getMaximum(),
                surveyAggregate.getBackgroundSurvey(),
                surveyAggregate.getCategories(),
                submissionCount
        );
    }

    public SurveyAggregateView(SurveyAggregate surveyAggregate) {
        this(surveyAggregate, 0);
    }

}

package com.sds.chocomuffin.mollymvc.domain.surveyaggregate;

import com.sds.chocomuffin.mollymvc.survey.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
@Document(collection = "survey-aggregate")
public class SurveyAggregate {

    @Id
    private String id;

    private CategoryView backgroundSurvey;
    private List<ViewNode> categories;

    private String title;
    private String teamName;
    private String description;
    private String closingMessage;
    private Progress progress;
    private ViewType viewType;
    private PageType pageType;
    private String libraryKey;

    private LocalDateTime createdDate;
    private LocalDateTime startedDate;
    private LocalDateTime closedDate;

    private String managerDisplayName;
    private String managerUsername;
    private int maximum;

    public SurveyAggregate(Survey survey, CategoryView backgroundSurvey, List<ViewNode> categoryViews) {
        this.id = survey.getId();
        this.title = survey.getTitle();
        this.teamName = survey.getTeamName();
        this.description = survey.getDescription();
        this.closingMessage = survey.getClosingMessage();
        this.progress = survey.getProgress();
        this.viewType = survey.getViewType();
        this.pageType = survey.getPageType();
        this.libraryKey = survey.getLibraryKey();
        this.createdDate = survey.getManagedInfo().getCreatedDate();
        this.startedDate = survey.getManagedInfo().getStartedDate();
        this.closedDate = survey.getManagedInfo().getClosedDate();
        this.managerDisplayName = survey.getManagedInfo().getManagedBy().getManagerName();
        this.managerUsername = survey.getManagedInfo().getManagedBy().getManager();
        this.maximum = survey.getMaximum();

        this.backgroundSurvey = backgroundSurvey;
        this.categories = categoryViews;
    }

    public Survey copyToSurvey(String id, String username, String displayName) {
        return new Survey(id, title, teamName, description, closingMessage, Progress.CREATING, viewType, pageType, maximum, null, ManagedInfo.initBy(username, displayName), List.of(), libraryKey);
    }
}

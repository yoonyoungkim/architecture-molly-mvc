package com.sds.chocomuffin.mollymvc.survey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@With
@RequiredArgsConstructor
@Getter
@Document(collection = "survey")
public class Survey {

    @Id
    private final String id;

    private final String title;
    private final String teamName;
    private final String description;
    private final String closingMessage;

    private final Progress progress;
    private final ViewType viewType;
    private final PageType pageType;
    private final Integer maximum;
    private final TempEmail tempEmail;
    private final ManagedInfo managedInfo;

    private final List<String> categoryIds;

    private final String libraryKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return id.equals(survey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @AllArgsConstructor
    @Getter
    public static class TempEmail {
        String title;
        String contents;
    }

}

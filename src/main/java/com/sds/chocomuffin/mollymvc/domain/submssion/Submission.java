package com.sds.chocomuffin.mollymvc.domain.submssion;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Document(collection = "submissions")
public class Submission {
    @Id
    private final String id;
    private final String surveyId;
    private final String did;
    private final String pid;
    private final List<Answer> backgroundSurvey;
    private final List<Answer> answers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdDate;

    public Submission withDidAndNow(String newDid) {
        return new Submission(id, surveyId, newDid, pid, backgroundSurvey, answers, LocalDateTime.now());
    }
}

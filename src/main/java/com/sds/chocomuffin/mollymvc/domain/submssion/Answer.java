package com.sds.chocomuffin.mollymvc.domain.submssion;

import com.sds.chocomuffin.mollymvc.domain.question.QuestionType;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@With
public class Answer {

    private String questionId;
    private QuestionType questionType;
    private String singleAnswer;
    private List<String> multipleAnswer;
    private String etc;
    private String opinion;
    private List<String> tags;
    private String parentQuestionId;
    private String referenceId;

    private String submissionId;

    private Boolean ratingLike;

    public boolean isRatingLike() {
        return QuestionType.RATING == questionType || QuestionType.RATING_INDEX == questionType || QuestionType.MATRIX_CHILD == questionType;
    }

}



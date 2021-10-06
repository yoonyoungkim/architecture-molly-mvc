package com.sds.chocomuffin.mollymvc.domain.question;


import com.sds.chocomuffin.mollymvc.domain.surveyaggregate.QuestionView;

public interface CommonAction {
    QuestionView toQuestionView();

    default QuestionView.QuestionViewBuilder questionViewBuilder(Question question) {
        return QuestionView.builder()
                .id(question.getId())
                .questionType(question.getQuestionType())
                .requiredType(question.getRequiredType())
                .text(question.getText())
                .plainText(question.getPlainText())
                .tags(question.getTags())
                .keyword(question.getKeyword())
                .referenceId(question.getReferenceId())
                .referenceNo(question.getReferenceNo())
                .displayLogics(question.getDisplayLogics());
    }
}

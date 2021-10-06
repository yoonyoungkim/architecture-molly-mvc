package com.sds.chocomuffin.mollymvc.domain.question.values;

import com.sds.chocomuffin.mollymvc.domain.question.specs.RankOperator;
import com.sds.chocomuffin.mollymvc.domain.question.specs.SkipLogicType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

@With
@Getter
@AllArgsConstructor
public class SkipLogic {
    SkipLogicType skipLogicType;
    private String fromChoiceId;
    private String toId;
    private Integer rank;
    private RankOperator rankOperator;
}

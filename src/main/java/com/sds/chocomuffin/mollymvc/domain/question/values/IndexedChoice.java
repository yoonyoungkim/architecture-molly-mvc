package com.sds.chocomuffin.mollymvc.domain.question.values;

import com.sds.chocomuffin.mollymvc.domain.question.FatChoice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IndexedChoice implements Choice {

    @Field("id")
    private String id;
    private String text;
    private String plainText;
    private String index;
    private String indexSummary;
    private List<String> factor;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getPlainText() {
        return plainText;
    }

    @Override
    public FatChoice toFatChoice() {
        return new FatChoice(id, text, plainText, null, null, null, index, indexSummary, factor);
    }

    public String getIndex() { return index; }

    public String getIndexSummary() { return indexSummary; }

    public List<String> getFactor() { return factor; }
}

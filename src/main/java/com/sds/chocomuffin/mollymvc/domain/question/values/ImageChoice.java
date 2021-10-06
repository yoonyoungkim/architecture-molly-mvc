package com.sds.chocomuffin.mollymvc.domain.question.values;

import com.sds.chocomuffin.mollymvc.domain.question.FatChoice;
import com.sds.chocomuffin.mollymvc.domain.question.specs.ImageUploadMode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageChoice implements Choice {

    @Field("id")
    private String id;
    private String text;
    private String plainText;
    private String url;
    private ImageUploadMode imageUploadMode;
    private String fileName;

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
        return new FatChoice(id, text, plainText, url, imageUploadMode, fileName, null, null, null);
    }

    public String getUrl() {
        return url;
    }

    public ImageUploadMode getImageUploadMode() {
        return imageUploadMode;
    }

    public String getFileName() {
        return fileName;
    }


}

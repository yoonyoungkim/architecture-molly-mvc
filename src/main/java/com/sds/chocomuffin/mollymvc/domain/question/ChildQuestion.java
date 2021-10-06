package com.sds.chocomuffin.mollymvc.domain.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChildQuestion {
    @Field("id")
    private String id;
    private String text;
    private String plainText;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildQuestion that = (ChildQuestion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.sds.chocomuffin.mollymvc.domain.question.specs;

import com.sds.chocomuffin.mollymvc.domain.question.FatChoice;
import com.sds.chocomuffin.mollymvc.domain.question.values.Choice;
import com.sds.chocomuffin.mollymvc.domain.question.values.ImageChoice;

import java.util.List;
import java.util.stream.Collectors;

public interface HasChoice {

    List<Choice> getChoices();

    default boolean choiceEquals(HasChoice that) {
        List<Choice> thisChoices = getChoices();
        List<Choice> thatChoices = that.getChoices();
        if (thisChoices.size() != thatChoices.size()) return false;

        for (int i = 0; i < thisChoices.size(); i++) {
            if (!thisChoices.get(i).getText().equals(thatChoices.get(i).getText()))
                return false;
        }
        return true;
    }

    default List<FatChoice> toFatChoice(List<Choice> choice, ChoiceLabel choiceLabel) {
        return choice.stream().map(c -> {
            if (choiceLabel == ChoiceLabel.IMAGE) {
                ImageChoice imageChoice = (ImageChoice) c;
                return new FatChoice(imageChoice.getId(), imageChoice.getText(), imageChoice.getPlainText(), imageChoice.getUrl(), imageChoice.getImageUploadMode(), imageChoice.getFileName(), "", "", List.of());
            }
            return new FatChoice(c.getId(), c.getText(), c.getPlainText(), null, null, null, "", "", List.of());
        }).collect(Collectors.toList());
    }

    default List<FatChoice> toFatChoice(List<Choice> choice) {
        return choice.stream().map(c -> new FatChoice(c.getId(), c.getText(), c.getPlainText(), null, null, null, "", "", List.of())).collect(Collectors.toList());
    }
}
